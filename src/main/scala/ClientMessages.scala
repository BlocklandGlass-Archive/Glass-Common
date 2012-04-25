package blocklandglass.messages.clients

import blocklandglass.messages._

sealed trait S2CMessage extends Message
sealed trait C2SMessage extends Message

case class HandshakeInit(name: String, blid: String) extends C2SMessage {
	def serialize = Seq("handshake", "init", name, blid)
}
case class HandshakeResult(accepted: Boolean) extends S2CMessage {
	def serialize = Seq("handshake", "result", if (accepted) "1" else "0")
}
case object HandshakeUnregisteredResult extends S2CMessage {
	def serialize = Seq("handshake", "notregistered")
}

object C2SMessageReader extends MessageReader[C2SMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "init", name, blid) => Some(HandshakeInit(name, blid))
		case _ => None
	}
}

object S2CMessageReader extends MessageReader[S2CMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "result", result) => Some(HandshakeResult(result == "1"))
		case Seq("handshake", "notregistered") => Some(HandshakeUnregisteredResult)
		case _ => None
	}
}