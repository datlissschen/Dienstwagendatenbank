import project.DataModellReader;
import project.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*load the data in the beginning*/

        Data data = new Data("src/project/dienstwagenprojekt2025.db");
        Logik logik = new Logik(data);

        /*try some tests*/
        String suchanfrageFahrer = "Hoff";
        System.out.println("Starte Suche für Hoff");
        logik.print(logik.fahrersuche(suchanfrageFahrer));

        String suchanfrageFahrzeuge = "Ford";
        System.out.println("Starte Suche für Fahrzeug ");
        logik.print(logik.fahrzeugsuche(suchanfrageFahrzeuge));

        String blitzertest = "S-MN-9932;2024-02-14T13:57:43";
        System.out.println("Starte Blitzertest für: " + blitzertest);
        logik.print(logik.blitzer(blitzertest));

        String fundsuchanfrageFahrer = "F003;2024-08-13"; // Beispiel-Suchanfrage
        System.out.println("Starte Fundsuche für: " + fundsuchanfrageFahrer);
        logik.printFundsuchanfrage(logik.fundsuche(fundsuchanfrageFahrer));

        data.fahrerListe.clear();

        System.out.println("Starte Suche für Hoff");
        logik.print(logik.fahrersuche(suchanfrageFahrer));

    }
}