package tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import project.*;

public class DienstwagenTests {

    /*testing the getters*/
    @Test
    public void testDienstwagenGetters() {
        Dienstwagen wagen = new Dienstwagen("V001", "Audi", "A4", "M-AA 1234");

        assertEquals("V001", wagen.getFahrzeugId());
        assertEquals("Audi", wagen.getHersteller());
        assertEquals("A4", wagen.getModell());
        assertEquals("M-AA 1234", wagen.getKennzeichen());
    }

    /*testing if Dienstwagen acts correctc if it is empty*/
    @Test
    public void testEmptyStrings() {
        Dienstwagen wagen = new Dienstwagen("", "", "", "");

        assertEquals("", wagen.getFahrzeugId());
        assertEquals("", wagen.getHersteller());
        assertEquals("", wagen.getModell());
        assertEquals("", wagen.getKennzeichen());
    }
}
