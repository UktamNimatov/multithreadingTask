package uz.epam.multithreading.reader.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uz.epam.multithreading.exception.ProjectException;
import uz.epam.multithreading.reader.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ReaderImpl implements Reader {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String readSingleVan(File file) {
        String toReturn = "";
        try (Scanner scanner = new Scanner(file)){
            if (scanner.hasNext()) {
                toReturn = scanner.nextLine();
            }
        }catch (FileNotFoundException exception){
            logger.error("Could not find the file " + exception);
            throw new ProjectException("Could not find the file ", exception);
        }
        return toReturn;
    }

    @Override
    public List<String> readMultipleVans(File file) {
        List<String> toReturn = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNext()) {
                toReturn.add(scanner.nextLine());
            }
        }catch (FileNotFoundException exception){
            logger.error("Could not find the file " + exception);
            throw new ProjectException("Could not find the file ", exception);
        }
        return toReturn;
    }
}
