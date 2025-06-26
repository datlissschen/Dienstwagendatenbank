package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import project.*;

import java.time.LocalDateTime;

public class FahrtTests {

    private LocalDateTime startTime = LocalDateTime.of(2023,6,1,0,0);
    private LocalDateTime endTime = LocalDateTime.of(2023,6,3,0,0);

    /*testing the getters*/
    @Test
    public void testFahrtGetters() {
        Fahrt fahrt = new Fahrt("F001", "V001", 100, 200, startTime, endTime);

        assertEquals("F001", fahrt.getFahrerID());
        assertEquals("V001", fahrt.getFahrzeugID());
        assertEquals(100, fahrt.getStartKm());
        assertEquals(200, fahrt.getEndKm());
        assertEquals(startTime, fahrt.getStartzeit());
        assertEquals(endTime, fahrt.getEndzeit());
    }

    /*testing for negative km*/
    @Test
    public void testNegativeKilometerValues() {
        Fahrt fahrt = new Fahrt("F002", "V002", -50, -10, startTime, endTime);

        assertEquals(-50, fahrt.getStartKm());
        assertEquals(-10, fahrt.getEndKm());
    }

    /*testing if all the valuables are empty or zero in case of int*/
    @Test
    public void testEmptyStringsAndZeroKm() {
        Fahrt fahrt = new Fahrt("", "", 0, 0, startTime, endTime);

        assertEquals("", fahrt.getFahrerID());
        assertEquals("", fahrt.getFahrzeugID());
        assertEquals(0, fahrt.getStartKm());
        assertEquals(0, fahrt.getEndKm());
        assertEquals(startTime, fahrt.getStartzeit());
        assertEquals(endTime, fahrt.getEndzeit());
    }
}
