package ch3

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
import scala.util.Failure

object FuturePromise extends App {
	
	//simple futrue
	val sf : Future[String] = Future { "hello world" }

	println(sf.value)
	Thread.sleep(500)
	println(sf.value)

	//has delay
	val sf2 : Future[String] = Future { Thread.sleep(200) ; "hello world" }

	println(sf2.value)
	Thread.sleep(300)
	println(sf2.value)

	//call back
	val sf3 : Future[String] = Future { 
		Thread.sleep(100)
		if (System.currentTimeMillis % 2 == 0) {
			"hello world"
		} else {
			throw new Exception("what?")
		}
	}

	sf3 onComplete {
		case Success(posts) => println(posts) ///for (post <- posts) println(post)
		case Failure(t) => println("failure : " + t.getMessage)
	}

	Thread.sleep(300)
}