package sensorstatistics.domain

import java.io.File

object DataLoader {
  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(f => f.isFile && f.getName.contains(".csv")).toList
    } else {
      List.empty[File]
    }
  }
}
