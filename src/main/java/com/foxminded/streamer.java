package com.foxminded;


import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class streamer {

    private final String nameOfStartLogFile = "start.log";
    private final String nameOfEndLogFile = "end.log";
    private final String nameOfAbbreviationsFile = "abbreviations.txt";

    public void compute(String startLog, String endLog){

        String[] resultStart = reader(nameOfStartLogFile);
        String[] resultEnd = reader(nameOfEndLogFile);
        System.out.println( match(resultStart, resultEnd));
    }

    private String[] reader(String nameFile){
        File file = new File(getClass().getClassLoader().getResource(nameFile).getFile());

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

    private StringBuilder match(String[] start, String[] end){
        List<String> list = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        for (String startElements: start) {
            String startEl = startElements.substring(0, 3);
                for (String endElements: end) {
                    String endEl = endElements.substring(0, 3);
                if (startEl.equals( endEl)){
                    list.add(startEl + time(startElements, endElements));
                }
            }
        }
        sort(list);
        return viewMaker(list);
    }
    private String time(String startInput, String endInput){
        String StartTime = startInput.substring(3);
        String EndTime =  endInput.substring(3);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
        Date startDate = new Date();
        Date endDate = new Date();
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            startDate = format.parse(StartTime);
            endDate = format.parse(EndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long resultTime = endDate.getTime() - startDate.getTime();
        Date result = new Date(resultTime);
        SimpleDateFormat format1 =  new SimpleDateFormat("mm:ss.SSS");
        format1.setTimeZone(TimeZone.getTimeZone("GMT"));

        return format1.format(result);
    }

    private StringBuilder viewMaker(List list){
        String[] abbreviature = reader(nameOfAbbreviationsFile);
        StringBuilder result = new StringBuilder();
        Iterator iterator = list.iterator();
        for (int j = 1; j < list.size()+1; j++) {
            if (iterator.hasNext()) {
                String iterable = iterator.next().toString();
            for (int i = 0; i < abbreviature.length; i++) {
                String elements = abbreviature[i];
                    if (iterable.substring(0, 3).equals(elements.substring(0, 3))) {
                        result.append(j).append(elements.substring(3).replaceAll("_", "|")).append("|").append(iterable.substring(3)).append('\n');
                    }
                }
            }
        }
        return result;

    }
    private void sort(List<String> strings) {
        Collections.sort(strings, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });
    }
}
