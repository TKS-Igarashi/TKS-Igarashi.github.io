ThisBuild / scalaVersion := "3.10.0"
ThisBuild / organization := "dev.igam"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalacOptions ++= Seq("-deprecation", "-feature")

// ルートで sbt compile などを実行すると、frontend と backend の両方に対して実行される
lazy val root = project
  .in(file("."))
  .aggregate(frontend, backend)
  .settings(
    name := "portfolio"
  )

lazy val frontend = project
  .in(file("frontend"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "frontend",
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
    },
    libraryDependencies ++= Seq(
      "org.scala-js" %% "scalajs-dom" % "2.8.1",
      "com.raquo" %% "laminar" % "18.0.0-M5",
      "com.raquo" %% "waypoint" % "10.0.0-M7"
    )
  )

lazy val backend = project
  .in(file("backend"))
  .enablePlugins(PlayScala)
  .settings(
    name := "backend",
    libraryDependencies ++= Seq(
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "8.0.0-M2" % Test
    ),
    // sbt 2 では Enter 待ちの入力をうまく受け取れず、起動直後に止まるため、待たないモードにする
    PlayKeys.playInteractionMode := play.sbt.StaticPlayNonBlockingInteractionMode,
    exportJars := false
  )