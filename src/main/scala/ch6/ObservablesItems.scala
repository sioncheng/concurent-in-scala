package ch4

import rx.lang.scala.Observable

object ObservablesItems extends App {
	//
	val languages = Array("Pascal", "Java", "Scala")
	val o = Observable.from(languages)
	o.subscribe( name => println(s"learned the $name language") )
	o.subscribe( name => println(s"forgot the $name language") )
}

/*
thanks for GFW, i have to buy vpn for download RxScala library.
*/