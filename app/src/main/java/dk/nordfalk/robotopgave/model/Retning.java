package dk.nordfalk.robotopgave.model;


/**
 * Retning en 2D robot vender,
 * plus hvordan x og y ændrer sig et 2D koordinatsystem
 * hvis man bevæger sig 1 skridt i den pågældende retning
 */
public enum Retning {
    N(0,-1), E(1,0), S(0,1), W(-1,0);

    private final int dx;
    private final int dy;

    Retning(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Retning tilHøjre() {
        int nyRetning = this.ordinal() + 1;
        return Retning.values()[ nyRetning % 4 ];
    }

    public Retning tilVenstre() {
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
