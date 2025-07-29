package project.io;

import project.*;
import project.exception.DataImportException;
import project.model.Dienstwagen;
import project.model.Fahrer;
import project.model.Fahrt;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import static project.util.HelperMethods.convertToUnixTime;


public class DataModelReader implements ModelReader {
    // import file and convert it into data models Dienstwagen,Fahrt and Fahrer

    // create lists with data models
    private final String fileName;

    public DataModelReader(String fileName) {
        this.fileName = fileName;
    }

    // #TODO javadoc
    @Override
    public void populate(Data dataClass) throws DataImportException {

        /*read file, if not possible throw exception*/
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line;
            while((line = reader.readLine()) != null){
                line = line.replaceAll("\"", "");
                if(line.isEmpty()){
                    continue;
                }

                String[] data = line.split(",");
                String firstData = data[0].trim();
                // skip the definition for new entity
                if(firstData.startsWith("New_Entity:")){
                    continue;
                }

                /*differ into the different models*/
                if (data.length == 6){
                    try{
                        long startzeit = convertToUnixTime(data[4]);
                        long endzeit = convertToUnixTime(data[5]);

                        // 2024-01-30T00:40:28
                        String[] endZeitString = data[5].split("T");

                        // create a new object and parse startKm and endKm from String in int, add object to fahrtenListe
                        Fahrt neu = new Fahrt(data[0], data[1], Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()), startzeit, endzeit);
                        dataClass.addFahrt(neu);
                    }
                    catch(NumberFormatException e){
                        // find mistake
//                        System.out.println("Verarbeite Zeile als project.model.Fahrt: " + line);
//                        System.out.println("data[2]: '" + data[2] + "', data[3]: '" + data[3] + "'");
//
//                        System.err.println("Fehler beim Zahlenformat!" + data[4] + " " + data[5]);
                        continue;
                    }

                } else if(data.length == 4){
                    char firstChar = data[0].charAt(0);
                    if(firstChar == 'F'){
                        /*create new object and add to fahrerliste*/
                        Fahrer neu = new Fahrer(data[0], data[1], data[2], data[3]);
                        dataClass.addFahrer(neu);
                    }
                    if(firstChar == 'V'){
                        /*create new object and add to dienstwagenliste*/
                        Dienstwagen neu = new Dienstwagen(data[0], data[1], data[2], data[3]);
                        dataClass.addDienstwagen(neu);
                    }
                }
            }

        }
        /*throw exception if file could not be located*/
        catch(FileNotFoundException e){
            throw new DataImportException("Datei wurde nicht gefunden: " + fileName, e);
        }
        /*throw exception if there are problems with reading file*/
        catch(IOException e){
            throw new DataImportException("Etwas ist falsch gelaufen (I/O exception) " + fileName, e);
        }
    }

}
