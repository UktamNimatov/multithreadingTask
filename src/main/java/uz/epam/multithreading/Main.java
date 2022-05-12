package uz.epam.multithreading;

import uz.epam.multithreading.entity.Van;

public class Main {
    public static void main(String[] args) {
        Van van = new Van(1, true, Van.Task.UNLOAD);
    }
}
