package blocklandglass.messages.clients

import blocklandglass.messages._

case class ManagedServerAddress(wrapper: String, id: String)

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

case class AddServerMessage(server: ManagedServerAddress) extends S2CMessage {
	def serialize = Seq("server", "add", server.wrapper, server.id)
}
case class RemoveServerMessage(server: ManagedServerAddress) extends S2CMessage {
	def serialize = Seq("server", "remove", server.wrapper, server.id)
}

case class EvalMessage(server: ManagedServerAddress, cmd: String) extends C2SMessage {
	def serialize = Seq("eval", server.wrapper, server.id, cmd)
}
case class ServerLogMessage(server: ManagedServerAddress, message: String) extends S2CMessage {
	def serialize = Seq("log", server.wrapper, server.id, message)
}

object C2SMessageReader extends MessageReader[C2SMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "init", name, blid) => Some(HandshakeInit(name, blid))
		case Seq("eval", wrapper, serverId, cmd) => Some(EvalMessage(ManagedServerAddress(wrapper, serverId), cmd))
		case _ => None
	}
}

object S2CMessageReader extends MessageReader[S2CMessage] {
	protected def parse(message: Seq[String]) = message match {
		case Seq("handshake", "result", result) => Some(HandshakeResult(result == "1"))
		case Seq("handshake", "notregistered") => Some(HandshakeUnregisteredResult)
		case Seq("log", wrapper, serverId, message) => Some(ServerLogMessage(ManagedServerAddress(wrapper, serverId), message))
		case Seq("server", "add", wrapper, serverId) => Some(AddServerMessage(ManagedServerAddress(wrapper, serverId)))
		case Seq("server", "remove", wrapper, serverId) => Some(RemoveServerMessage(ManagedServerAddress(wrapper, serverId)))
		case _ => None
	}
}