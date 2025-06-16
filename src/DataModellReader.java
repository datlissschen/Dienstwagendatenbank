
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataModellReader {
    /*import file and convert it into the data models Dienstwagen, Fahrt and Fahrer*/

    /*create the lists with the data models*/
    private static List<Fahrt> fahrtenListe = new ArrayList<>();
    private static List<Fahrer> fahrerListe = new ArrayList<>();
    private static List<Dienstwagen> dienstwagenListe = new ArrayList<>();

    public static void main(String[] args) {
        String filepath = "src/dienstwagenprojekt2025.db";

        /*read file, if not possible throw exception*/
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){

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

                /*differ into the different models*/
                /*use class Fahrten*/
                if (data.length == 6){
                    /*find mistake*/
                    System.out.println("Verarbeite Zeile als Fahrt: " + line);
                    System.out.println("data[2]: '" + data[2] + "', data[3]: '" + data[3] + "'");

                    try{
                        /*create a new object and parse the startKm and endKm from String in int, add object to fahrtenListe*/
                        Fahrt neu = new Fahrt(data[0], data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4], data[5]);
                        fahrtenListe.add(neu);
                    }
                    catch(NumberFormatException e){
                        System.err.println("Fehler beim Zahlenformat!");
                    }

                } else if(data.length == 4){
                    char firstChar = data[0].charAt(0);
                    /*use class Fahrer*/
                    if(firstChar == 'F'){
                        /*create new object and add to fahrerliste*/
                        Fahrer neu = new Fahrer(data[0], data[1], data[2], data[3]);
                        fahrerListe.add(neu);
                    }
                    /*use class Dienstwagen*/
                    if(firstChar == 'V'){
                        /*create new object and add to dienstwagenliste*/
                        Dienstwagen neu = new Dienstwagen(data[0], data[1], data[2], data[3]);
                        dienstwagenListe.add(neu);
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
