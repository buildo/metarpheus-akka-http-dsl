enablePlugins(spray.boilerplate.BoilerplatePlugin)

lazy val root = project.in(file("."))
  .settings(inThisBuild(
      name := "monadicctrl"
    ),
    name := "monadicctrl",
    scalaVersion := "2.12.1",
    crossScalaVersions := Seq("2.11.8", "2.12.1"),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats" % "0.9.0",
      "com.typesafe.akka" %% "akka-http" % "10.0.1"
    ),
    licenses := Seq(("MIT", url("http://opensource.org/licenses/MIT")))
  )

lazy val example = project
  .dependsOn(root)
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.6.1",
      "io.circe" %% "circe-generic" % "0.6.1",
      "de.heikoseeberger" %% "akka-http-circe" % "1.11.0"
    ),
    scalaVersion := "2.12.1",
    scalacOptions ++= Seq("-language", "reflectiveCalls")
  )

