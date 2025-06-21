//package tests;
//
//import org.junit.jupiter.api.*;
//import project.*;
//
//import java.io.*;
//import java.nio.file.*;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class DataModellReaderTests {
//
//    private static final String TEST_FILE = "test_data.csv";
//    private Data testData;
//    @BeforeEach
//    public void setUp() {
//        // Clear all static lists before each test
//        this.testData = new Data(TEST_FILE);
//    }
//
//    @AfterEach
//    public void cleanUp() throws IOException {
//        Files.deleteIfExists(Paths.get(TEST_FILE));
//    }
//
//    @Test
//    public void testCorrectImportOfAllModels() throws IOException {
//        List<String> lines = Arrays.asList(
//                "\"F001\",\"Max\",\"Mustermann\",\"B\"",
//                "\"V001\",\"Audi\",\"A4\",\"M-AA 1234\"",
//                "\"F001\",\"V001\",\"100\",\"200\",\"2023-06-01\",\"2023-06-02\""
//        );
//        Files.write(Paths.get(TEST_FILE), lines);
//
//        assertEquals(1, testData.fahrerListe.size(), "project.Fahrer should be imported");
//        assertEquals(1, testData.dienstwagenListe.size(), "Dienstwagen should be imported");
//        assertEquals(1, testData.fahrtenListe.size(), "project.Fahrt should be imported");
//
//        Fahrer f = testData.fahrerListe.getFirst();
//        assertEquals("F001", f.getFahrerID());
//        assertEquals("Max", f.getVorname());
//
//        Dienstwagen v = testData.dienstwagenListe.getFirst();
//        assertEquals("Audi", v.getHersteller());
//
//        Fahrt ft = testData.fahrtenListe.getFirst();
//        assertEquals(100, ft.getStartKm());
//        assertEquals(200, ft.getEndKm());
//    }
//
//    @Test
//    public void testFileNotFoundHandling() {
//        assertDoesNotThrow(() -> DataModellReader.populateModels("non_existent.csv"));
//    }
//
//    @Test
//    public void testMalformedFahrtDataIsIgnored() throws IOException {
//        List<String> lines = List.of(
//                "F001,V001,abc,xyz,2023-06-01,2023-06-02" // invalid start/end km
//        );
//        Files.write(Paths.get(TEST_FILE), lines);
//
//        DataModellReader.populateModels(TEST_FILE);
//
//        assertEquals(0, Data.fahrtenListe.size(), "Malformed project.Fahrt rows should be ignored");
//    }
//
//    @Test
//    public void testEmptyAndEntityLinesAreIgnored() throws IOException {
//        List<String> lines = Arrays.asList(
//                "", // empty
//                "New_Entity:project.Fahrer", // should be ignored
//                "   \"\"   " // empty after trim/replace
//        );
//        Files.write(Paths.get(TEST_FILE), lines);
//
//        DataModellReader.populateModels(TEST_FILE);
//
//        assertTrue(Data.fahrerListe.isEmpty());
//        assertTrue(Data.dienstwagenListe.isEmpty());
//        assertTrue(Data.fahrtenListe.isEmpty());
//    }
//
//    @Test
//    public void testUnknownFormatLineIsIgnored() throws IOException {
//        List<String> lines = List.of(
//                "Just,Some,Random,Text,Here,Today,Unexpected" // 7 fields, doesn't match anything
//        );
//        Files.write(Paths.get(TEST_FILE), lines);
//
//        DataModellReader.populateModels(TEST_FILE);
//
//        assertTrue(Data.fahrtenListe.isEmpty());
//        assertTrue(Data.fahrerListe.isEmpty());
//        assertTrue(Data.dienstwagenListe.isEmpty());
//    }
//}
