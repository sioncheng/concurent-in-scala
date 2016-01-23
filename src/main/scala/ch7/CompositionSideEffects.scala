package ch7

import scala.concurrent.stm._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import util.Util

object CompositionSideEffects extends App {
	
	val myVal = Ref(0)

	def inc() = atomic {
		implicit txn =>
			Util.log(s"incrementing ${myVal()}")
			myVal() = myVal() + 1
	}

	def get() = atomic {
		implicit txt => myVal()
	}

	Future { inc() }
	Future { inc() }
	
	Thread.sleep(1000)

	Util.log(s"final value ${get()}")

	/*
	after running above code, we might get 
	[info] Running ch7.CompositionSideEffects
	[ForkJoinPool-1-worker-3]    incrementing 0
	[ForkJoinPool-1-worker-5]    incrementing 0
	[ForkJoinPool-1-worker-5]    incrementing 1
	[run-main-0]    final value 2
	
	that two lines of `[ForkJoinPool-1-worker-5]` show the re-execution of STM and
	the side effects of log

	*/

	def incWithNoEffects() = atomic {
		implicit txn =>
			val valAtStart = myVal()
			Txn.afterCommit {
				_ => Util.log(s"incrementing $valAtStart")
				//to ensure the log is no side effect
			}
			myVal() = myVal() + 1
	}
	Future { incWithNoEffects() }
	Future { incWithNoEffects() }
	
	Thread.sleep(1000)

	Util.log(s"final value ${get()}")

	/*
	and we might get
	[ForkJoinPool-1-worker-3]    incrementing 2
	[ForkJoinPool-1-worker-5]    incrementing 3
	[run-main-0]    final value 4
	*/
}