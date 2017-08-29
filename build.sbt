name := """slick-jooq"""

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
	"com.typesafe.slick" %% "slick" % "3.2.1",
	"com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
	"com.typesafe.slick" %% "slick-codegen" % "3.2.1",
	"org.slf4j" % "slf4j-nop" % "1.6.4",
	"com.h2database" % "h2" % "1.4.196",
	"org.jooq" % "jooq" % "3.9.5",
	"org.jooq" % "jooq-meta" % "3.9.5",
	"org.jooq" % "jooq-codegen" % "3.9.5",
)

val slick: TaskKey[Unit] = taskKey[Unit]("slick")
slick := {
	val outputDir = "src/main/scala"
	val url = "jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1;INIT=runscript from 'src/main/resources/create.sql'"
	val jdbcDriver = "org.h2.Driver"
	val slickDriver = "slick.jdbc.H2Profile"
	val pkg = ""
	(runner in Compile).value.run(
		"slick.codegen.SourceCodeGenerator",
		(dependencyClasspath in Compile).value.files,
		Array(slickDriver, jdbcDriver, url, outputDir, pkg),
		streams.value.log
	)
	val fname = outputDir + "Tables.scala"
	Seq(file(fname))
}

val jooq: TaskKey[Unit] = TaskKey[Unit]("jooq")
jooq := {
	val jooqConf = "src/main/resources/jooq.xml"
	(runner in Compile).value.run("org.jooq.util.GenerationTool", (dependencyClasspath in Compile).value.files, List(jooqConf), streams.value.log)
}