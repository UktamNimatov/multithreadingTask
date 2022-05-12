package uz.epam.multithreading.parser;

import uz.epam.multithreading.entity.Van;

import java.util.List;

public interface FromStringToVan {

    Van parseOne(String word);

    List<Van> parseMultiple(List<String> words);

}
