package dk.nordfalk.robotopgave;

import static dk.nordfalk.robotopgave.Retning.N;

public class Robottest {
    public static void main(String[] args) {
        Rum rum = new Rum(5,5);

        Position position = new Position(1,2, N);

        Robot robot = new Robot(rum, position);
        System.out.println("robot = " + robot);

        //robot.execute("RFRFFRFRF");

    }
}
