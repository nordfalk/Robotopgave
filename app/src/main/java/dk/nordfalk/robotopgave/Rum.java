package dk.nordfalk.robotopgave;

public class Rum {
    int bredde;
    int højde;

    public Rum(int bredde, int højde) {
        this.bredde = bredde;
        this.højde = højde;
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
