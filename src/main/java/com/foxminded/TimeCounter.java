package com.foxminded;


import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TimeCounter {

    private static final int ABBREVIATIONS_LENGTH = 3;
    private static final int SIZE_OF_WINNERS_TABLE = 15;
    private static final String FORMULA_FOR_SIZE_LINE = "%-26s";
    private static final String INPUT_DATE_FORMAT = "yyyy-MM-dd_HH:mm:ss.SSS";
    private static final String DESERVE_DATE_FORMAT = "mm:ss.SSS";
    private static final String NEW_LINE_SYMBOL = "\n";
    private static final String EXISTING_DELIMITER = "_";
    private static final String DESIRED_DELIMITER = "|";
    private static final String TIME_ZONE = "GMT";

    public String compute(String nameOfStartLogFile, String nameOfEndLogFile, String nameOfFileWithAbbreviations) throws FileNotFoundException {
        return match(reader(nameOfStartLogFile), reader(nameOfEndLogFile), reader(nameOfFileWithAbbreviations));
    }

    private List<String> reader(String fileName) throws FileNotFoundException {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        URL resourceUrl = getClass().getClassLoader().getResource(fileName);
        if (resourceUrl == null) {
            throw new FileNotFoundException();
        }
        List<String> result = new ArrayList<>();
        try (FileReader fileReader = new FileReader(resourceUrl.getFile());
             BufferedReader readerOfInputStream = new BufferedReader(fileReader))
        {
            result = readerOfInputStream.lines().collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String match(List<String> start, List<String> end, List<String> abbreviate) {
        Collections.sort(start);
        Collections.sort(end);
        Collections.sort(abbreviate);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < start.size(); i++) {
            if (start.get(i).substring(0, ABBREVIATIONS_LENGTH).equals(end.get(i).substring(0, ABBREVIATIONS_LENGTH)) && end.get(i).substring(0, ABBREVIATIONS_LENGTH).equals(abbreviate.get(i).substring(0, ABBREVIATIONS_LENGTH))) {
                String time = time(start.get(i), end.get(i));
                result.add(start.get(i).substring(0, ABBREVIATIONS_LENGTH) + time);
            }
        }
        sort(result);
        return viewMaker(result, abbreviate).toString();
    }

    private StringBuilder viewMaker(List<String> list, List<String> abbreviations) {
        StringBuilder result = new StringBuilder();
        Iterator<String> listIterator = list.iterator();
        for (int counter = 1; counter < list.size() + 1; counter++) {
            if (listIterator.hasNext()) {
                String iterable = listIterator.next();
                for (String abbreviation : abbreviations) {
                    if (iterable.substring(0, ABBREVIATIONS_LENGTH).equals(abbreviation.substring(0, ABBREVIATIONS_LENGTH))) {
                        result.append(appender(abbreviation, iterable, counter)).append(NEW_LINE_SYMBOL);
                    }
                }
            }
        }
        return result;
    }



        private String time(String startInput, String endInput) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(INPUT_DATE_FORMAT);
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = inputFormat.parse(startInput.substring(ABBREVIATIONS_LENGTH));
            endDate = inputFormat.parse(endInput.substring(ABBREVIATIONS_LENGTH));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long resultTime = endDate.getTime() - startDate.getTime();
        Date result = new Date(resultTime);
        SimpleDateFormat outputFormat = new SimpleDateFormat(DESERVE_DATE_FORMAT);
        outputFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

        return outputFormat.format(result);
    }


    private String appender(String fullName, String time, int counter){
        String[] splitter = fullName.substring(ABBREVIATIONS_LENGTH+ EXISTING_DELIMITER.length()).split(EXISTING_DELIMITER);
        String format = String.format(FORMULA_FOR_SIZE_LINE, counter + DESIRED_DELIMITER + splitter[0]);
        String format2 = String.format(FORMULA_FOR_SIZE_LINE, splitter[1]);
        StringBuilder result = new StringBuilder(String.join(DESIRED_DELIMITER, format, format2,  time.substring(ABBREVIATIONS_LENGTH)));
        if (counter == SIZE_OF_WINNERS_TABLE) {
            result.append(NEW_LINE_SYMBOL).append(Stream.generate(() -> String.valueOf('-')).limit(result.length()  - (long) NEW_LINE_SYMBOL.length()).collect(Collectors.joining()));
        }
        return result.toString();
    }


    private void sort(List<String> strings) {
        strings.sort(new Comparator<String>() {
            public int compare(String firstString, String secondString) {
                return extractInt(firstString) - extractInt(secondString);
            }

            int extractInt(String line) {
                String num = line.replaceAll("\\D", "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });


    }
}
