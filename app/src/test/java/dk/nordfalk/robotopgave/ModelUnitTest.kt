package dk.nordfalk.robotopgave

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ModelUnitTest {
    @Test
    fun udleveretTest() {
        val rum = Rum(5, 5)
        val position = Position(1, 2, Retning.N)
        val robot = Robot(rum, position)
        println("robot = $robot")

        robot.execute("RFRFFRFRF")

        println("robot = " + robot.report)
        assertEquals("1 3 N", robot.report)
    }


    @Test
    fun udleveretTest2() {
        val robot = Robot(Rum(5, 5), Position(0, 0, Retning.E))
        robot.execute("RFLFFLRF")
        println("robot = " + robot.report)
        assertEquals("3 1 E", robot.report)
    }

}