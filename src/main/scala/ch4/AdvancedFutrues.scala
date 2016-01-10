package ch5

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

object AdvancedFutures extends App{
	
	val netiquetteUrl = "http://www.douban.com/group/topic/82937314/"
	val netiquette = Future {
		Source.fromURL(netiquetteUrl).mkString
	}

	val urlSpeceUrl = "http://www.douban.com/group/topic/82929143/"
	val urlSpec = Future {
		Source.fromURL(urlSpeceUrl).mkString
	}

	val answer = netiquette.flatMap {
		netiText => urlSpec.map { //map, not flatMap
			urlText => "Check this out : " + netiText + " And check out : " + urlText
		}
	}

	answer.foreach {
		case contents => println(contents)
	}

	val exAnswer = netiquette.recover {
		case e : java.io.FileNotFoundException =>
			"exception happend for answer"
	}

	exAnswer.foreach {
		case contents => println(contents)
	}

	Thread.sleep(10 * 1000)

	println("")
	println("")
	//recover
	val netiquetteUrl2 = "http://www.douban.com/a.txt"
	val netiquette2 = Future {
		Source.fromURL(netiquetteUrl2).mkString
	}

	val answer2 = netiquette2.recover {
		case e : java.io.FileNotFoundException => 
			"exception happend for answer2"
	}

	answer2.foreach{
		case contents => println(contents)
	}

	Thread.sleep(10 * 1000)

}