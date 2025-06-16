public class Fahrer {

    private String fahrerID;
    private String vorname;
    private String nachname;
    private String fuehrerscheinklasse;

    public Fahrer(String fahrerID, String vorname, String nachname, String fuehrerscheinklasse) {
        this.fahrerID = fahrerID;
        this.vorname = vorname;
        this.nachname = nachname;
        this.fuehrerscheinklasse = fuehrerscheinklasse;
    }

    public String getFahrerID() {
        return fahrerID;
    }

    public String getVorname() {
        return vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public String getFuehrerscheinklasse() {
        return fuehrerscheinklasse;
    }
}
