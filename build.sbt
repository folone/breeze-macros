name := "breeze-macros"

version := "0.3-SNAPSHOT"

organization := "org.scalanlp"

scalaVersion := "2.10.3"

crossScalaVersions := Seq("2.11.0-RC1")

resolvers ++= Seq(
  "ScalaNLP Maven2" at "http://repo.scalanlp.org/repo",
  "Scala Tools Snapshots" at "http://scala-tools.org/repo-snapshots/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.5" % "test"
)

libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
  sv match {
    case x if x.startsWith("2.10") =>
      deps :+ ("org.scalamacros" % "quasiquotes" % "2.0.0-M3" cross CrossVersion.full)
    case _ => deps
  }
}

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.11.3" % "test",
  "org.scalatest"  %% "scalatest"  % "2.1.0"  % "test"
)

libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-reflect" % _)

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")


scalacOptions ++= Seq("-deprecation", "-language:_", "-optimize")

javaOptions += "-Xmx2g"


addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M3" cross CrossVersion.full)

pomExtra := (
    <url>http://scalanlp.org/</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:dlwh/breeze.git</url>
      <connection>scm:git:git@github.com:dlwh/breeze.git</connection>
    </scm>
    <developers>
      <developer>
        <id>dlwh</id>
        <name>David Hall</name>
        <url>http://cs.berkeley.edu/~dlwh/</url>
      </developer>
    </developers>)



  pomIncludeRepository := { _ => false }

publishMavenStyle := true


  publishTo <<= version { (v: String) =>
    val nexus = "https://oss.sonatype.org/"
    if (v.trim.endsWith("SNAPSHOT")) 
      Some("snapshots" at nexus + "content/repositories/snapshots") 
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  }

