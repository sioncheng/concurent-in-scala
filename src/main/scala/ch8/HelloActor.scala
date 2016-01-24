package ch8

import akka.actor._
import akka.event.Logging

class HelloActor(val hello: String) extends Actor {
	val log = Logging(context.system, this)

	def receive = {
		case `hello` =>
			log.info(s"received a '$hello'...$hello")
		case msg =>
			log.info(s"unexpected message '$msg'")
			context.stop(self)
	}
}

object HelloActor {
	def props(hello: String) = Props(new HelloActor(hello))
	def propsAlt(hello: String) = Props(classOf[HelloActor], hello)
}

object HelloActorApp extends App {
	lazy val ourSystem = ActorSystem("OurExampleSystem")

	val hiActor : ActorRef = ourSystem.actorOf(HelloActor.props("hi"), "greeter")

	hiActor ! "hi"

	Thread.sleep(1000)

	hiActor ! "hello"

	Thread.sleep(1000)

	ourSystem.shutdown()
}