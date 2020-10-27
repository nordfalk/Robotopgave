package dk.nordfalk.robotopgave;

/** En placering i et rum + retningen robotten vender */
public class Position {
    int x;
    int y;
    Retning retning;

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
