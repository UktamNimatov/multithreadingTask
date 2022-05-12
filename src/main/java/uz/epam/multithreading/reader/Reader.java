package uz.epam.multithreading.reader;

import java.io.File;
import java.util.List;

public interface Reader {

    String readSingleVan(File file);

    List<String> readMultipleVans(File file);

}
