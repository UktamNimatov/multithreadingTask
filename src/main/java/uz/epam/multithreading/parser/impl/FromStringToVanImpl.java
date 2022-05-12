package uz.epam.multithreading.parser.impl;

import uz.epam.multithreading.entity.Van;
import uz.epam.multithreading.parser.FromStringToVan;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FromStringToVanImpl implements FromStringToVan {

    private static final String DELIMITER = "\\s+";

    @Override
    public Van parseOne(String word) {
        Van van;

        String[] parameters = word.split(DELIMITER);
        int id = Integer.parseInt(parameters[0]);
        boolean isPerishable = Boolean.parseBoolean(parameters[1]);
        Van.Task task = Van.Task.valueOf(parameters[2]);

        van = new Van(id, isPerishable, task);
        return van;
    }

    @Override
    public List<Van> parseMultiple(List<String> words) {
        return words.stream()
                .map(this::parseOne)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }
}
