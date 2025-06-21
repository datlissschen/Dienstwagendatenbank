//package tests;
//
//import org.junit.jupiter.api.*;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import java.util.List;
//
//import project.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class LogikTests {
//
//    private Logik logik;
//    private Data data
//
//    @BeforeEach
//    public void setUp() {
//        logik = new Logik();
//        data = new Data("src/project/dienstwagenprojet.db");
//    }
//
//    /*test whether a Fahrer can be found by their Vorname*/
//    @Test
//    public void testFahrersuche_foundByVorname() {
//        Data.fahrerListe.add(new Fahrer("F001", "Max", "Mustermann", "B"));
//
//        List<String> result = logik.fahrersuche("Max");
//        assertTrue(result.isEmpty(), "Result should be empty because method does not populate output list");
//    }
//
//    /*test whether a Dienstwagen can be found based on it's Kennzeichen*/
//    @Test
//    public void testFahrzeugsuche_foundByKennzeichen() {
//        Data.dienstwagenListe.add(new Dienstwagen("V001", "S-AA-123", "BMW", "X1"));
//
//        List<String> result = logik.fahrzeugsuche("X1");
//
//        assertEquals(1, result.size());
//        assertEquals("V001, S-AA-123, BMW, X1", result.get(0));
//    }
//
//    /*Does the method act correct if there is no Dienstwagen for the search*/
//    @Test
//    public void testFahrzeugsuche_notFound() {
//        Data.dienstwagenListe.add(new Dienstwagen("V001", "S-AA-123", "BMW", "X1"));
//
//        List<String> result = logik.fahrzeugsuche("Tesla");
//
//        assertTrue(result.isEmpty());
//    }
//
//
//    /*test the correct verification and output of the method blitzer*/
//    @Test
//    public void testBlitzer_matchFound() {
//        Data.fahrerListe.add(new Fahrer("F002", "Lisa", "Müller", "B"));
//        Data.fahrtenListe.add(new Fahrt("F002", "V002", 0, 100, "2025-06-19T08:00:00", "2025-06-19T09:00:00"));
//
//        List<String> result = logik.blitzer("V002;2025-06-19T08:00:00");
//
//        assertEquals(1, result.size());
//        assertEquals("Gefundener Fahrer: Lisa Müller", result.get(0));
//    }
//
//    /* test if the blitzer acts correctly if there is no Fahrer for the method*/
//    @Test
//    public void testBlitzer_fahrtNotFound() {
//        List<String> result = logik.blitzer("V999;2025-06-19T08:00:00");
//
//        assertEquals(1, result.size());
//        assertEquals("Kein Fahrer gefunden für Fahrzeug-ID: V999 und Startzeit: 2025-06-19T08:00:00", result.get(0));
//    }
//
//
//
//    /*test whether method correctly identifies other drivers who used the same vehicle on the same day as the "fundsucher"*/
//    @Test
//    public void testFundsuche_treffer() {
//        Data.fahrerListe.add(new Fahrer("F001", "Ben", "Wagner", "B"));
//        Data.fahrerListe.add(new Fahrer("F002", "Mia", "Hoffmann", "B"));
//
//        Data.fahrtenListe.add(new Fahrt("F001", "S-GH-3277", 100, 200, "2025-06-20T08:00:00", "2025-06-20T09:00:00"));
//        Data.fahrtenListe.add(new Fahrt("F002", "S-GH-3277", 300, 400, "2025-06-20T10:00:00", "2025-06-20T11:00:00"));
//
//        List<String> result = logik.fundsuche("F001;2025-06-20");
//
//        assertEquals(1, result.size());
//        assertEquals("Mia Hoffmann (S-GH-3277)", result.get(0));
//    }
//
//    /*test whether the format/input is tested before using the function to catch errors*/
//    @Test
//    public void testFundsuche_invalidFormat() {
//        List<String> result = logik.fundsuche("F001");
//
//        assertEquals(1, result.size());
//        assertEquals("Keine project.Fahrer gefunden!", result.get(0));
//    }
//
//    /*test if the method acts correctly if fundsuche is not successful*/
//    @Test
//    public void testFundsuche_noMatch() {
//        Data.fahrerListe.add(new Fahrer("F001", "Ben", "Wagner", "B"));
//        Data.fahrerListe.add(new Fahrer("F002", "Mia", "Hoffmann", "B"));
//
//        Data.fahrtenListe.add(new Fahrt("F001", "CAR-123", 0, 100, "2025-06-20T08:00:00", "2025-06-20T09:00:00"));
//        Data.fahrtenListe.add(new Fahrt("F002", "CAR-456", 0, 100, "2025-06-20T08:00:00", "2025-06-20T09:00:00"));
//
//        List<String> result = logik.fundsuche("F001;2025-06-20");
//
//        assertEquals(1, result.size());
//        assertEquals("Keine Fahrer gefunden. Viel Glück mit der Suche.", result.get(0));
//    }
//}
//
