package dk.nordfalk.robotopgave.model;

/**
 * Rum og startposition (og retning) for robot i rummet. Uforanderlig/immutable
 */
public class Rum {
    public final int bredde;
    public final int højde;
    public final Position startposition;
    public static final Rum ILLEGAL = new Rum(1,1, new Position(0, 0, Retning.N));

    public Rum(int bredde, int højde, Position startposition) {
        this.bredde = bredde;
        this.højde = højde;
        this.startposition = startposition;
    }

    @Override
    public String toString() {
        return "Rum " + bredde + "x" + højde;
    }

    public boolean erLovligPosition(Position pos) {
        return 0<=pos.x && pos.x<bredde &&
               0<=pos.y && pos.y<højde;

    }
}
