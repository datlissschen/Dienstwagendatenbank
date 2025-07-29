package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.Data;
import project.exception.DataImportException;
import project.io.DataModellReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DataModellReaderTests {

    private Path tempFile;

    @BeforeEach
    void setup() throws IOException {
        // Temporäre Datei anlegen
        tempFile = Files.createTempFile("data_model", ".csv");
    }

    @Test
    void testThrowsExceptionForMissingFile() {
        String nonExistentPath = "non_existent_file.csv";
        DataModellReader reader = new DataModellReader(nonExistentPath);

        Data data = new Data();
        assertThrows(DataImportException.class, () -> reader.populate(data));
    }

    @Test
    void testInvalidLineIsIgnored() throws IOException, DataImportException {
        String content = """
            F1,Max,Muster,max@example.com
            Invalid,line,not,matching,format
            V1,Audi,A4,BL-123
            """;

        Files.writeString(tempFile, content);

        Data data = new Data();
        DataModellReader reader = new DataModellReader(tempFile.toString());

        assertDoesNotThrow(() -> reader.populate(data));
        assertEquals(1, data.getFahrerListe().size());
        assertEquals(1, data.getDienstwagenListe().size());
        assertEquals(0, data.getFahrtenListe().size());
    }

    @Test
    void testEmptyFile() throws IOException, DataImportException {
        Files.writeString(tempFile, "");

        Data data = new Data();
        DataModellReader reader = new DataModellReader(tempFile.toString());

        reader.populate(data);

        assertTrue(data.getFahrerListe().isEmpty());
        assertTrue(data.getDienstwagenListe().isEmpty());
        assertTrue(data.getFahrtenListe().isEmpty());
    }
    @Test
    void testInvalidTimeFormatInFahrt() throws IOException {
        String content = """
        F1,Max,Muster,max@example.com
        V1,Audi,A4,BL-123
        F123,V1,100,200,invalid-start,2024-07-01T11:00:00
        """;

        Files.writeString(tempFile, content);

        Data data = new Data();
        DataModellReader reader = new DataModellReader(tempFile.toString());

        // Erwartung: keine Exception nach außen, Fahrt wird einfach nicht hinzugefügt
        assertDoesNotThrow(() -> reader.populate(data));
        assertEquals(1, data.getFahrerListe().size());
        assertEquals(1, data.getDienstwagenListe().size());
        assertEquals(0, data.getFahrtenListe().size());
    }

}
