import sbt._
import Keys._
import ls.Plugin._

object SquerylBuild extends Build {
  
  lazy val squeryl = Project(
      id = "squeryl",
      base = file("."),
      settings = Project.defaultSettings ++ lsSettings ++ Seq(
    		  description := "A Scala ORM and DSL for talking with Databases using minimum verbosity and maximum type safety",
    		  organization := "com.github.aselab",
    		  version := "0.9.5",
    		  parallelExecution := false,
    		  publishMavenStyle := true,
  			  scalaVersion := "2.9.2",
  			  crossScalaVersions := Seq("2.9.2", "2.9.1", "2.9.0-1", "2.9.0", "2.8.1", "2.8.0"),
          crossPaths := false,
  			  licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  			  homepage := Some(url("http://squeryl.org")),
			  pomExtra := (<scm>
			  				<url>git@github.com:max-l/squeryl.git</url>
			  				<connection>scm:git:git@github.com:max-l/squeryl.git</connection>
			  			  </scm>
			  			  <developers>
			      			<developer>
			  					<id>max-l</id>
			  					<name>Maxime Lévesque</name>
			  					<url>https://github.com/max-l</url>
			  	  			</developer>
			  	  			<developer>
			  					<id>davewhittaker</id>
			  					<name>Dave Whittaker</name>
			  					<url>https://github.com/davewhittaker</url>
			  				</developer>
			  			  </developers>),
        publishTo := Some(Resolver.file("file", file("target/publish"))),
        publish ~= {_ =>
          val script = Path.userHome / ".sbt/publish"
          if (script.exists)
            "%s %s".format(script.getAbsolutePath, file("target/publish").getAbsolutePath) !
        },
			  publishArtifact in Test := false,
			  pomIncludeRepository := { _ => false },
			  //below is for lsync, run "ls-write-version", commit to github, then run "lsync" 
			  (LsKeys.tags in LsKeys.lsync) := Seq("sql", "orm", "query", "database", "db", "dsl"),
			  (LsKeys.docsUrl in LsKeys.lsync) := Some(new URL("http://squeryl.org/api/")),
			  (LsKeys.ghUser in LsKeys.lsync) := Some("max-l"),
    		  libraryDependencies ++= Seq(
  					  "cglib" % "cglib-nodep" % "2.2",
  					  "com.h2database" % "h2" % "1.2.127" % "provided",
  					  "mysql" % "mysql-connector-java" % "5.1.10" % "provided",
  					  "postgresql" % "postgresql" % "8.4-701.jdbc4" % "provided",
  					  "net.sourceforge.jtds" % "jtds" % "1.2.4" % "provided",
  					  "org.apache.derby" % "derby" % "10.7.1.1" % "provided",
  					  "junit" % "junit" % "4.8.2" % "provided"),
  			  libraryDependencies <++= scalaVersion { sv =>
  			    Seq("org.scala-lang" % "scalap" % sv,
  			    if(sv startsWith "2.9")
  			     "org.scalatest" % "scalatest_2.9.1" % "1.6.1" % "test"
  			    else
  			      "org.scalatest" % "scalatest_2.8.2" % "1.5.1" % "test")
  			  }))

}
