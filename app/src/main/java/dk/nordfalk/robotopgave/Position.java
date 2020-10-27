package dk.nordfalk.robotopgave;

public class Position {
    int x;
    int y;
    Retning retning;

    public Position(int x, int y, Retning retning) {
        this.x = x;
        this.y = y;
        this.retning = retning;
    }
}
