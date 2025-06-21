package project;

import java.util.ArrayList;
import java.util.List;


public class Logik {
    private Data dataFile;
    public Logik(Data data) {
        this.dataFile = data;
    }

    /*search for Fahrer object*/
    public List<String> fahrersuche(String a) {
        /*Use Liste for ouput*/
        List<String> fahrersoutput  = new ArrayList<>();
        /*iterate through List to find fahrer*/
        for (Fahrer fahrer : dataFile.fahrerListe) {
            boolean found = false;
            if (fahrer.getFahrerID().contains(a)) {
                found = true;
            } else if (fahrer.getVorname().contains(a)) {
                found = true;
            } else if (fahrer.getNachname().contains(a)) {
                found = true;
            }
            if (found) {
                fahrersoutput.add(fahrer.getFahrerID() + ", " + fahrer.getVorname()
                        + " " + fahrer.getNachname() + ", " + fahrer.getFuehrerscheinklasse());
            }
        }
        if (fahrersoutput.isEmpty()) {
            fahrersoutput.add("No fahrers found");
        }
        return(fahrersoutput);
    }

    /* find a Dienstwagen*/
    public List<String> fahrzeugsuche(String a) {
        /*List for ouput*/
        List<String> fahrzeugsout = new ArrayList<>();
        /*iterate through List to search for the matching dienstwagen objects*/
        for (Dienstwagen dienstwagen : dataFile.dienstwagenListe) {
            boolean found = false;
            if (dienstwagen.getFahrzeugId().contains(a)) {
                found = true;
            } else if (dienstwagen.getHersteller().contains(a)) {
                found = true;
            } else if (dienstwagen.getKennzeichen().contains(a)) {
                found = true;
            } else if (dienstwagen.getModell().contains(a)) {
                found = true;
            }

            if (found) {
                fahrzeugsout.add(dienstwagen.getFahrzeugId() + ", " + dienstwagen.getKennzeichen()
                        + ", " + dienstwagen.getHersteller() + ", " + dienstwagen.getModell());
            }
        }
        return fahrzeugsout;
    }


    /***
     * find project.Fahrer for given time and Dienstwagen (FahrzeugID)
     * @param inputString Format: "fahrzeugID;startzeit" (z.B. "V_001;2025-06-19T08:00:00")
     * @return
     */
    public List<String> blitzer(String inputString) {
        /*List for Output*/
        List <String> blitzeroutput = new ArrayList<>();
        /*check format*/
        String[] data = inputString.split(";");

        if (data.length != 2) {
            System.err.println("Fehler: Ungültiges Eingabeformat. Erwartet: \"fahrzeugID;startzeit\"");
            return blitzeroutput;
        }

        String gesuchteFahrzeugID = data[0].trim();
        String gesuchteStartzeit = data[1].trim();

        String gefundenerFahrerId = null; // safe FahrerID here
        boolean fahrtGefunden = false;   // flag if a project.Fahrt has actually been found

        /* search project.Fahrt*/
        for (Fahrt fahrt : dataFile.fahrtenListe) {
            if (fahrt.getFahrzeugID().equals(gesuchteFahrzeugID) &&
                    fahrt.getStartzeit().equals(gesuchteStartzeit)) {

                gefundenerFahrerId = fahrt.getFahrerID(); /*safe FahrerID for the project.Fahrt*/
                fahrtGefunden = true;
                break;
            }
        }

        if (fahrtGefunden) {
            /*find the project.Fahrer for the project.Fahrt*/
            boolean fahrerGefunden = false;
            for (Fahrer fahrer : dataFile.fahrerListe) {
                if (fahrer.getFahrerID().equals(gefundenerFahrerId)) {
                    blitzeroutput.add("Gefundener project.Fahrer: " + fahrer.getVorname() + " " + fahrer.getNachname());
                    fahrerGefunden = true;
                    break;
                }
            }
            if (!fahrerGefunden) {
                blitzeroutput.add("Fehler: Fahrer-ID '" + gefundenerFahrerId + "' aus Fahrt nicht in Fahrerliste gefunden.");
            }
        } else {
            /* search not successful*/
            blitzeroutput.add("Kein Fahrer gefunden für Fahrzeug-ID: " + gesuchteFahrzeugID + " und Startzeit: " + gesuchteStartzeit);
        }
        return blitzeroutput;
    }

    /**
     * @param a in format "FahrerID;Datum".
     *          searching fahrerId is fundsucher and the date for the search is suchdatum
     * @return
     */
    public List<String> fundsuche(String a) {
        /*List to gather the output*/
        List <String> fundsucheoutput = new ArrayList<>();

        /*Lists to collect the solutions*/
        List<String> gefundeneFahrzeuge = new ArrayList<>();
        List<String> gefundeneFahrer = new ArrayList<>();
        List<String> ausgaben = new ArrayList<>();

        /*split the entry for day and the driver who needs the info and trim the split*/
        String[] data = a.split(";");
        if (data.length < 2){
            System.err.println("Fehler: Das Eingabeformat ist ungültig");
            return fundsucheoutput;
        }
        String fundsucher = data[0].trim();
        String suchdatum = data[1].trim();

        /*everytime the driver has driven something on the day, save the FahrzeugID */
        /*iterating through every drive*/
        for (Fahrt fahrt : dataFile.fahrtenListe) {
            if (fahrt.getFahrerID().equals(fundsucher)) {
                /*Starzeit come in the format 2024-01-01T20:08:53, only need first 10 characters(0-9) for precise day*/
                if (fahrt.getStartzeit() != null && fahrt.getStartzeit().length() >= 10 &&fahrt.getStartzeit().substring(0,10).equals(suchdatum)) {
                    gefundeneFahrzeuge.add(fahrt.getFahrzeugID());
                }
            }
        }

        /*only continue if the fundsucher has driven on the day*/
        if (gefundeneFahrzeuge.isEmpty()){
            fundsucheoutput.add("Keine project.Fahrer gefunden!");
            return fundsucheoutput;
        }
        /*searching in the possible drives*/
        for (Fahrt fahrt : dataFile.fahrtenListe) {
            /*control the date*/
            if (fahrt.getStartzeit() != null && fahrt.getStartzeit().substring(0, 10).equals(suchdatum)) {
                /*make sure that the cars have been used by the fundsucher*/
                if (gefundeneFahrzeuge.contains(fahrt.getFahrzeugID())) {
                    /*FahrerID cannot be fundsucher*/
                    if (!fahrt.getFahrerID().equals(fundsucher)) {
                        /*no duplicates*/
                        // add FahrerID
                        if (!gefundeneFahrer.contains(fahrt.getFahrerID())) {
                            gefundeneFahrer.add(fahrt.getFahrerID());
                        }
                    }
                }
            }
        }

        /*collect the needed info for output through iterating over the IDs*/
        for (String currentFahrerId : gefundeneFahrer){

            Fahrer gefundenerFahrer = null;
            /*search for the drivers in the main fahrerListe*/
            for (Fahrer extrahierteFahrer : dataFile.fahrerListe){
                if (extrahierteFahrer.getFahrerID().equals(currentFahrerId)) {
                    gefundenerFahrer = extrahierteFahrer;
                    break;
                }
            }

            /*search for the vehicles driven by this 'other' driver on the specified date and used by the fundsucher*/
            if (gefundenerFahrer != null){
                for (Fahrt fundFahrt : dataFile.fahrtenListe){
                    /* Check match the current 'other' driver, the date, AND a vehicle the fundsucher used*/
                    if (fundFahrt.getFahrerID().equals(currentFahrerId) &&
                            fundFahrt.getStartzeit().length() >= 10 &&
                            fundFahrt.getStartzeit().substring(0,10).equals(suchdatum) &&
                            gefundeneFahrzeuge.contains(fundFahrt.getFahrzeugID())) {

                        /*required to be output format: Ben Wagner (S-GH-3277), Mia Hoffmann (S-GH-3277)*/
                        String formatAusgabe = gefundenerFahrer.getVorname() + " " + gefundenerFahrer.getNachname() + " ("
                                + fundFahrt.getFahrzeugID() + ")"; // Use 'gefundenerFahrer' for name parts

                        /*no duplicates allowed in the final output list*/
                        if (!ausgaben.contains(formatAusgabe)) {
                            ausgaben.add(formatAusgabe);
                        }
                    }
                }
            }
        }
        /*output of the string*/
        if (ausgaben.isEmpty()){
            fundsucheoutput.add("Keine project.Fahrer gefunden. Viel Glück mit der Suche.");
        } else {
            for (String currentFahrerID : ausgaben){
                fundsucheoutput.add((String.join(", ", currentFahrerID)));
            }

        }
        return fundsucheoutput;
    }

    public void printFundsuchanfrage(List<String> ausgaben){
        for (String s : ausgaben) {
            System.out.print(s);
        }
    }
    public void print(List<String> fundsucheoutput) {
        for (String s : fundsucheoutput){
            System.out.println(s);
        }
    }
}
