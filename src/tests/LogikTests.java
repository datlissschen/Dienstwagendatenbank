package tests;

import org.junit.jupiter.api.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import project.*;

import static org.junit.jupiter.api.Assertions.*;

public class LogikTests {

    /*
    *  some of the methods use System.out or System.err
    *  with ByteArrayOutputStream catching that kind of output is possible.
    * */
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    /*prepare for tests*/
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        // Reset test data
        Data.fahrerListe.clear();
        Data.dienstwagenListe.clear();
        Data.fahrtenListe.clear();
    }

    /*implement it again correctly, so that System.out or System.err works correctly*/
    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /*test whether a Fahrer can be found by their Vorname*/
    @Test
    public void testFahrersuche_byVorname() {
        Data.fahrerListe.add(new Fahrer("F001", "Max", "Mustermann", "B"));
        Logik.fahrersuche("Max");
        assertTrue(outContent.toString().contains("F001, Max Mustermann, B"));
    }

    /*test whether a Dienstwagen can be found based on it's Kennzeichen*/
    @Test
    public void testFahrzeugsuche_byKennzeichen() {
        Data.dienstwagenListe.add(new Dienstwagen("V001", "Audi", "A4", "B-AB-1234"));
        Logik.fahrzeugsuche("B-AB");
        assertTrue(outContent.toString().contains("V001, B-AB-1234, Audi, A4"));
    }

    /*test the correct verification and output of Fahrer*/
    @Test
    public void testBlitzer_found() {
        /*create new objects Fahrer for test*/
        Fahrer fahrer = new Fahrer("F002", "Lisa", "Müller", "B");
        Fahrt fahrt = new Fahrt("F002", "V002", 0, 100, "2025-06-19T08:00:00", "2025-06-19T09:00:00");

        Data.fahrerListe.add(fahrer);
        Data.fahrtenListe.add(fahrt);

        Logik.blitzer("V002;2025-06-19T08:00:00");
        assertTrue(outContent.toString().contains("Gefundener project.Fahrer: Lisa Müller"));
    }

    /* test if the wrong format is sorted out*/
    @Test
    public void testBlitzer_invalidFormat() {
        Logik.blitzer("V002-2025-06-19T08:00:00");
        assertTrue(errContent.toString().contains("Fehler: Ungültiges Eingabeformat"));
    }

    /*test how the method behaves when no matching trip is found.*/
    @Test
    public void testBlitzer_noMatch() {
        Logik.blitzer("V002;2025-06-19T08:00:00");
        assertTrue(outContent.toString().contains("Kein project.Fahrer gefunden"));
    }

    /*test whether method correctly identifies other drivers who used the same vehicle on the same day as the "fundsucher"*/
    @Test
    public void testFundsuche_basicMatch() {
        // fundsucher
        Data.fahrerListe.add(new Fahrer("F001", "Ben", "Wagner", "B"));
        // different Fahrer
        Data.fahrerListe.add(new Fahrer("F002", "Mia", "Hoffmann", "B"));

        /*both drive the same car on the same day*/
        Data.fahrtenListe.add(new Fahrt("F001", "S-GH-3277", 100, 200, "2025-06-20T08:00:00", "2025-06-20T09:00:00"));
        Data.fahrtenListe.add(new Fahrt("F002", "S-GH-3277", 300, 400, "2025-06-20T10:00:00", "2025-06-20T11:00:00"));

        Logik.fundsuche("F001;2025-06-20");

        assertTrue(outContent.toString().contains("Mia Hoffmann (S-GH-3277)"));
    }

    /*test if the method acts correctly if fundsuche is not successful because the "fundsucher" has not driven anything*/
    @Test
    public void testFundsuche_noDrivesForFundsucher() {
        Data.fahrerListe.add(new Fahrer("F003", "Anna", "Klein", "B"));
        Logik.fundsuche("F003;2025-06-20");
        assertTrue(outContent.toString().contains("Keine project.Fahrer gefunden"));
    }

    /*test whether the format/input is tested before using the function to catch errors*/
    @Test
    public void testFundsuche_invalidInput() {
        Logik.fundsuche("F001_only");
        assertTrue(errContent.toString().contains("Fehler: Das Eingabeformat ist ungültig"));
    }

    /*test if the method acts correctly if fundsuche is not successful*/
    @Test
    public void testFundsuche_noCommonDrivers() {
        Data.fahrerListe.add(new Fahrer("F001", "Ben", "Wagner", "B"));
        Data.fahrerListe.add(new Fahrer("F002", "Mia", "Hoffmann", "B"));

        // fundsucher fährt Auto A
        Data.fahrtenListe.add(new Fahrt("F001", "CAR-123", 0, 100, "2025-06-20T08:00:00", "2025-06-20T09:00:00"));
        // anderer Fahrer fährt Auto B
        Data.fahrtenListe.add(new Fahrt("F002", "CAR-456", 0, 100, "2025-06-20T08:00:00", "2025-06-20T09:00:00"));

        Logik.fundsuche("F001;2025-06-20");
        assertTrue(outContent.toString().contains("Keine project.Fahrer gefunden"));
    }
}
