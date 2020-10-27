package dk.nordfalk.robotopgave.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import static dk.nordfalk.robotopgave.model.Retning.*;

public class Model {

    private static Model instance;

    public static Model get() {
        if (instance == null) instance = new Model();
        return instance;
    }

    public ArrayList<Robottilstand> starttilstande = new ArrayList<>();
    public ArrayList<String> programmer = new ArrayList<>();
    public MutableLiveData<String> programmer_livedata = new MutableLiveData<String>();


    public Model() {
        starttilstande.add(new Robottilstand(new Rum(5,5), new Position(1,2, N)));
        starttilstande.add(new Robottilstand(new Rum(3,3), new Position(1,2, N)));
        starttilstande.add(new Robottilstand(new Rum(5,5), new Position(0,0, E)));
        starttilstande.add(new Robottilstand(new Rum(1,1), new Position(0,0, E)));

        programmer.add("RFRFFRFRF");
        programmer.add("RFLFFLRF");

    }
}
