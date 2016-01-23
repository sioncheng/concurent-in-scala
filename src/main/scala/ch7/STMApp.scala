package ch7

import scala.concurrent.stm._
import scala.collection.immutable
import ch2.ThreadUtil

class ThreadSafeList[T] {

	val list = Ref[List[T]](Nil)
	//it is not good enough to use immutable list
	//but the point is to use STM for thread safe operations
	
	def add(x: T) { 
		atomic { implicit txn =>
			list() = x :: list()
		}
	}

	def size() = {
		atomic { implicit txn =>
			list().size
		}
	}
}

object STMApp extends App {
	
	val tsl = new ThreadSafeList[String]()
	for (i <- 1 to 10) {
		ThreadUtil.thread( { tsl.add("hello") ; println(s"hello $i") } )
	}

	Thread.sleep(2000)
	println(tsl.size)
	/*
	after running, we might get below 
	[info] Running ch7.STMApp
	hello 3
	hello 2
	hello 9
	hello 8
	hello 7
	hello 5
	hello 4
	hello 6
	hello 1
	hello 10
	10
	*/
}