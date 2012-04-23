package blocklandglass.messages.clients

import blocklandglass.Authenticator
import blocklandglass.messages._

sealed trait S2CMessage extends Message
sealed trait C2SMessage extends Message

case class HandshakeInit(name: String, blid: String) extends C2SMessage {
	def serialize = Seq("handshake", "init", name, blid)
}
case class HandshakeChallenge(challenge: String) extends S2CMessage {
	def serialize = Seq("handshake", "challenge", challenge)
}
case class HandshakeResponse(response: String) extends C2SMessage {
	def serialize = Seq("handshake", "response", response)
}
case class HandshakeResult(accepted: Boolean) extends S2CMessage {
	def serialize = Seq("handshake", "result", if(Authenticator.queryAuth()) { if (accepted) "1" else "0" } else "-1")
}

object C2SMessageReader extends MessageReader[C2SMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "init", name, blid) => Some(HandshakeInit(name, blid))
		case Seq("handshake", "response", response) => Some(HandshakeResponse(response))
		case _ => None
	}
}

object S2CMessageReader extends MessageReader[S2CMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "challenge", challenge) => Some(HandshakeChallenge(challenge))
		case Seq("handshake", "result", result) => Some(HandshakeResult(result == "1"))
		case _ => None
	}
}