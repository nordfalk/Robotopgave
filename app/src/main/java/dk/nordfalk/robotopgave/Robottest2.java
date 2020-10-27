package dk.nordfalk.robotopgave;

import static dk.nordfalk.robotopgave.Retning.*;

public class Robottest2 {
    public static void main(String[] args) {
        Rum rum = new Rum(5,5);

        Position position = new Position(0,0, E);

        Robot robot = new Robot(rum, position);
        System.out.println("robot = " + robot);

        robot.execute("RFLFFLRF");

        System.out.println("robot = " + robot.getReport());
    }
}
