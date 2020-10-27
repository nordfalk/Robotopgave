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
}
