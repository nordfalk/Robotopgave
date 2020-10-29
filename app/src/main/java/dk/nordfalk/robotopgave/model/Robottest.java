package dk.nordfalk.robotopgave.model;

import static dk.nordfalk.robotopgave.model.Retning.N;

public class Robottest {
    public static void main(String[] args) {
        Rum rum = new Rum(5,5, new Position(1,2, N));

        Robot robot = new Robot(rum);
        System.out.println("robot = " + robot);

        robot.execute("RFRFFRFRF");

        System.out.println("robot = " + robot.getReport());
    }
}
