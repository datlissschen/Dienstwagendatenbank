public class Dienstwagen {
    private String fahrzeugId;
    private String hersteller;
    private String modell;
    private String kennzeichen;

    public Dienstwagen(String fahrzeugId, String hersteller, String modell, String kennzeichen) {
        this.fahrzeugId = fahrzeugId;
        this.hersteller = hersteller;
        this.modell = modell;
        this.kennzeichen = kennzeichen;
    }

    public String getFahrzeugId() {
        return fahrzeugId;
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
