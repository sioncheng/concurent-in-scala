name := "concurrnt-in-scala"

version := "0.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "io.reactivex" % "rxscala_2.11" % "0.25.1"
libraryDependencies += "commons-io" % "commons-io" % "2.4"
libraryDependencies += "org.scala-stm" %% "scala-stm" % "0.7"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"