import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

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
}