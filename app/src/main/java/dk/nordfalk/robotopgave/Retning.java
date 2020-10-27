package dk.nordfalk.robotopgave;

public enum Retning {
    N(0,-1), E(1,0), S(0,1), W(-1,0);

    private final int dx;
    private final int dy;

    Retning(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Retning drejHøjre() {
        int nyRetning = this.ordinal() + 1;
        return Retning.values()[ nyRetning % 4 ];
    }

    public Retning drejVenstre() {
        int nyRetning = this.ordinal() -1 + 4;
        return Retning.values()[ nyRetning % 4 ];
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
