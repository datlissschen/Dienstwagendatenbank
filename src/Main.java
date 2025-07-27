import project.*;
import project.io.DataModellReader;
import project.io.ModelReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*load the data in the beginning*/

        Data data = new Data();
        ModelReader reader = new DataModellReader("dienstwagenprojekt2025.db");
        reader.populate(data);
        Logik logik = new Logik(data);

        if (args.length > 0) {
            String arg1 = args[0];

            if (arg1.startsWith("--fahrerZeitpunkt")) {
                String inputForBlitzer = arg1.substring(arg1.indexOf("=") + 1).replace("\"", "");
                List<String> results = logik.blitzer(inputForBlitzer);
                if (!results.isEmpty()) System.out.println(results.getFirst());
            } else if(arg1.startsWith("--fahrerDatum")) {
                String inputForFundsuche = arg1.substring(arg1.indexOf("=") + 1).replace("\"", "");
                List<String> results = logik.fundsuche(inputForFundsuche);
                System.out.println(String.join(", ", results));
            }
        }
    }
}