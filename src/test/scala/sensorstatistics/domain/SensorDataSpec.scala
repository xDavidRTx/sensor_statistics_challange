package sensorstatistics.domain

import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class SensorDataSpec extends AnyFunSpecLike with Matchers {

  describe("SensorData toString") {
    it("should output the data in the expected format") {
      SensorData("id1", 50, 100, 0, 200, 5).toString shouldBe "id1,0,50,100"
    }

    it("should handle sensors with only NaN values") {
      SensorData.init("id1").update("NaN").toString shouldBe "id1,NaN,NaN,NaN"
    }
  }

  describe("SensorData update") {
    it("should correctly update the sensor data") {
      val readings   = List("10", "NaN", "98")
      val sensorData = readings.foldLeft(SensorData.init("id1"))((carry, reading) => carry.update(reading))

      sensorData.sensorId shouldBe "id1"
      sensorData.max shouldBe 98
      sensorData.min shouldBe 10
      sensorData.nReadings shouldBe readings.size
      sensorData.nFailedReadings shouldBe 1
      sensorData.humidityAvg shouldBe 54
    }
  }
}
