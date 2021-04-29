package com.foxminded;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        TimeCounter timeCounter = new TimeCounter();
        timeCounter.compute("start.log", "end.log", "abbreviations.txt");
    }
}
