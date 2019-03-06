package com.pesiykot

import java.io.{BufferedWriter, File, FileWriter}
import java.nio.charset.MalformedInputException

import scala.io.{BufferedSource, Source}

object FilesActions extends App {
  val root: String = "C:/gogs/wfmt-c"
  //val root: String = "C:/Temp/test_replace"
  val rootFile: File = new File(root)



  val replacePath = (file: File, findToken: String, replaceToken: String) => {
    var src: BufferedSource = null
    var srcLines: List[String] = null
    var bufferList = List[String]()

    try {
      src = Source.fromFile(file)("UTF-8")
      srcLines = src.getLines().toList
      if (srcLines.find(_.contains(findToken)).isDefined) {
        bufferList = srcLines.map(_.replace(findToken, replaceToken)).toList
      }
    } catch {
      case e: MalformedInputException => {
        src.close
        bufferList = List[String]()
        src = Source.fromFile(file)("ISO-8859-1")
        srcLines = src.getLines().toList
        if (srcLines.find(_.contains(findToken)).isDefined) {
          bufferList = srcLines.map(_.replace(findToken, replaceToken)).toList
        }
      }
    } finally {
      src.close
    }

    if (!bufferList.isEmpty) {
      file.setWritable(true)
      val bw = new BufferedWriter(new FileWriter(file))
      try {
        bw.write(bufferList.mkString("\n"))
      } finally {
        bw.close()
      }
    }
  }


  recursivelyWalkThroughDir(rootFile, replacePath)

  def recursivelyWalkThroughDir(root: File, fileHandler: (File, String, String) => Unit): Unit = {
    root.listFiles.filter(_.isFile).filter(!_.getName.contains(".copyarea.db")).foreach(f => fileHandler(f, "/aspekt/entw/ars_home", "/apps/wmsti/ar_api9/"))
    root.listFiles.filter(_.isFile).filter(!_.getName.contains(".copyarea.db")).foreach(f => fileHandler(f, "/aspekt/entw/oracle_home", "/apps/wmsti/oracle_client12/"))
    root.listFiles.filter(_.isFile).filter(!_.getName.contains(".copyarea.db")).foreach(f => fileHandler(f, "/aspekt/entw/mqs_home", "/opt/mqm/"))
    root.listFiles.filter(_.isDirectory).filter(!_.getName.contains(".git")).foreach(f => recursivelyWalkThroughDir(f, fileHandler))
  }
}
