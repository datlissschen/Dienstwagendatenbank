import project.*;

import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*load the data in the beginning*/

        Data data = new Data("src/project/dienstwagenprojekt2025.db");
        Logik logik = new Logik(data);

        //testing the logic
        String sucher = "Hoff";
        System.out.println("Starte Suche für Hoff");
        logik.print(logik.fahrersuche(sucher));

        String dienstwagen = "Golf";
        System.out.println("Starte Suche für Golf");
        logik.print(logik.fahrzeugsuche(dienstwagen));

        String testinput = "V001;2024-01-01T19:00:00";
        System.out.println("Starte Suche nach Raser");
        logik.print(logik.blitzer(testinput));

        String blitzerInput1 = "V001;2024-01-01T19:00:00";
        logik.print(logik.blitzer(blitzerInput1));
//
//        if (args.length > 0) {
//            String arg1 = args[0];
//
//            if (arg1.startsWith("--fahrerZeitpunkt")) {
//                String inputForBlitzer = arg1.substring(arg1.indexOf("=") + 1).replace("\"", "");
//                String result = logik.blitzer(inputForBlitzer);
//                if (result != null) System.out.println(result);
//            } else if(arg1.startsWith("--fahrerDatum")) {
//                String inputForFundsuche = arg1.substring(arg1.indexOf("=") + 1).replace("\"", "");
//                List<String> results = logik.fundsuche(inputForFundsuche);
//                System.out.println(String.join(", ", results));
//            }
//        }

        /*try some tests*/
//        String suchanfrageFahrer = "Hoff";
//        System.out.println("Starte Suche für Hoff");
//        logik.print(logik.fahrersuche(suchanfrageFahrer));
////
//        String suchanfrageFahrzeuge = "Ford";
//        System.out.println("Starte Suche für Fahrzeug ");
//        logik.print(logik.fahrzeugsuche(suchanfrageFahrzeuge));
//
//        String blitzertest = "S-MN-9932;2024-02-14T13:57:43";
//        System.out.println("Starte Blitzertest für: " + blitzertest);
//        logik.print(Collections.singletonList(logik.blitzer(blitzertest)));
//
//        String fundsuchanfrageFahrer = "F003;2024-08-13"; // Beispiel-Suchanfrage
//        System.out.println("Starte Fundsuche für: " + fundsuchanfrageFahrer);
//        logik.print(logik.fundsuche(fundsuchanfrageFahrer));
//
//        data.fahrerListe.clear();
//
//        System.out.println("Starte Suche für Hoff");
//        logik.print(logik.fahrersuche(suchanfrageFahrer));



    }
}