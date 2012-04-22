name := "glass-wrapper-common"

organization := "blocklandglass"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.9.1"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.2" % "test"