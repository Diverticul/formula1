package com.foxminded;

import javax.lang.model.util.SimpleElementVisitor6;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class streamer {

    public void compute(String startLog, String endLog){
        File start = new File(getClass().getClassLoader().getResource(startLog).getFile());
        File end = new File(getClass().getClassLoader().getResource(endLog).getFile());
        String[] resultStart = reader(start);
        String[] resultEnd = reader(end);
        match(resultStart, resultEnd);
    }

    private String[] reader(File file){

        FileReader streamOfStartLog;
        String result = null;
        try {
             streamOfStartLog = new FileReader(file);
            BufferedReader ReaderOfInputStream = new BufferedReader(streamOfStartLog);

            result = ReaderOfInputStream.lines().parallel().collect(Collectors.joining("\n"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result.split("\n");
    }

    private void match(String[] start, String[] end){
        Map<String , String> speed = new HashMap<>();
        String time = null;
        for (String elements: start) {
                for (String elements2: end)
                {
                    String startEl = elements2.substring(0, 3);
                    String endEl = elements.substring(0, 3);
                if (startEl.equals( endEl)){
                    speed.put(startEl, time(elements, elements2) );
                }
            }
        }
    }
    private String time(String startInput, String endInput){
        String StartTime = startInput.substring(startInput.indexOf(':')-2);
        String EndTime =  endInput.substring(endInput.indexOf(':') -2);
//        SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd_mm:ss.SSS");
//
//        LocalTime start = LocalTime.parse(StartTime, formatter);
//        LocalTime end = LocalTime.parse(EndTime, formatter);
//        Duration duration = Duration.between(start, end);
        LocalTime start = LocalTime.parse(StartTime);
        LocalTime end = LocalTime.parse(EndTime);
        Duration duration = Duration.between(start, end);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS").withLocale(Locale.US);
        System.out.println(duration);
        System.out.println(format(duration)+ " это медот");
        return duration.toString();

    }public static String format(Duration d) {
        long days = d.toDays();
        d = d.minusDays(days);
        long hours = d.toHours();
        d = d.minusHours(hours);
        long minutes = d.toMinutes();
        d = d.minusMinutes(minutes);
        long seconds = d.getSeconds() ;
        return
                (days ==  0?"":days+" jours,")+
                        (hours == 0?"":hours+" heures,")+
                        (minutes ==  0?"":minutes+" minutes,")+
                        (seconds == 0?"":seconds+" secondes,");
    }
}
