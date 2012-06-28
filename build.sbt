name := "glass-common"

organization := "blocklandglass"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.9.1"

resolvers += "Nullable.se" at "http://nexus.nullable.se/nexus/content/groups/public/"

publishMavenStyle := true

publishTo <<= (version) { version: String =>
  val nexus = "http://nexus.nullable.se/nexus/content/repositories/"
  if (version.trim.endsWith("-SNAPSHOT")) Some("snapshots" at nexus + "snapshots/") 
  else                                   Some("releases"  at nexus + "releases/")
}

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.2"

libraryDependencies += "org.apache.directory.studio" % "org.apache.commons.codec" % "1.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.7.2" % "test"