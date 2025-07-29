package tests;

import org.junit.jupiter.api.Test;
import project.model.Fahrt;

import static org.junit.jupiter.api.Assertions.*;

class FahrtTests {

    @Test
    void testFahrtConstructorAndGetters() {
        // Beispiel-Datq
        String fahrerID = "F123";
        String fahrzeugID = "V456";
        int startKm = 10000;
        int endKm = 10100;
        long startzeit = 1722202800L;
        long endzeit = 1722206400L;

        Fahrt fahrt = new Fahrt(fahrerID, fahrzeugID, startKm, endKm, startzeit, endzeit);

        // Test Getter
        assertEquals(fahrerID, fahrt.getFahrerID());
        assertEquals(fahrzeugID, fahrt.getFahrzeugID());
        assertEquals(startKm, fahrt.getStartKm());
        assertEquals(endKm, fahrt.getEndKm());
        assertEquals(startzeit, fahrt.getStartzeit());
        assertEquals(endzeit, fahrt.getEndzeit());
    }

    @Test
    void testNegativeKilometerValues() {
        Fahrt fahrt = new Fahrt("F001", "V001", -10, -5, 0L, 1000L);
        assertEquals(-10, fahrt.getStartKm());
        assertEquals(-5, fahrt.getEndKm());
    }

    @Test
    void testStartzeitBeforeEndzeit() {
        long start = 1000L;
        long end = 2000L;
        Fahrt fahrt = new Fahrt("F999", "V999", 0, 1, start, end);
        assertTrue(fahrt.getStartzeit() < fahrt.getEndzeit());
    }

    @Test
    void testEqualStartAndEndzeit() {
        long time = 123456789L;
        Fahrt fahrt = new Fahrt("F111", "V222", 10, 20, time, time);
        assertEquals(time, fahrt.getStartzeit());
        assertEquals(time, fahrt.getEndzeit());
    }
}
