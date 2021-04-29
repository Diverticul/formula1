package com.foxminded;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
    TimeCounter timeCounter = new TimeCounter();
//    System.out.println("please enter your log names");
//    System.out.print("start log name ---->");
//    String start = scan.nextLine();
//    System.out.print("end log name   ---->");
//    String end = scan.nextLine();
//    streamer.compute(start, end);
    System.out.println(timeCounter.compute("start.log", "end.log" , "abbreviations.txt"));

    }
}
