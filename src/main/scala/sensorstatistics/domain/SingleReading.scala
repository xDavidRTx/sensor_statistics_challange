package sensorstatistics.domain

case class SingleReading(sensorId: String, humidity: String)

object SingleReading {
  def fromRawText(raw: String): Option[SingleReading] =
    raw.split(",") match {
      case Array(id, humidity) => Some(SingleReading(id, humidity))
      case _                   => None
    }
}
