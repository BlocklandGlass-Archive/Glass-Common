package object blocklandglass {
	def using[A <: { def close(): Unit }, B](a: A)(f: A => B): B = {
		try {
			f(a)
		} finally {
			a.close()
		}
	}
}