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

    public void execute(String program) {
        for (int i=0; i<program.length(); i++) {
            char instruks = program.charAt(i);
            udfør1instruks(instruks);
        }
    }

    private void udfør1instruks(char instruks) {
        Retning retning = position.retning;
        if (instruks=='R') {
            position.retning = retning.drejHøjre();
        } else if (instruks=='L') {
            position.retning = retning.drejVenstre();
        } else if (instruks=='F') {
            position.x += retning.getDx();
            position.y += retning.getDy();
        } else throw new IllegalArgumentException("Ugyldig instruks: '"+instruks+"'");
    }
}
