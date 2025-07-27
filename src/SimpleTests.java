import project.Data;
import project.Logik;

public class SimpleTests {
    public static void main(String[] args) {
        //testing the logic

        Logik logik = new Logik(new Data("src/project/dienstwagenprojekt2025.db"));
        String sucher = "Hoff";
        System.out.println("Starte Suche für Hoff");
        logik.print(logik.fahrersuche(sucher));

        String dienstwagen = "Golf";
        System.out.println("Starte Suche für Golf");
        logik.print(logik.fahrzeugsuche(dienstwagen));

        String testinput = "V001;2024-01-01T19:00:00";
        System.out.println("Starte Suche nach Raser");
        logik.print(logik.blitzer(testinput));

        String fundsucherInput1 = "F003;2024-08-13";
        System.out.println("Starte Fundsuche");
        logik.printfundsuche(logik.fundsuche(fundsucherInput1));

    }
}
