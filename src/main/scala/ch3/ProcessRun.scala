package ch3

import scala.sys.process._

object ProcessRun extends App {
	
	val command = "ls"
	val exitcode = command.! 
	/*
	importing the contents of the scala.sys.process package
	allows us to call the ! method on any string. The shell
	command represented by the string is then run from the
	working directory of the current process.
	*/
	println(s"command exited with status $exitcode")
}