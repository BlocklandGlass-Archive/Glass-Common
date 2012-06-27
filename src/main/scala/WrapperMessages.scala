package blocklandglass.messages.wrappers

import java.math.BigInteger
import java.security.spec.RSAPublicKeySpec

import org.apache.commons.codec.binary.Base64

import blocklandglass.messages._

sealed trait S2WMessage extends Message
sealed trait W2SMessage extends Message

case class HandshakeInit(pubkey: RSAPublicKeySpec) extends W2SMessage {
	def serialize = Seq("handshake", "init", pubkey.getModulus().toString(10), pubkey.getPublicExponent().toString(10))
}
case class HandshakeChallenge(challenge: Array[Byte]) extends S2WMessage {
	def serialize = Seq("handshake", "challenge", Base64.encodeBase64String(challenge))
}
case class HandshakeResponse(response: String) extends W2SMessage {
	def serialize = Seq("handshake", "response", response)
}
case class HandshakeResult(accepted: Boolean) extends S2WMessage {
	def serialize = Seq("handshake", "result", if (accepted) "1" else "0")
}

case class AddServerMessage(serverId: String, friendlyName: String) extends W2SMessage {
	def serialize = Seq("server", "add", serverId, friendlyName)
}
case class RemoveServerMessage(serverId: String) extends W2SMessage {
	def serialize = Seq("server", "remove", serverId)
}

case class EvalMessage(serverId: String, cmd: String) extends S2WMessage {
	def serialize = Seq("eval", serverId, cmd)
}
case class ServerLogMessage(serverId: String, message: String) extends W2SMessage {
	def serialize = Seq("log", serverId, message)
}

object W2SMessageReader extends MessageReader[W2SMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "init", keyModulo, keyPubExp) => Some(HandshakeInit(new RSAPublicKeySpec(new BigInteger(keyModulo, 10), new BigInteger(keyPubExp, 10))))
		case Seq("handshake", "response", response) => Some(HandshakeResponse(response))
		case Seq("log", serverId, message) => Some(ServerLogMessage(serverId, message))
		case Seq("server", "add", serverId, friendlyName) => Some(AddServerMessage(serverId, friendlyName))
		case Seq("server", "remove", serverId) => Some(RemoveServerMessage(serverId))
		case _ => None
	}
}

object S2WMessageReader extends MessageReader[S2WMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "challenge", challenge) => Some(HandshakeChallenge(Base64.decodeBase64(challenge)))
		case Seq("handshake", "result", result) => Some(HandshakeResult(result == "1"))
		case Seq("eval", serverId, cmd) => Some(EvalMessage(serverId, cmd))
		case _ => None
	}
}