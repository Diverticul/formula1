package com.foxminded;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        TimeCounter timeCounter = new TimeCounter();
       System.out.print(timeCounter.compute("start.log", "end.log", "abbreviations.txt"));
    }
}
