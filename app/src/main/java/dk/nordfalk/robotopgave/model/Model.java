package dk.nordfalk.robotopgave.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import static dk.nordfalk.robotopgave.model.Retning.*;

public class Model {

    private static Model instance;
    public MutableLiveData<String> valgtProgram = new MutableLiveData<String>("");
    public MutableLiveData<Rum> valgtRum = new MutableLiveData<>();

    public static Model get() {
        if (instance == null) instance = new Model();
        return instance;
    }

    public ArrayList<Rum> rum = new ArrayList<>();
    public ArrayList<String> programmer = new ArrayList<>();
    public MutableLiveData<String> programmer_livedata = new MutableLiveData<String>();


    public Model() {
        rum.add(new Rum(5,5, new Position(1,2, N)));
        rum.add(new Rum(3,3, new Position(1,2, N)));
        rum.add(new Rum(5,5, new Position(0,0, E)));
        rum.add(new Rum(1,1, new Position(0,0, E)));

        programmer.add("RFRFFRFRF");
        programmer.add("RFLFFLRF");

    }
}
