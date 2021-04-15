package com.foxminded;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
    streamer streamer = new streamer();
//    System.out.println("please enter your log names");
//    System.out.print("start log name ---->");
//    String start = scan.nextLine();
//    System.out.print("end log name   ---->");
//    String end = scan.nextLine();
//    streamer.compute(start, end);
    streamer.compute("start.log", "end.log");

    }
}
