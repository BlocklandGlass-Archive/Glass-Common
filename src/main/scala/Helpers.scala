package blocklandglass

import akka.actor._
import akka.util.ByteString

class SocketWrapper(socket: IO.WriteHandle) {
	def write(message: messages.Message) {
		socket write ByteString(message.serializeToString, "UTF-8")
	}
}

package object blocklandglass {
	implicit def writehandle2socketwrapper(socket: IO.WriteHandle) = new SocketWrapper(socket)
}