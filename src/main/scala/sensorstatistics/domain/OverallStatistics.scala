package sensorstatistics.domain

case class OverallStatistics(nProcessed: BigInt, nFailures: BigInt, sensors: List[SensorData] = Nil) {
  def update(sensorData: SensorData): OverallStatistics =
    this.copy(
      nProcessed = nProcessed + sensorData.nReadings,
      nFailures = nFailures + sensorData.nFailedReadings,
      sensors = sensorData :: sensors
    )
}

object OverallStatistics {
  def init: OverallStatistics = OverallStatistics(0, 0)
}
