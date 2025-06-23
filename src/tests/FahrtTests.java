//package tests;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import project.*;
//
//public class FahrtTests {
//
//    /*testing the getters*/
//    @Test
//    public void testFahrtGetters() {
//        Fahrt fahrt = new Fahrt("F001", "V001", 100, 200, "2023-06-01", "2023-06-02");
//
//        assertEquals("F001", fahrt.getFahrerID());
//        assertEquals("V001", fahrt.getFahrzeugID());
//        assertEquals(100, fahrt.getStartKm());
//        assertEquals(200, fahrt.getEndKm());
//        assertEquals("2023-06-01", fahrt.getStartzeit());
//        assertEquals("2023-06-02", fahrt.getEndzeit());
//    }
//
//    /*testing for negative km*/
//    @Test
//    public void testNegativeKilometerValues() {
//        Fahrt fahrt = new Fahrt("F002", "V002", -50, -10, "2023-05-01", "2023-05-02");
//
//        assertEquals(-50, fahrt.getStartKm());
//        assertEquals(-10, fahrt.getEndKm());
//    }
//
//    /*testing if all the valuables are empty or zero in case of int*/
//    @Test
//    public void testEmptyStringsAndZeroKm() {
//        Fahrt fahrt = new Fahrt("", "", 0, 0, "", "");
//
//        assertEquals("", fahrt.getFahrerID());
//        assertEquals("", fahrt.getFahrzeugID());
//        assertEquals(0, fahrt.getStartKm());
//        assertEquals(0, fahrt.getEndKm());
//        assertEquals("", fahrt.getStartzeit());
//        assertEquals("", fahrt.getEndzeit());
//    }
//}
