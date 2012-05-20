package blocklandglass

import java.security.spec.RSAPublicKeySpec

object PubKeyWrangler {
	def pk2str(pk: RSAPublicKeySpec): String = "%d %d" format (pk.getModulus(), pk.getPublicExponent())
}