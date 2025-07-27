package project.model;

public class Dienstwagen {
    private final String fahrzeugID;
    private final String hersteller;
    private final String modell;
    private final String kennzeichen;

    public Dienstwagen(String fahrzeugID, String hersteller, String modell, String kennzeichen) {
        this.fahrzeugID = fahrzeugID;
        this.hersteller = hersteller;
        this.modell = modell;
        this.kennzeichen = kennzeichen;
    }

    public String getFahrzeugId() {
        return fahrzeugID;
    }

    public String getHersteller() {
        return hersteller;
    }

    public String getModell() {
        return modell;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }
}
