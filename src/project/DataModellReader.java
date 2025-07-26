package project;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static project.HelperMethods.convertToUnixTime;


public class DataModellReader {
    /*import file and convert it into data models Dienstwagen,Fahrt and Fahrer*/

    /*create lists with data models*/


    public static void populateModels(Data dataClass, String source) {

        /*read file, if not possible throw exception*/
        try(BufferedReader reader = new BufferedReader(new FileReader(source))){

            String line;
            /*read lines, while it is not zero*/
            while((line = reader.readLine()) != null){
                /*remove " if they exist and skip the line if it is now empty due to removing "*/
                line = line.replaceAll("\"", "");
                if(line.isEmpty()){
                    continue;
                }

                /*split the line with indicator "," */
                String[] data = line.split(",");
                String firstData = data[0].trim();
                /* skip the definition for new entity*/
                if(firstData.startsWith("New_Entity:")){
                    continue;
                }

                /*differ into the different models*/
                /*use class Fahrten*/
                if (data.length == 6){
                    try{
                        // 2024-08-03T19:17:0
                        long startzeit = convertToUnixTime(data[4]);
                        long endzeit = convertToUnixTime(data[5]);



                        // 2024-01-30T00:40:28
                        String[] endZeitString = data[5].split("T");




                        /*create a new object and parse startKm and endKm from String in int, add object to fahrtenListe*/
                        Fahrt neu = new Fahrt(data[0], data[1], Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()), startzeit, endzeit);
                        dataClass.fahrtenListe.add(neu);
                    }
                    catch(NumberFormatException e){
                        /*find mistake */
                        System.out.println("Verarbeite Zeile als project.Fahrt: " + line);
                        System.out.println("data[2]: '" + data[2] + "', data[3]: '" + data[3] + "'");

                        System.err.println("Fehler beim Zahlenformat!" + data[4] + " " + data[5]);
                    }

                } else if(data.length == 4){
                    char firstChar = data[0].charAt(0);
                    /*use class project.Fahrer*/
                    if(firstChar == 'F'){
                        /*create new object and add to fahrerliste*/
                        Fahrer neu = new Fahrer(data[0], data[1], data[2], data[3]);
                        dataClass.fahrerListe.add(neu);
                    }
                    /*use class Dienstwagen*/
                    if(firstChar == 'V'){
                        /*create new object and add to dienstwagenliste*/
                        Dienstwagen neu = new Dienstwagen(data[0], data[1], data[2], data[3]);
                        dataClass.dienstwagenListe.add(neu);
                    }
                }
            }

        }
        /*throw exception if file could not be located*/
        catch(FileNotFoundException e){
            System.err.println("Datei wurde nicht gefunden!");
        }
        /*throw exception if there are problems with reading file*/
        catch(IOException e){
            System.err.println("Etwas ist falsch gelaufen (I/O exception)");
        }
    }

//    private static long convertToUnixTime(String timeString){
//        // 2024-08-03T19:17:0
//        String[] split = timeString.split("T");
//        String[] zeitStringJahre = split[0].split("-");
//        int year = Integer.parseInt(zeitStringJahre[0]);
//        int month = Integer.parseInt(zeitStringJahre[1]);
//        int day = Integer.parseInt(zeitStringJahre[2]);
//
//        String[] zeitStringTage = split[1].split(":");
//        int hour = Integer.parseInt(zeitStringTage[0]);
//        int minute = Integer.parseInt(zeitStringTage[1]);
//        int second = Integer.parseInt(zeitStringTage[2]);
//
//        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
//        return dateTime.toEpochSecond(ZoneOffset.UTC);
//    }
}
