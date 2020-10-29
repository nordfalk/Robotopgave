package dk.nordfalk.robotopgave.model;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import static dk.nordfalk.robotopgave.model.Retning.*;

public class Model {

    // Singleton
    private static Model instance;

    public static Model get() {
        if (instance == null) instance = new Model();
        return instance;
    }

    // Reinstantiering i frisk JVM
    public static Boolean instantieret() {
        return instance != null;
    }

    public void setFraModel(Model model) {
        rum = model.rum;
        programmer = model.programmer;
        valgtProgram.setValue("");
        valgtRum.setValue(null);
    }


    // Lister
    public ArrayList<Rum> rum = new ArrayList<>();
    public ArrayList<String> programmer = new ArrayList<>();
    public transient MutableLiveData<String> programmerObserver = new MutableLiveData<String>();

    // Brugervalg
    public transient MutableLiveData<String> valgtProgram = new MutableLiveData<String>("");
    public transient MutableLiveData<Rum> valgtRum = new MutableLiveData<>();


    public Model() {
        rum.add(new Rum(5,5, new Position(1,2, N)));
        rum.add(new Rum(3,3, new Position(1,2, N)));
        rum.add(new Rum(5,5, new Position(0,0, E)));
        rum.add(new Rum(1,1, new Position(0,0, E)));

        programmer.add("RFRFFRFRF");
        programmer.add("RFLFFLRF");

    }
}
