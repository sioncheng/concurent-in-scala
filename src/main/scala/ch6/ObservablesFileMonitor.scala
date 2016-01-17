package ch6

import rx.lang.scala.Observable
import rx.lang.scala.Subscription
import org.apache.commons.io.monitor._

object ObservablesFileMonitor extends App {
	
	val o = Observable.create[String] {
		observer => 
			val fileMonitor = new FileAlterationMonitor(1000)
			val fileObs = new FileAlterationObserver(".")
			val fileListener = new FileAlterationListenerAdaptor {
				override def onFileChange(file: java.io.File) {
					observer.onNext(file.getName)
				}
			}

			fileObs.addListener(fileListener)
			fileMonitor.addObserver(fileObs)
			fileMonitor.start()

			Subscription {
				println("fileMonitor.stop")
				fileMonitor.stop() 
			}
	}

	val sub = o.subscribe( s => println(s"changed $s"))
	//after sbt run, we can change this file and we will see
	// `changed ObservablesFileMonitor.scala` on console
	Thread.sleep(5000)
	sub.unsubscribe()
}