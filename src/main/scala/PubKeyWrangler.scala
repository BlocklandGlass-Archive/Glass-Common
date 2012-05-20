package blocklandglass

import java.security.spec.RSAPublicKeySpec

object PubKeyWrangler {
	def pk2str(pk: RSAPublicKeySpec): String = "%d %s" format (pk.getModulus(), pk.getPublicExponent())
}