import org.scalajs.linker.interface.ModuleSplitStyle

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "frontend",
    scalaVersion := "3.10.0",
    scalacOptions ++= Seq("-encoding", "utf-8", "-deprecation", "-feature"),
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(ModuleSplitStyle.SmallModulesFor(List("example")))
    },

    libraryDependencies ++= Seq(
      "org.scala-js" %% "scalajs-dom" % "2.8.1",
      "com.raquo" %% "laminar" % "18.0.0-M5",
      "com.raquo" %% "waypoint" % "10.0.0-M7"
    )
  )
