package dk.nordfalk.robotopgave;

public class Robot {
    Rum rum;
    Position position;

    public Robot(Rum rum, Position position) {
        this.rum = rum;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "rum=" + rum +
                ", position=" + position +
                '}';
    }
}
