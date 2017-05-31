import play.Project._

name := """iwm_play"""

version := "1.0-SNAPSHOT"

playScalaSettings

libraryDependencies ++= Seq(
  "org.xerial" % "sqlite-jdbc" % "3.8.10.1",
  jdbc
)