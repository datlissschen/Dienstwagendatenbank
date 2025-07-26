package project;

import java.time.LocalDateTime;

public class Fahrt {

    private final String fahrerID;
    private final String fahrzeugID;
    private final int startKm;
    private final int endKm;
    private final long startzeit;
    private final long endzeit;

    public Fahrt(String fahrerID, String fahrzeugID, int startKm, int endKm, long startzeit, long endzeit) {
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

    public String getFahrzeugID() { return fahrzeugID;}

    public int getStartKm() {
        return startKm;
    }

    public int getEndKm() {
        return endKm;
    }

    public long getStartzeit() {
        return startzeit;
    }

    public long getEndzeit() {
        return endzeit;
    }

}
