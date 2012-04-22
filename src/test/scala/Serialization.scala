import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import akka.actor.IO
import akka.util.ByteString

import blocklandglass.messages._

class MessageSerializationSpec extends FlatSpec with ShouldMatchers {
	object TestingMessage extends Message {
		def serialize = Seq("a", "b", "c")
	}

	"Serialization of a message" should "result in a conforming string" in {
		TestingMessage.serializeToString should equal ("a\tb\tc\r\n")
	}
	"A handshake result" should "serialize properly" in {
		wrappers.HandshakeResult(true).serialize should equal (Seq("handshake", "result", "1"))
		wrappers.HandshakeResult(false).serialize should equal (Seq("handshake", "result", "0"))
	}

	"A serialized handshake result" should "deserialize properly" in {
		var called = false
		val reader = for (message <- wrappers.S2WMessageReader.read) yield {
			message should equal (Some(wrappers.HandshakeResult(true)))
			called = true
		}
		reader(IO Chunk ByteString(wrappers.HandshakeResult(true).serializeToString, "UTF-8"))
		
		called should equal (true)
	}
}