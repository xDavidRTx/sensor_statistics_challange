package sensor_statistics.domain

import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class SensorDataSpec extends AnyFunSpecLike with Matchers {

  describe("SensorData toString") {
    it("should output the data in the expected format") {
      SensorData("id1", 50, 100, 0, 200, 5).toString shouldBe "id1,0,50,100"
    }
  }

  describe("SensorData update") {
    it("should correctly update the sensor data") {
      val readings = List("29", "30", "17", "70", "NaN", "4000")
      val sensorData = readings.foldLeft(SensorData.init("id1"))((carry, reading) => carry.update(reading))

      sensorData.sensorId shouldBe "id1"
      sensorData.max shouldBe 70
      sensorData.min shouldBe 17
      sensorData.nReadings shouldBe readings.size
      sensorData.nFailedReadings shouldBe 2
      sensorData.humidityAvg shouldBe List(29, 30, 17, 70).sum / 4
    }
  }
}
