package ch6

import rx.lang.scala.Observable
import rx.lang.scala.Subscription

object ObservablesCreate extends App {
	
	val vms = Observable.create[String] {
		obs => obs.onNext("JVM"); obs.onNext("DartVM"); obs.onNext("V8"); obs.onCompleted()
		Subscription()
	}

	vms.subscribe(println _ , e => println(s"oops - $e", e), () => println("Done"))
}