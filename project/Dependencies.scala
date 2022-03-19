import sbt._

object Version {
  final val Scala = "2.13.6"
  final val ScalaTest = "3.2.11"
}

object Library {
  val ScalaTest = "org.scalatest" %% "scalatest" % Version.ScalaTest % "test"
}
