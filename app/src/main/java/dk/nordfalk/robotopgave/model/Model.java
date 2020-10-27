package dk.nordfalk.robotopgave.model;

import java.util.ArrayList;

import static dk.nordfalk.robotopgave.model.Retning.*;

public class Model {
    public ArrayList<Rum> rum = new ArrayList<>();
    public ArrayList<Position> positioner = new ArrayList<>();
    public ArrayList<String> programmer = new ArrayList<>();


    public Model() {
        rum.add(new Rum(5,5));
        rum.add(new Rum(1,1));

        positioner.add(new Position(1,2, N));
        positioner.add(new Position(0,0, E));

        programmer.add("RFRFFRFRF");
        programmer.add("RFLFFLRF");

    }
}
