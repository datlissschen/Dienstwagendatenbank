package project;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public List<Fahrt> fahrtenListe = new ArrayList<>();
    public List<Fahrer> fahrerListe = new ArrayList<>();
    public List<Dienstwagen> dienstwagenListe = new ArrayList<>();

    public Data(String fileName) {
        DataModellReader.populateModels(this, fileName);
    }

    public Data() {

    }
}
