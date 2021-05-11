package com.foxminded;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TimeCounter timeCounter = new TimeCounter();
        System.out.print(timeCounter.compute("start.log", "end.log", "abbreviations.txt"));
    }
}
