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
    private final String fileName;

    public DataModelReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * read file and transfer into models Dienstwagen fahrt and Fahrer
     * identified objects are given to the data-parameter
     * @param dataClass the data object which is to be populated
     * @throws DataImportException if no file was found, or it could not be read.
     */
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

                // differ into the different models
                if (data.length == 6){
                    try{
                        long startzeit = convertToUnixTime(data[4]);
                        long endzeit = convertToUnixTime(data[5]);

                        // format: 2024-01-30T00:40:28
                        String[] endZeitString = data[5].split("T");

                        // create a new object, parse startKm and endKm from String in int, add object to fahrtenListe
                        Fahrt neu = new Fahrt(data[0], data[1], Integer.parseInt(data[2].trim()), Integer.parseInt(data[3].trim()), startzeit, endzeit);
                        dataClass.addFahrt(neu);
                    }
                    catch(NumberFormatException e){
                        //skip
                        continue;
                    }

                } else if(data.length == 4){
                    char firstChar = data[0].charAt(0);
                    // create object based on character
                    if(firstChar == 'F'){
                        Fahrer neu = new Fahrer(data[0], data[1], data[2], data[3]);
                        dataClass.addFahrer(neu);
                    }
                    if(firstChar == 'V'){
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
