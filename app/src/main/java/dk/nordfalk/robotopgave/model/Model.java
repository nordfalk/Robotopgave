package dk.nordfalk.robotopgave.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static dk.nordfalk.robotopgave.model.Retning.*;

public class Model {

    private static Model instance;

    public static Model get() {
        if (instance == null) instance = new Model();
        return instance;
    }

    public ArrayList<Robot> starttilstande = new ArrayList<>();
    public ArrayList<String> programmer = new ArrayList<>();


    public Model() {
        starttilstande.add(new Robot(new Rum(5,5), new Position(1,2, N)));
        starttilstande.add(new Robot(new Rum(3,3), new Position(1,2, N)));
        starttilstande.add(new Robot(new Rum(5,5), new Position(0,0, E)));
        starttilstande.add(new Robot(new Rum(1,1), new Position(0,0, E)));

        programmer.add("RFRFFRFRF");
        programmer.add("RFLFFLRF");

    }
}
