package blocklandglass.messages

import akka.actor._
import akka.util._

import blocklandglass._

trait Message {
	def serialize: Seq[String]
	def serializeToString: String = serialize.mkString("\t")+"\r\n"
}

trait MessageReader[T] {
	def read(action: T => Unit): IO.Iteratee[Unit] = IO repeat {
		for (message <- readMessage)
		yield action(message)
	}
	def readMessage: IO.Iteratee[T] = for {
		message <- IO takeUntil ByteString("\r\n", "UTF-8")
	} yield parse(message.decodeString("UTF-8").split("\t"))
	protected def parse(header: Seq[String]): T
}