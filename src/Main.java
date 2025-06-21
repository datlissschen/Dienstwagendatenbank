import project.DataModellReader;
import project.*;

public class Main {
    public static void main(String[] args) {

        /*load the data in the beginning*/
        DataModellReader.populateModels("src/project/dienstwagenprojekt2025.db");


        /*try some tests*/
        String suchanfrageFahrer = "Hoff";
        System.out.println("Starte Suche für Hoff");
        Logik.fahrersuche(suchanfrageFahrer);

        String suchanfrageFahrzeuge = "Ford";
        System.out.println("Starte Suche für Fahrzeug ");
        Logik.fahrzeugsuche(suchanfrageFahrzeuge);

        String blitzertest = "S-MN-9932;2024-02-14T13:57:43";
        System.out.println("Starte Blitzertest für: " + blitzertest);
        Logik.blitzer(blitzertest);

        String fundsuchanfrageFahrer = "F003;2024-08-13"; // Beispiel-Suchanfrage
        System.out.println("Starte Fundsuche für: " + fundsuchanfrageFahrer);
        Logik.fundsuche(fundsuchanfrageFahrer);

    }
}