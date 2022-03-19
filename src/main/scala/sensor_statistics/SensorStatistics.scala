package sensor_statistics

import sensor_statistics.domain.DataLoader.getListOfFiles
import sensor_statistics.domain.{OverallStatistics, SensorData, SingleReading}

import java.io.{File, FileInputStream}
import scala.collection.mutable
import scala.io.Source
import scala.io.StdIn.readLine

object SensorStatistics extends App {
  lazy val overallStatistics = sensorDataMap.toList.foldLeft(OverallStatistics.init) {
    case (stats, (_, data)) =>
      stats.update(data)
  }

  val path: String =
    if (args.length == 1) args(0)
    else {
      print("Please insert the path of the data directory: ")
      readLine()
    }
  val sensorDataMap = mutable.HashMap[String, SensorData]()
  getListOfFiles(path).foreach(iterateFile)

  overallStatistics.sensors.sorted(SensorData.ordering).foreach(println)

  def iterateFile(file: File): Unit =
    Source
      .fromInputStream(new FileInputStream(file))
      .getLines()
      .flatMap(SingleReading.fromRawText)
      .map(
        parsedLine =>
          sensorDataMap.getOrElse(parsedLine.sensorId, SensorData.init(parsedLine.sensorId)).update(parsedLine.humidity)
      )
      .foreach { updatedSensorInfo =>
        sensorDataMap.update(updatedSensorInfo.sensorId, updatedSensorInfo)
      }

  println(s"Num of processed files: ")
  println(s"Num of processed measurements: ${overallStatistics.nProcessed}")
  println(s"Num of failed measurements: ${overallStatistics.nFailures}")

  println("\nSensors with highest avg humidity:")
  println("\nsensor-id,min,avg,max")
}
