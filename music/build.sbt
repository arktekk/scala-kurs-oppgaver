name := "music"

organization := "no.arktekk"

version := "2.10.3"

libraryDependencies ++= Seq(
  "net.databinder" %% "dispatch-http" % "0.8.10",
  "net.databinder" %% "dispatch-tagsoup" % "0.8.10",
  "org.scalatest" %% "scalatest" % "2.0" % "test")