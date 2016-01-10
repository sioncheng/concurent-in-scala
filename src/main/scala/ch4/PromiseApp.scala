package ch4

import scala.concurrent.Promise
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object PromiseApp extends App {
	
	val p = Promise[String]
	val q = Promise[String]

	p.future foreach {
		case x => println(s"successed with $x")
	}
	p.success("assigned")

	q.future.failed foreach {
		case x => println(s"failed with $x")
	}
	q.failure(new Exception("cant do it"))

	Thread.sleep(2000)
}