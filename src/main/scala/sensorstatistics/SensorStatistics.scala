package sensorstatistics

import sensorstatistics.domain.DataLoader.getListOfFiles
import sensorstatistics.domain.{OverallStatistics, SensorData, SingleReading}

import java.io.{File, FileInputStream}
import scala.collection.mutable
import scala.io.Source
import scala.io.StdIn.readLine

object SensorStatistics extends App {
  lazy val overallStatistics = sensorDataMap.view.foldLeft(OverallStatistics.init) {
    case (carryOver, (_, sensorData)) =>
      carryOver.update(sensorData)
  }
  val sensorDataMap = mutable.HashMap.empty[String, SensorData]
  val path: String =
    if (args.length >= 1) args(0)
    else {
      print("Please insert the path of the data directory: ")
      readLine()
    }

  val files = getListOfFiles(path)

  files.foreach(iterateFile)

  println(s"Num of processed files: ${files.size}")
  println(s"Num of processed measurements: ${overallStatistics.nProcessed}")
  println(s"Num of failed measurements: ${overallStatistics.nFailures}")

  println("\nSensors with highest avg humidity:")
  println("\nsensor-id,min,avg,max")
  overallStatistics.sensors.sorted(SensorData.ordering).foreach(println)

  def iterateFile(file: File): Unit =
    Source
      .fromInputStream(new FileInputStream(file))
      .getLines()
      .drop(1)
      .flatMap(SingleReading.fromRawText)
      .map(
        parsedLine =>
          sensorDataMap.getOrElse(parsedLine.sensorId, SensorData.init(parsedLine.sensorId)).update(parsedLine.humidity)
      )
      .foreach { updatedSensorInfo =>
        sensorDataMap.update(updatedSensorInfo.sensorId, updatedSensorInfo)
      }
}
