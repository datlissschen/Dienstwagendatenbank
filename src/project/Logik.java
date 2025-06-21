package project;

import java.util.ArrayList;
import java.util.List;


public class Logik {
    public static void main(String[] args) {


    }

    /*search for Fahrer object*/
    public static void fahrersuche(String a) {
        /*iterate through List to find fahrer*/
        for (Fahrer fahrer : Data.fahrerListe) {
            if (fahrer.getFahrerID().contains(a)) {
                System.out.println(fahrer.getFahrerID() + ", " + fahrer.getVorname()
                        + " " + fahrer.getNachname() + ", " + fahrer.getFuehrerscheinklasse());
            } else if (fahrer.getVorname().contains(a)) {
                System.out.println(fahrer.getFahrerID() + ", " + fahrer.getVorname()
                        + " " + fahrer.getNachname() + ", " + fahrer.getFuehrerscheinklasse());
            } else if (fahrer.getNachname().contains(a)) {
                System.out.println(fahrer.getFahrerID() + ", " + fahrer.getVorname()
                        + " " + fahrer.getNachname() + ", " + fahrer.getFuehrerscheinklasse());
            }
        }
    }

    /* find a Dienstwagen*/
    public static void fahrzeugsuche(String a) {
        /*iterate through List to search for the matching dienstwagen objects*/
        for (Dienstwagen dienstwagen : Data.dienstwagenListe) {
            if (dienstwagen.getFahrzeugId().contains(a)) {
                System.out.println(dienstwagen.getFahrzeugId() + ", " + dienstwagen.getKennzeichen()
                        + ", " + dienstwagen.getHersteller() + ", " + dienstwagen.getModell());
            } else if (dienstwagen.getHersteller().contains(a)) {
                System.out.println(dienstwagen.getFahrzeugId() + ", " + dienstwagen.getKennzeichen()
                        + ", " + dienstwagen.getHersteller() + ", " + dienstwagen.getModell());
            } else if (dienstwagen.getKennzeichen().contains(a)) {
                System.out.println(dienstwagen.getFahrzeugId() + ", " + dienstwagen.getKennzeichen()
                        + ", " + dienstwagen.getHersteller() + ", " + dienstwagen.getModell());
            } else if (dienstwagen.getModell().contains(a)) {
                System.out.println(dienstwagen.getFahrzeugId() + ", " + dienstwagen.getKennzeichen()
                + ", " + dienstwagen.getHersteller() + ", " + dienstwagen.getModell());
            }
        }
    }


    /***
     * find project.Fahrer for given time and Dienstwagen (FahrzeugID)
     * @param inputString Format: "fahrzeugID;startzeit" (z.B. "V_001;2025-06-19T08:00:00")
     */
    public static void blitzer(String inputString) {
        /*check format*/
        String[] data = inputString.split(";");

        if (data.length != 2) {
            System.err.println("Fehler: Ungültiges Eingabeformat. Erwartet: \"fahrzeugID;startzeit\"");
            return;
        }

        String gesuchteFahrzeugID = data[0].trim();
        String gesuchteStartzeit = data[1].trim();

        String gefundenerFahrerId = null; // safe FahrerID here
        boolean fahrtGefunden = false;   // flag if a project.Fahrt has actually been found

        /* search project.Fahrt*/
        for (Fahrt fahrt : Data.fahrtenListe) {
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
            for (Fahrer fahrer : Data.fahrerListe) {
                if (fahrer.getFahrerID().equals(gefundenerFahrerId)) {
                    System.out.println("Gefundener project.Fahrer: " + fahrer.getVorname() + " " + fahrer.getNachname());
                    // Wenn du hier auch die Informationen aus deiner 'fahrersuche'-Methode möchtest,
                    // rufe sie HIER auf:
                    // fahrersuche(gefundenerFahrerId);
                    fahrerGefunden = true;
                    break;
                }
            }
            if (!fahrerGefunden) {
                System.out.println("Fehler: project.Fahrer-ID '" + gefundenerFahrerId + "' aus project.Fahrt nicht in Fahrerliste gefunden.");
            }
        } else {
            /* search not successful*/
            System.out.println("Kein project.Fahrer gefunden für Fahrzeug-ID: " + gesuchteFahrzeugID + " und Startzeit: " + gesuchteStartzeit);
        }
    }

    /**
     * @param a in format "FahrerID;Datum".
     * searching fahrerId is fundsucher and the date for the search is suchdatum
     */
    public static void fundsuche(String a) {
        /*Lists to collect the solutions*/
        List<String> gefundeneFahrzeuge = new ArrayList<>();
        List<String> gefundeneFahrer = new ArrayList<>();
        List<String> ausgaben = new ArrayList<>();

        /*split the entry for day and the driver who needs the info and trim the split*/
        String[] data = a.split(";");
        if (data.length < 2){
            System.err.println("Fehler: Das Eingabeformat ist ungültig");
            return;
        }
        String fundsucher = data[0].trim();
        String suchdatum = data[1].trim();

        /*everytime the driver has driven something on the day, save the FahrzeugID */
        /*iterating through every drive*/
        for (Fahrt fahrt : Data.fahrtenListe) {
            if (fahrt.getFahrerID().equals(fundsucher)) {
                /*Starzeit come in the format 2024-01-01T20:08:53, only need first 10 characters(0-9) for precise day*/
                if (fahrt.getStartzeit() != null && fahrt.getStartzeit().length() >= 10 &&fahrt.getStartzeit().substring(0,10).equals(suchdatum)) {
                    gefundeneFahrzeuge.add(fahrt.getFahrzeugID());
                }
            }
        }

        /*only continue if the fundsucher has driven on the day*/
        if (gefundeneFahrzeuge.isEmpty()){
            System.out.println("Keine project.Fahrer gefunden!");
            return;
        }
        /*searching in the possible drives*/
        for (Fahrt fahrt : Data.fahrtenListe) {
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
        for (String currentFahrerId : gefundeneFahrer){ // Using 'currentFahrerId' for clarity

            Fahrer gefundenerFahrer = null; // Correct: Reference variable for the project.Fahrer object
            /*search for the drivers in the main fahrerListe*/
            for (Fahrer extrahierteFahrer : Data.fahrerListe){ // Corrected: Direct access to fahrerListe
                if (extrahierteFahrer.getFahrerID().equals(currentFahrerId)) { // Correct: Using getId() for comparison
                    gefundenerFahrer = extrahierteFahrer; // <<-- CORRECTED LINE 149: Assign the found project.Fahrer object
                    break; // Driver found, no need to continue searching in fahrerListe
                }
            }

            /*search for the vehicles driven by this 'other' driver on the specified date and used by the fundsucher*/
            if (gefundenerFahrer != null){
                for (Fahrt fundFahrt : Data.fahrtenListe){
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
            System.out.println("Keine project.Fahrer gefunden. Viel Glück mit der Suche.");
        } else {
            System.out.println((String.join(", ", ausgaben))); //print the drivers
        }
    }
}
