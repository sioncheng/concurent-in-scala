package ch6

import rx.lang.scala.Observable
import scala.concurrent.duration._

object ObservablesTimer extends App {
	
	val o = Observable.timer(1.second)
	o.subscribe( _ => println("timeout") )
	o.subscribe( _ => println("another timeout") )

	Thread.sleep(2100)
}