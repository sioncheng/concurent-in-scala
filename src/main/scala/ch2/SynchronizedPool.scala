package ch2

import scala.collection._

object SynchronizedPool extends App {
	
	private val tasks = mutable.Queue[() => Unit]()

	object Worker extends Thread {
		setDaemon(true)

		def poll() = tasks.synchronized {
			while (tasks.isEmpty) tasks.wait()
			tasks.dequeue()
		}

		override def run() = {
			while (true) {
				val task = poll()
				task()
			}
		}
	}

	def sendTaskAsync(body : => Unit) {
		tasks.synchronized {
			tasks.enqueue ( () => body )
			tasks.notify()
		}
	}

	Worker.start()

	sendTaskAsync( println("hello") )
	sendTaskAsync( println("world") )

	val t1 = ThreadUtil.thread( {
		sendTaskAsync( println("hello 1") )
		sendTaskAsync( println("world 1") )
	} )

	val t2 = ThreadUtil.thread( {
		sendTaskAsync( println("hello 2") )
		sendTaskAsync( println("world 2") )
	} )

	Thread.sleep(1000)

	t1.join()
	t2.join()
}