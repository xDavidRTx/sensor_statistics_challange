package sensorstatistics.domain

import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers

class SingleReadingSpec extends AnyFunSpecLike with Matchers {

  describe("Single reading fromRawText") {
    it("can parse data correctly") {
      SingleReading.fromRawText("sensor1,90") shouldBe Some(SingleReading("sensor1", "90"))
      SingleReading.fromRawText("sensor1,NaN") shouldBe Some(SingleReading("sensor1", "NaN"))
    }

    it("ignores bad formatted lines") {
      SingleReading.fromRawText("sensor1,90,90") shouldBe None
    }
  }
}
