name := "glass-common"

organization := "blocklandglass"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.9.1"

resolvers += "Nullable Archiva" at "http://archiva.nullable.se/repository/internal/"

resolvers += "Nullable Archiva Snapshots" at "http://archiva.nullable.se/repository/snapshots/"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.2" % "test"