import Dependencies._

ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val scalacheckVersion = "1.16.0"

lazy val chapter1 = (project in file("chapter1"))
  .settings(
    name := "Chapter 1",
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test",
      scalaTest % Test
    )
  )

lazy val chapter2 = (project in file("chapter2"))
  .settings(
    name := "Chapter 2",
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test",
      scalaTest % Test
    )
  )

lazy val chapter3 = (project in file("chapter3"))
  .settings(
    name := "Chapter 3",
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test",
      scalaTest % Test
    )
  )

lazy val chapter4 = (project in file("chapter4"))
  .settings(
    name := "Chapter 4",
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test",
      scalaTest % Test
    )
  )

lazy val chapter5 = (project in file("chapter5"))
  .settings(
    name := "Chapter 5",
    libraryDependencies ++= Seq(
      "org.scalacheck" %% "scalacheck" % scalacheckVersion % "test",
      scalaTest % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
