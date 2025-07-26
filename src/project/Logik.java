package project;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Logik {
    private Data dataFile;
    public Logik(Data data) {
        this.dataFile = data;
    }

    public List <String> fahrersuche(String a){
        List <String> list = new ArrayList <>();
        List <String> fahrerOutput = new ArrayList <>();
        // iterate through List to find fahrer
        for(Fahrer fahrer: dataFile.fahrerListe){
            // if statement to check the different values
            if(fahrer.getVorname().contains(a) ||
                    fahrer.getNachname().contains(a) ||
                    fahrer.getFahrerID().contains(a) ||
                    fahrer.getFuehrerscheinklasse().contains(a)){
                fahrerOutput.add(fahrer.getFahrerID() + ", " + fahrer.getVorname()
                        + " " + fahrer.getNachname() + ", " + fahrer.getFuehrerscheinklasse());
            }
        }
        if (fahrerOutput.isEmpty()){
            fahrerOutput.add("Keine Fahrer vorhanden");
        }
        return fahrerOutput;
    }



//
//    /* find a Dienstwagen*/
//    public List<String> fahrzeugsuche(String a) {
//        /*List for ouput*/
//        List<String> fahrzeugsout = new ArrayList<>();
//        /*iterate through List to search for the matching dienstwagen objects*/
//        for (Dienstwagen dienstwagen : dataFile.dienstwagenListe) {
//            boolean found = false;
//            if (dienstwagen.getFahrzeugId().contains(a)) {
//                found = true;
//            } else if (dienstwagen.getHersteller().contains(a)) {
//                found = true;
//            } else if (dienstwagen.getKennzeichen().contains(a)) {
//                found = true;
//            } else if (dienstwagen.getModell().contains(a)) {
//                found = true;
//            }
//
//            if (found) {
//                fahrzeugsout.add(dienstwagen.getFahrzeugId() + ", " + dienstwagen.getKennzeichen()
//                        + ", " + dienstwagen.getHersteller() + ", " + dienstwagen.getModell());
//            }
//        }
//        return fahrzeugsout;
//    }
//
//
//    /***
//     * find Fahrer for given time and Dienstwagen (FahrzeugID)
//     * @param inputString Format: "fahrzeugID;startzeit" (z.B. "V_001;2025-06-19T08:00:00")
//     * @return null
//     */
//    public String blitzer(String inputString) {
//        /*check format*/
//        String[] data = inputString.split(";");
//
//        /*catch insufficient input*/
//        if (data.length != 2) {
//            return null;
//        }
//
//        String gesuchteFahrzeugID = data[0].trim();
//        /*parse into the data format*/
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//        LocalDateTime gesuchteStartzeit = null; //Variable needs to exist otuside of the try and catch block
//        try {
//            gesuchteStartzeit = LocalDateTime.parse(data[1].trim(), formatter);
//        } catch (java.time.format.DateTimeParseException e) {
//            return null;
//        }
//
//        String gefundenerFahrerID = null; // safe FahrerID here
//
//        /* search Fahrt*/
//
//        for (Fahrt fahrt : dataFile.fahrtenListe) {
//            LocalDateTime startTime = fahrt.getStartzeit();
//            LocalDateTime endTime = fahrt.getEndzeit();
//            boolean istInSpanne = !startTime.isAfter(gesuchteStartzeit) && !endTime.isBefore(gesuchteStartzeit);
//            boolean istEqual = startTime.equals(gesuchteStartzeit) || endTime.equals(gesuchteStartzeit);
//            if (fahrt.getFahrzeugID().equals(gesuchteFahrzeugID) && (istInSpanne)) {
//                /*safe FahrerID for the Fahrt*/
//                gefundenerFahrerID = fahrt.getFahrerID();
//                break;
//            }
//        }
//
//        /*find the Fahrer for the Fahrt*/
//        if (gefundenerFahrerID != null) {
//            for (Fahrer fahrer : dataFile.fahrerListe) {
//                if (fahrer.getFahrerID().equals(gefundenerFahrerID)) {
//                    return fahrer.getVorname() + " " + fahrer.getNachname(); //output
//                }
//            }
//        }
//        return null; /*no system.err.println for the projekttester*/
//    }
//
//    /**
//     * @param a in format "FahrerID;Datum".
//     *          searching fahrerId is fundsucher and the date for the search is suchdatum
//     * @return fundsucheoutput
//     */
//    public List<String> fundsuche(String a) {
//        /*List to gather the output*/
//        List <String> fundsucheoutput = new ArrayList<>();
//
//        /*Hashset to collect the solutions-> no duplicates in Hashsets(efficiency)*/
//        Set<String> gefundeneFahrzeuge = new HashSet<>();
//        Set<String> gefundeneFahrer = new HashSet<>();
//
//        /*split the entry for day and the driver who needs the info and trim the split*/
//        String[] dataf = a.split(";");
//        if (dataf.length < 2){
//            System.err.println("Fehler: Das Eingabeformat ist ungültig");
//            return fundsucheoutput;
//        }
//        String fundsucher = dataf[0].trim();
//        String suchdatum = dataf[1].trim();
//
//        /*
//        *everytime the driver has driven something on the day, save FahrzeugID
//        *iterating through every drive
//        */
//        for (Fahrt fahrt : dataFile.fahrtenListe) {
//            if (fahrt.getFahrerID().equals(fundsucher)) {
//                /*Startzeit come in format 2024-01-01T20:08:53, only need first 10 characters(0-9) for precise day*/
//                if (fahrt.getStartzeit() != null && fahrt.getStartzeit().toString().length() >= 10 &&fahrt.getStartzeit().toString().substring(0,10).equals(suchdatum)) {
//                    gefundeneFahrzeuge.add(fahrt.getFahrzeugID());
//                }
//            }
//        }
//
//        /*only continue if the fundsucher has driven on the day*/
//        if (gefundeneFahrzeuge.isEmpty()) return fundsucheoutput;
//
//        /*searching in possible drives*/
//        for (Fahrt fahrt : dataFile.fahrtenListe) {
//            /*control date*/
//            if (fahrt.getStartzeit() != null && fahrt.getStartzeit().toString().substring(0, 10).equals(suchdatum)) {
//                /*make sure that cars have been used by fundsucher*/
//                if (gefundeneFahrzeuge.contains(fahrt.getFahrzeugID())) {
//                    gefundeneFahrer.add(fahrt.getFahrerID()); // add FahrerID
//                }
//            }
//        }
//
//        Map<String, Fahrer> fahrerMap = new HashMap<>();
//        for (Fahrer fahrer : dataFile.fahrerListe) {
//            fahrerMap.put(fahrer.getFahrerID(), fahrer);
//        }
//
//        Map<String, Dienstwagen> dienstwagenMap = new HashMap<>();
//        for (Dienstwagen dw : dataFile.dienstwagenListe) {
//            dienstwagenMap.put(dw.getFahrzeugId(), dw);
//        }
//
//        Set<String> endgueltigeAusgaben = new HashSet<>(); // Set für Duplikatsvermeidung im Output
//        for (String currentFahrerId : gefundeneFahrer){
//            Fahrer gefundenerFahrer = fahrerMap.get(currentFahrerId);
//
//            if (gefundenerFahrer != null){
//                for (Fahrt fundFahrt : dataFile.fahrtenListe){
//                    if (fundFahrt.getFahrerID().equals(currentFahrerId) &&
//                            fundFahrt.getStartzeit() != null && fundFahrt.getStartzeit().toString().length() >= 10 &&
//                            fundFahrt.getStartzeit().toString().substring(0,10).equals(suchdatum) &&
//                            gefundeneFahrzeuge.contains(fundFahrt.getFahrzeugID())) {
//
//                        String kennzeichenOutput = fundFahrt.getFahrzeugID();
//                        Dienstwagen genutzterDienstwagen = dienstwagenMap.get(fundFahrt.getFahrzeugID());
//                        if (genutzterDienstwagen != null) {
//                            kennzeichenOutput = genutzterDienstwagen.getKennzeichen();
//                        }
//
//                        String formatAusgabe = gefundenerFahrer.getVorname() + " " + gefundenerFahrer.getNachname() + " (" + kennzeichenOutput + ")";
//                        endgueltigeAusgaben.add(formatAusgabe);
//                    }
//                }
//            }
//        }
//        fundsucheoutput.addAll(endgueltigeAusgaben);
//        return fundsucheoutput;
//    }
//
    /*print methods*/
    public void print(List<String> liste) {
        for (String eintrag : liste) {
            System.out.println(eintrag);
        }
    }
    public void printFundsuchanfrage(List<String> liste) {
        for (String eintrag : liste) {
            System.out.println(eintrag);
        }
    }


//    /*search for Fahrer object*/
//    public List<String> fahrersuche(String a) {
//        /*Use Liste for ouput*/
//        List<String> fahrersoutput  = new ArrayList<>();
//        /*iterate through List to find fahrer*/
//        for (Fahrer fahrer : dataFile.fahrerListe) {
//            boolean found = false;
//            if (fahrer.getFahrerID().contains(a)) {
//                found = true;
//            } else if (fahrer.getVorname().contains(a)) {
//                found = true;
//            } else if (fahrer.getNachname().contains(a)) {
//                found = true;
//            }
//            if (found) {
//                fahrersoutput.add(fahrer.getFahrerID() + ", " + fahrer.getVorname()
//                        + " " + fahrer.getNachname() + ", " + fahrer.getFuehrerscheinklasse());
//            }
//        }
//        if (fahrersoutput.isEmpty()) {
//            fahrersoutput.add("No fahrers found");
//        }
//        return(fahrersoutput);
//    }

}
