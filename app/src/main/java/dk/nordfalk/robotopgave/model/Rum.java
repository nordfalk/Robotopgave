package dk.nordfalk.robotopgave.model;

/**
 * Rum og startposition (og retning) for robot i rummet. Uforanderlig/immutable
 */
public class Rum {
    public final int bredde;
    public final int højde;
    public final Position startposition;

    public Rum(int bredde, int højde, Position startposition) {
        this.bredde = bredde;
        this.højde = højde;
        this.startposition = startposition;
    }

    @Override
    public String toString() {
        return "Rum{" +
                "bredde=" + bredde +
                ", højde=" + højde +
                '}';
    }

    public boolean erLovligPosition(Position pos) {
        return 0<=pos.x && pos.x<bredde &&
               0<=pos.y && pos.y<højde;

    }
}
