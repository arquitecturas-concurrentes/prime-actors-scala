name := "primeActors"

version := "1.0"

scalaVersion := "2.10.3"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"org.scalatest" %% "scalatest" % "2.0" % "test",
	"com.typesafe.akka" %% "akka-actor" % "2.2.3",
	"com.typesafe.akka" %% "akka-testkit" % "2.2.3",
	"com.novocode" % "junit-interface" % "0.9" % "test"
)

EclipseKeys.withSource := true
	
 


