package ch6

import scala.concurrent._
import ExecutionContext.Implicits.global

import rx.lang.scala.Observable
import rx.lang.scala.Subscription

object ObservableCreateFuture extends App {
	
	//
	val f = Future { "Back to the Futrue" }
	val o = Observable.create[String] {
		obs => f foreach { 
			case s => obs.onNext(s) ; obs.onCompleted()
		}
		f.failed foreach {
			case t => obs.onError(t)
		}
		Subscription()
	}

	o.subscribe( s => println(s))

	val f2 = Future { "Back to the Future 2" }
	val o2 = Observable.from(f2)
	o2.subscribe( s => println(s) )
}