package blocklandglass.messages.wrappers

import java.math.BigInteger
import java.security.spec.RSAPublicKeySpec

import blocklandglass.messages._

sealed trait S2WMessage extends Message
sealed trait W2SMessage extends Message

case class HandshakeInit(pubkey: RSAPublicKeySpec) extends W2SMessage {
	def serialize = Seq("handshake", "init", pubkey.getModulus().toString(10), pubkey.getPublicExponent().toString(10))
}
case class HandshakeChallenge(challenge: String) extends S2WMessage {
	def serialize = Seq("handshake", "challenge", challenge)
}
case class HandshakeResponse(response: String) extends W2SMessage {
	def serialize = Seq("handshake", "response", response)
}
case class HandshakeResult(accepted: Boolean) extends S2WMessage {
	def serialize = Seq("handshake", "result", if (accepted) "1" else "0")
}

object W2SMessageReader extends MessageReader[W2SMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "init", keyModulo, keyPubExp) => Some(HandshakeInit(new RSAPublicKeySpec(new BigInteger(keyModulo, 10), new BigInteger(keyPubExp, 10))))
		case Seq("handshake", "response", response) => Some(HandshakeResponse(response))
		case _ => None
	}
}

object S2WMessageReader extends MessageReader[S2WMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "challenge", challenge) => Some(HandshakeChallenge(challenge))
		case Seq("handshake", "result", result) => Some(HandshakeResult(result == "1"))
		case _ => None
	}
}