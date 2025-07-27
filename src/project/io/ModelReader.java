package project.io;

import project.Data;
import project.exception.DataImportException;

public interface ModelReader {
    void populate(Data data) throws DataImportException;
}
