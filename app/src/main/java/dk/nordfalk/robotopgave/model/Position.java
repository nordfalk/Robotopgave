package dk.nordfalk.robotopgave.model;

/** En placering i et rum + retningen robotten vender. Uforanderlig/immutable */
public class Position {
    final int x;
    final int y;
    final Retning retning;

    public Position(int x, int y, Retning retning) {
        this.x = x;
        this.y = y;
        this.retning = retning;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", retning=" + retning +
                '}';
    }
}
