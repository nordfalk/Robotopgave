package dk.nordfalk.robotopgave

import dk.nordfalk.robotopgave.model.*
import dk.nordfalk.robotopgave.model.Retning.*
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
        val rum = Rum(5, 5, Position(1, 2, N))
        val robot = Robot(rum)
        println("robot = $robot")

        robot.execute("RFRFFRFRF")

        println("robot = " + robot.report)
        assertEquals("1 3 N", robot.report)
    }


    @Test
    fun forLidtPlads() {
        val robot = Robot(Rum(1, 1, Position(0, 0, N)))
        robot.execute("RFRFFRFRF")
        println("robot = " + robot.report)
        assertEquals("0 0 N", robot.report)
    }



    @Test
    fun udleveretTest2() {
        val robot = Robot(Rum(5, 5, Position(0, 0, E)))
        robot.execute("RFLFFLRF")
        println("robot = " + robot.report)
        assertEquals("3 1 E", robot.report)
    }


    @Test
    fun forLidtPlads2() {
        val robot = Robot(Rum(1, 1, Position(0, 0, E)))
        robot.execute("RFLFFLRF")
        println("robot = " + robot.report)
        assertEquals("0 0 E", robot.report)
    }



    @Test
    fun standardværdierIModel() {
        val m = Model()
        val robot = Robot(m.rum[0])
        robot.execute(m.programmer[0])
        println("robot = " + robot.report)
        assertEquals("1 3 N", robot.report)
    }


}