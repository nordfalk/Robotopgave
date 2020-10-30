package dk.nordfalk.robotopgave.model;

/** En placering i et rum + retningen robotten vender. Uforanderlig/immutable */
public class Position {
    public final int x;
    public final int y;
    public final Retning retning;

    public Position(int x, int y, Retning retning) {
        this.x = x;
        this.y = y;
        this.retning = retning;
    }

    @Override
    public String toString() {
        return "pos (" + x + ", " + y + ", " + retning +')';
    }
}
