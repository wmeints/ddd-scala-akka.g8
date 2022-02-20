ThisBuild / organization := "$organization$"
ThisBuild / scalaVersion := "2.13.8"

val AkkaVersion = "2.6.18"
val AkkaHttpVersion = "10.2.8"
val SlickVersion = "3.3.3"
val AkkaHttpJsonVersion = "1.39.2"
val CirceVersion = "0.14.1"

val MainClassName = Some("$package$.Main")

lazy val root = (project in file("."))
  .settings(
    name := "$name$",
    version := "$version$",
    libraryDependencies ++= Seq(
      // Actors
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,

      // HTTP
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
      "de.heikoseeberger" %% "akka-http-circe" % AkkaHttpJsonVersion,

      // JSON support
      "io.circe" %% "circe-core" % CirceVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "io.circe" %% "circe-parser" % CirceVersion,

      // Persistence
      "com.typesafe.akka" %% "akka-persistence-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-persistence-query" % AkkaVersion,
      "com.typesafe.akka" %% "akka-serialization-jackson" % AkkaVersion,
      "com.lightbend.akka" %% "akka-persistence-jdbc" % "5.0.4",
      "com.typesafe.akka" %% "akka-persistence-testkit" % AkkaVersion % Test,
      "com.lightbend.akka" %% "akka-projection-slick" % "1.2.3",
      "com.typesafe.akka" %% "akka-persistence-query" % AkkaVersion,
      "com.typesafe.slick" %% "slick" % SlickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
      "org.postgresql" % "postgresql" % "42.3.3",

      // Projection
      "com.lightbend.akka" %% "akka-projection-core" % "1.2.3",
      "com.lightbend.akka" %% "akka-projection-eventsourced" % "1.2.3",
      "com.lightbend.akka" %% "akka-projection-slick" % "1.2.3",
      "com.lightbend.akka" %% "akka-projection-jdbc" % "1.2.3",

      // Logging
      "ch.qos.logback" % "logback-classic" % "1.2.9"
    )
  )
