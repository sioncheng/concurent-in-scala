package ch2

object ThreadsCommunicate extends App {
	
	var result : String = ""

	val t = ThreadUtil.thread( { result = " I am in thread " +  Thread.currentThread.getName } )

	t.join()

	println(result)
}