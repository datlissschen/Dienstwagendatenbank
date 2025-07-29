package project;

import project.model.Dienstwagen;
import project.model.Fahrer;
import project.model.Fahrt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Data {
    private final List<Fahrt> fahrtenListe = new ArrayList<>();
    private final List<Fahrer> fahrerListe = new ArrayList<>();
    private final List<Dienstwagen> dienstwagenListe = new ArrayList<>();


    public Data() {
    }

    public List<Fahrt> getFahrtenListe() {
        return Collections.unmodifiableList(fahrtenListe);
    }

    public List<Fahrer> getFahrerListe() {
        return Collections.unmodifiableList(fahrerListe);
    }

    public List<Dienstwagen> getDienstwagenListe() {
        return Collections.unmodifiableList(dienstwagenListe);
    }

    public void addFahrt(Fahrt fahrt) {
        fahrtenListe.add(fahrt);
    }

    public void addFahrer(Fahrer fahrer) {
        fahrerListe.add(fahrer);
    }

    public void addDienstwagen(Dienstwagen dienstwagen) {
        dienstwagenListe.add(dienstwagen);
    }
}
