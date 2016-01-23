package util

object Util {

	def log(s: String) {
		val currentThreadName = Thread.currentThread.getName()
		println(s"[$currentThreadName]    $s")
	}
}