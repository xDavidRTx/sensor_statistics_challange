package sensor_statistics.domain

import scala.util.Try

case class SensorData(
    sensorId: String,
    humidityAvg: Int,
    max: Int,
    min: Int,
    nReadings: BigInt,
    nFailedReadings: BigInt
) {
  def update(humidity: String): SensorData =
    Try(humidity.toInt).toOption.filter(0 to 100 contains _).fold(failedReading) { humidity =>
      val newAvg: Int = ((humidityAvg * nReadings + humidity) / (nReadings + 1)).toInt
      val newMax      = if (humidity > max) humidity else max
      val newMin      = if (humidity < min) humidity else min
      this.copy(humidityAvg = newAvg, nReadings = nReadings + 1, max = newMax, min = newMin)
    }

  def failedReading: SensorData = this.copy(nFailedReadings = nFailedReadings + 1, nReadings = nReadings + 1)

  override def toString: String = if (nReadings > 1) s"$sensorId,$min,$humidityAvg,$max" else s"$sensorId,NaN,NaN,NaN"
}

object SensorData {
  val ordering: Ordering[SensorData] = Ordering.by(-_.humidityAvg)

  def init(sensorId: String): SensorData =
    SensorData(sensorId = sensorId, max = 0, min = 100, humidityAvg = 0, nReadings = 0, nFailedReadings = 0)
}
