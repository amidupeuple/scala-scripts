package com.pesiykot

import java.io.File

object FilesActions extends App {
  val root: String = "C:/gogs/wfmt-c"
  val rootFile: File = new File(root)

  val f: (File => Unit)

  def recursivelyWalkThroughDir(root: File, fileHandler: File => Unit): Unit = {
    root.listFiles.filter(_.isFile).foreach(fileHandler)
    root.listFiles.filter(_.isDirectory).foreach(f => recursivelyWalkThroughDir(f, fileHandler))
  }
}
