package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import project.*;

public class FahrerTests {

    /*testing the getters*/
    @Test
    public void testFahrerGetters() {
        Fahrer fahrer = new Fahrer("F001", "Max", "Mustermann", "B");

        assertEquals("F001", fahrer.getFahrerID());
        assertEquals("Max", fahrer.getVorname());
        assertEquals("Mustermann", fahrer.getNachname());
        assertEquals("B", fahrer.getFuehrerscheinklasse());
    }

    /*testing if there are problems when Fahrer is empty*/
    @Test
    public void testEmptyFahrer() {
        Fahrer fahrer = new Fahrer("", "", "", "");

        assertEquals("", fahrer.getFahrerID());
        assertEquals("", fahrer.getVorname());
        assertEquals("", fahrer.getNachname());
        assertEquals("", fahrer.getFuehrerscheinklasse());
    }
}
