package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.Data;
import project.Logik;
import project.model.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LogikTests {

    private Logik logik;

    @BeforeEach
    void setUp() {
        Data testData = new Data();

        // Fahrer
        testData.addFahrer(new Fahrer("F1", "Max", "Mustermann", "B"));
        testData.addFahrer(new Fahrer("F2", "Erika", "Musterfrau", "BE"));
        testData.addFahrer(new Fahrer("F3", "Max", "Maier", "C"));

        // Dienstwagen
        testData.addDienstwagen(new Dienstwagen("V1", "BMW", "B-XY123", "320i"));
        testData.addDienstwagen(new Dienstwagen("V2", "Audi", "B-AB456", "A4"));

        // Fahrten
        long start = 1722210000L;
        long end = 1722213600L;
        testData.addFahrt(new Fahrt("F1", "V1", 10000, 10100, start, end));
        testData.addFahrt(new Fahrt("F2", "V1", 10100, 10200, start, end));
        testData.addFahrt(new Fahrt("F3", "V2", 5000, 5100, start, end));

        logik = new Logik(testData);
    }

    @Test
    void testFahrersuche_byVorname() {
        List<String> result = logik.fahrersuche("Max");
        assertEquals(2, result.size());
        assertTrue(result.get(0).contains("Mustermann") || result.get(1).contains("Mustermann"));
    }

    @Test
    void testFahrzeugsuche_byKennzeichen() {
        List<String> result = logik.fahrzeugsuche("B-XY");
        assertEquals(1, result.size());
        assertTrue(result.getFirst().contains("BMW"));
    }

    @Test
    void testFahrzeugsuche_noMatch() {
        List<String> result = logik.fahrzeugsuche("TESLA");
        assertEquals(1, result.size());
        assertEquals("Keine Dienstwagen vorhanden", result.getFirst());
    }

    @Test
    void testFahrersuche_noMatch() {
        List<String> result = logik.fahrersuche("Schmidt");
        assertEquals(1, result.size());
        assertEquals("Keine Fahrer vorhanden", result.getFirst());
    }

    @Test
    void testBlitzer_wrongFormat() {
        List<String> result = logik.blitzer("B-XY123");
        assertEquals(1, result.size());
        assertTrue(result.getFirst().startsWith("Fehler"));
    }

    @Test
    void testBlitzer_noHit() {
        List<String> result = logik.blitzer("B-XY123;2023-01-01T00:00:00");
        assertEquals(1, result.size());
        assertTrue(result.getFirst().startsWith("Kein Fahrer gefunden"));
    }

    @Test
    void testFundsuche_match() {
        List<String> result = logik.fundsuche("F1;2024-07-29");
        assertFalse(result.contains("Keine Fahrer gefunden"));
        assertTrue(result.getFirst().contains("Erika Musterfrau"));
    }

    @Test
    void testFundsuche_noDrivers() {
        List<String> result = logik.fundsuche("F3;2024-07-29");
        assertTrue(result.contains("Keine Fahrer gefunden"));
    }

    @Test
    void testFundsuche_noFahrtenFound() {
        List<String> result = logik.fundsuche("F1;2020-01-01");
        assertEquals(1, result.size());
        assertEquals("Keine Dienstwagen gefunden", result.getFirst());
    }

    @Test
    void testFundsuche_wrongFormat() {
        List<String> result = logik.fundsuche("F1");
        assertEquals(1, result.size());
        assertTrue(result.getFirst().startsWith("Fehler"));
    }
}
