package scalaslides

object LatexToPdf {  // assumed commands on path: pdflatex, tail
  import scala.sys.process.Process

  def apply(texFile: String, workDir: String = "tex"): Unit = {
    val basePath = new java.io.File( "." ).getCanonicalPath()
    val workPath = s"$basePath/$workDir"
    val texPath = s"$workPath/$texFile"
    val logFile = texFile.replace(".tex", "-console.log")

    println(s" ******* Compiling $workDir/$texFile to pdf *******")
    val cmd = scala.sys.process.Process(
      Seq("pdflatex","-halt-on-error", texPath),
      new java.io.File(workPath)
    )
    val logPath =  s"""$workPath/$logFile"""
    if (new java.io.File(logPath).createNewFile) println(s"Created: $logPath")
    val exitValue = cmd.#>(new java.io.File(logPath)).run.exitValue
    if (exitValue != 0) {
      println("*** ############ ERROR LOG STARTS HERE ############### ***")
      scala.sys.process.Process(
        Seq("tail", "-40", logPath),
        new java.io.File(workDir)
      ).run
      sys.error(s"\n*** ERROR: pdflatex output in: $logPath")
    } else println(s"             Log file: $workDir/$logFile")
  }
}
