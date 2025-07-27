package project;

import java.util.*;
import java.util.HashMap;
import static project.HelperMethods.convertToUnixTime;


public class Logik {
    private Data dataFile;

    public Logik(Data data) {
        this.dataFile = data;
    }

    public List<String> fahrersuche(String a) {
        List<String> fahrerOutput = new ArrayList<>();
        // iterate through List to find fahrer
        for (Fahrer fahrer : dataFile.fahrerListe) {
            // if statement to check the different values
            if (fahrer.getVorname().contains(a) ||
                    fahrer.getNachname().contains(a) ||
                    fahrer.getFahrerID().contains(a) ||
                    fahrer.getFuehrerscheinklasse().contains(a)) {
                fahrerOutput.add(fahrer.getFahrerID() + ", " + fahrer.getVorname()
                        + " " + fahrer.getNachname() + ", " + fahrer.getFuehrerscheinklasse());
            }
        }
        if (fahrerOutput.isEmpty()) {
            fahrerOutput.add("Keine Fahrer vorhanden");
        }
        return fahrerOutput;
    }

    public List<String> fahrzeugsuche(String a) {
        List<String> dienstwagenOutput = new ArrayList<>();
        // iterate through List to find
        for (Dienstwagen dienstwagen : dataFile.dienstwagenListe) {
            // if statement to check the different values
            if (dienstwagen.getFahrzeugId().contains(a) ||
                    dienstwagen.getHersteller().contains(a) ||
                    dienstwagen.getKennzeichen().contains(a) ||
                    dienstwagen.getModell().contains(a)) {
                dienstwagenOutput.add(dienstwagen.getFahrzeugId() + ", " + dienstwagen.getKennzeichen()
                        + ", " + dienstwagen.getHersteller() + ", " + dienstwagen.getModell());
            }
        }
        if (dienstwagenOutput.isEmpty()) {
            dienstwagenOutput.add("Keine Dienstwagen vorhanden");
        }
        return dienstwagenOutput;
    }

    /**
     * Processing of speeding events
     *
     * @param input input from user to search the one who got a ticket in format: FAHRZEUG_ID;YYYY-MM-DD'T'HH:mm:ss
     * @return ID of driver who was speeding,
     * if no driver was found:"Kein Fahrer gefunden",
     * error message if incorrect format
     * @see Fahrt
     * @see Fahrer
     * @see DataModellReader
     */
    public List<String> blitzer(String input) {
        //check format
        String[] data = input.split(";");
        if (data.length != 2) {
            return Collections.singletonList("Fehler: Eingabeformat ungültig. Erwartet: FAHRZEUG_ID;YYYY-MM-DD'T'HH:mm:ss");
        }
        String gesuchtesFahrzeuKennzeichenOrID = data[0].trim();
        String timestampStr = data[1].trim();
        //parse time into unix
        long timestampUnix = convertToUnixTime(timestampStr);

        String gesuchteFahrzeugID = gesuchtesFahrzeuKennzeichenOrID;
        for (Dienstwagen dienstwagen : dataFile.dienstwagenListe) {
            if (dienstwagen.getKennzeichen().equals(gesuchtesFahrzeuKennzeichenOrID)) {
                gesuchteFahrzeugID = dienstwagen.getFahrzeugId();
                break;
            }
        }

        List<String> blitzerOutput = new ArrayList<>();
        List<String> gefundeneRaser = new ArrayList<>();
        for (Fahrt fahrt : dataFile.fahrtenListe) {
            //calculating the difference for time
            if (!gesuchteFahrzeugID.equals(fahrt.getFahrzeugID())) continue;
            if (!(timestampUnix >= fahrt.getStartzeit())) continue;
            if (!(timestampUnix <= fahrt.getEndzeit())) continue;
            gefundeneRaser.add(fahrt.getFahrerID());

        }
        if (gefundeneRaser.isEmpty()) {
            blitzerOutput.add("Kein Fahrer gefunden für Fahrzeug-ID: " + gesuchteFahrzeugID + " und Startzeit: " + timestampStr);
        } else {
            for (Fahrer fahrer : dataFile.fahrerListe) {
                if (gefundeneRaser.contains(fahrer.getFahrerID())) {
                    blitzerOutput.add(fahrer.getVorname() + " " + fahrer.getNachname());
                }
            }
        }
        return blitzerOutput;
    }


    /**
     * finding possible people who have found an item
     *
     * @param input the name of the searching driver and the date in format: ${Fahrer};${Datum}
     * @return fundsucheOutput a List of the drivers who shared the same vehicle as the searching driver
     * if no searching driver has not driven anythong on the date: "Keine Dienstwagen gefunden"
     * if no other driver can be found: Keine Fahrer gefunden"
     * @see Fahrer
     * @see Fahrt
     * @see DataModellReader
     */
    public List<String> fundsuche(String input) {
        //split info
        String[] data = input.split(";");
        if (data.length != 2) {
            return Collections.singletonList("Fehler: Eingabeformat ungültig. Erwartet: ${Fahrer};${Datum}");
        }
        String suchenderFahrer = data[0].trim();
        String dateStr = data[1].trim();
        long startdateUnix = convertToUnixTime(dateStr + "T00:00:00");
        long enddateUnix = convertToUnixTime(dateStr + "T23:59:59");

        //getting the dienstwagen that were driven by the suchendeFahrer on the date
        List<String> gefundeneDienstwagen = new ArrayList<>();
        for (Fahrt fahrt : dataFile.fahrtenListe) {
            //calculating the time
            if (!suchenderFahrer.equals(fahrt.getFahrerID())) continue;
            if (!(fahrt.getStartzeit() <= enddateUnix && fahrt.getEndzeit() >= startdateUnix)) continue;
            gefundeneDienstwagen.add(fahrt.getFahrzeugID());

        }
        if (gefundeneDienstwagen.isEmpty()) {
            return Collections.singletonList("Keine Dienstwagen gefunden");
        }

        //getting the drivers that have driven the same vehicle on the given date
        Set<String> gefundeneFahrer = new HashSet<>();
        Map<String, String> fahrerToFahrzeug = new HashMap<>();
        for (Fahrt fahrt : dataFile.fahrtenListe) {
            if (!(gefundeneDienstwagen.contains(fahrt.getFahrzeugID()))) continue;
            if (fahrt.getFahrerID().equals(suchenderFahrer)) continue;
            if (!(fahrt.getStartzeit() <= enddateUnix && fahrt.getEndzeit() >= startdateUnix)) continue;
            gefundeneFahrer.add(fahrt.getFahrerID());
            fahrerToFahrzeug.put(fahrt.getFahrerID(), fahrt.getFahrzeugID());
        }

        // Map.put (Fahrer ID, Für fahrer ID entsprechnende Fahrer
        HashMap<String, String> fahrerMap = new HashMap<>();
        List<String> foundFahrer = new ArrayList<>();
        for (Fahrer fahrer : dataFile.fahrerListe) {
            if (gefundeneFahrer.contains(fahrer.getFahrerID())) {
                foundFahrer.add(fahrer.getVorname());

                String fahrzeugID = fahrerToFahrzeug.get(fahrer.getFahrerID());

                String kennzeichen = fahrzeugID;
                for (Dienstwagen dienstwagen : dataFile.dienstwagenListe) {
                    if (dienstwagen.getFahrzeugId().equals(fahrzeugID)) {
                        kennzeichen = dienstwagen.getKennzeichen();
                        break;
                    }
                }

                fahrerMap.put(fahrer.getVorname(), fahrer.getVorname() + " " + fahrer.getNachname() +
                        " (" + kennzeichen + ")");
            }
        }
        // Import the HashMap class
        Collections.sort(foundFahrer);

        List<String> fahrersucheOutput = new ArrayList<>();
        for (String s : foundFahrer) {
            fahrersucheOutput.add(fahrerMap.get(s));
        }

        if (gefundeneFahrer.isEmpty()) {
            fahrersucheOutput.add("Keine Fahrer gefunden");
        }

        return fahrersucheOutput;
    }


    /*print methods*/
    public void print(List<String> liste) {
        for (String eintrag : liste) {
            System.out.println(eintrag);
        }
    }

    public void printfundsuche(List<String> liste) {
        for (String eintrag : liste) {
            System.out.print(eintrag);
            if (!eintrag.equals(liste.getLast())) {
                System.out.print(", ");
            } else {
                System.out.print(".");
            }
        }
        System.out.println();
    }

}
