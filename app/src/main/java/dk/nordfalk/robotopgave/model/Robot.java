package dk.nordfalk.robotopgave.model;

public class Robot {
    private Rum rum;
    private Position position;

    public Robot(Rum rum) {
        this.rum = rum;
        this.position = rum.startposition;
        if (!rum.erLovligPosition(position)) throw new IllegalArgumentException("Ulovlig position "+position+" i rum "+rum);
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
            position = new Position(position.x, position.y, retning.tilHøjre());
        } else if (instruks=='L') {
            position = new Position(position.x, position.y, retning.tilVenstre());
        } else if (instruks=='F') {
            Position nyPos = new Position(position.x + retning.getDx(), position.y + retning.getDy(), retning);
            if (rum.erLovligPosition(nyPos)) {
                position = nyPos;
            } else {
                System.err.println("Robot kunne ikke gå mod "+retning+", da ny position ikke er lovlig i "+rum);
            }
        } else throw new IllegalArgumentException("Ugyldig instruks: '"+instruks+"'");
    }

    public String getReport() {
        return position.x + " " + position.y + " " + position.retning;
    }

    public Rum getRum() {
        return rum;
    }

    public Position getPosition() {
        return position;
    }
}
