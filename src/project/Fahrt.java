package project;

public class Fahrt {

    private final String fahrerID;
    private final String fahrzeugID;
    private final int startKm;
    private final int endKm;
    private final String startzeit;
    private final String endzeit;

    public Fahrt(String fahrerID, String fahrzeugID, int startKm, int endKm, String startzeit, String endzeit) {
        this.fahrerID = fahrerID;
        this.fahrzeugID = fahrzeugID;
        this.startKm = startKm;
        this.endKm = endKm;
        this.startzeit = startzeit;
        this.endzeit = endzeit;
    }

    public String getFahrerID() {
        return fahrerID;
    }

    public String getFahrzeugID() {
        return fahrzeugID;
    }

    public int getStartKm() {
        return startKm;
    }

    public int getEndKm() {
        return endKm;
    }

    public String getStartzeit() {
        return startzeit;
    }

    public String getEndzeit() {
        return endzeit;
    }
}
