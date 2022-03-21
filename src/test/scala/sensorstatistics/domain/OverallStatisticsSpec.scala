package sensorstatistics.domain

import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class OverallStatisticsSpec extends AnyFunSpecLike with Matchers {

  describe("OverallStatisticsSpec update") {
    it("should correctly update") {
      val sensorData1       = SensorData("id1", 33, 90, 2, 55, 3)
      val currentStatistics = OverallStatistics.init
      currentStatistics.update(sensorData1) shouldBe OverallStatistics(55, 3, List(sensorData1))
    }
  }
}
