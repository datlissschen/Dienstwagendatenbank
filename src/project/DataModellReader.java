package project;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;



public class DataModellReader {
    /*import file and convert it into the data models Dienstwagen, project.Fahrt and project.Fahrer*/

    /*create the lists with the data models*/


    public static void populateModels(String source) {

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
                /* skip the definition for new entity*/
                String firstData = data[0].trim();
                if(firstData.startsWith("New_Entity:")){
                    continue;
                }

                /*differ into the different models*/
                /*use class Fahrten*/
                if (data.length == 6){
                    try{
                        /*create a new object and parse the startKm and endKm from String in int, add object to fahrtenListe*/
                        Fahrt neu = new Fahrt(data[0], data[1], Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()), data[4], data[5]);
                        Data.fahrtenListe.add(neu);
                    }
                    catch(NumberFormatException e){
                        /*find mistake */
                        System.out.println("Verarbeite Zeile als project.Fahrt: " + line);
                        System.out.println("data[2]: '" + data[2] + "', data[3]: '" + data[3] + "'");

                        System.err.println("Fehler beim Zahlenformat!");
                    }

                } else if(data.length == 4){
                    char firstChar = data[0].charAt(0);
                    /*use class project.Fahrer*/
                    if(firstChar == 'F'){
                        /*create new object and add to fahrerliste*/
                        Fahrer neu = new Fahrer(data[0], data[1], data[2], data[3]);
                        Data.fahrerListe.add(neu);
                    }
                    /*use class Dienstwagen*/
                    if(firstChar == 'V'){
                        /*create new object and add to dienstwagenliste*/
                        Dienstwagen neu = new Dienstwagen(data[0], data[1], data[2], data[3]);
                        Data.dienstwagenListe.add(neu);
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
}
