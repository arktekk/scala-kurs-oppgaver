name := "music"

organization := "no.arktekk"

version := "2.9.1"

libraryDependencies ++= Seq(
  "net.databinder" %% "dispatch-http" % "0.8.7",
  "net.databinder" %% "dispatch-tagsoup" % "0.8.7",
  "org.scalatest" %% "scalatest" % "1.6.1" % "test")