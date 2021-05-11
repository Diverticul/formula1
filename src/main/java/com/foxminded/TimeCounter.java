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
    private static final String FORMULA_FOR_SIZE_LINE = "%-27s";
    private static final String RIDERS_SORTING_FORMULA = "\\D";
    private static final String INPUT_DATE_FORMAT = "yyyy-MM-dd_HH:mm:ss.SSS";
    private static final String DESERVE_DATE_FORMAT = "mm:ss.SSS";
    private static final String NEW_LINE_SYMBOL = "\n";
    private static final String EXISTING_DELIMITER = "_";
    private static final String DESIRED_DELIMITER = "|";
    private static final String TIME_ZONE = "GMT";

    List<Racer> collect = new ArrayList<>();

    public String compute(String nameOfStartLogFile, String nameOfEndLogFile, String nameOfFileWithAbbreviations) throws IOException {
        return match(reader(nameOfStartLogFile), reader(nameOfEndLogFile), reader(nameOfFileWithAbbreviations));
    }

    private List<String> reader(String fileName) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }
        URL resourceUrl = getClass().getClassLoader().getResource(fileName);
        if (resourceUrl == null) {
            throw new FileNotFoundException();
        }
        List<String> result;
        try (FileReader fileReader = new FileReader(resourceUrl.getFile());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            result = bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException();
        }
        return result;
    }

    private String match(List<String> startTime, List<String> endTime, List<String> abbreviations) {
        Collections.sort(startTime);
        Collections.sort(endTime);
        Collections.sort(abbreviations);
        for (int i = 0; i < startTime.size(); i++) {
            Racer racer = new Racer();
            String nameAndModelCar = abbreviations.get(i).substring(ABBREVIATIONS_LENGTH + EXISTING_DELIMITER.length());
            racer.setName(nameAndModelCar.substring(0, nameAndModelCar.indexOf(EXISTING_DELIMITER)));
            racer.setTime(time(startTime.get(i), endTime.get(i)));
            racer.setCarModel(nameAndModelCar.substring(nameAndModelCar.indexOf(EXISTING_DELIMITER) + EXISTING_DELIMITER.length()));
            collect.add(racer);
        }
        sort(collect);
        return viewMaker().toString();
    }

    private StringBuilder viewMaker() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < collect.size(); i++) {
            String pilotName = String.format(FORMULA_FOR_SIZE_LINE, i + 1 + DESIRED_DELIMITER + collect.get(i).getName());
            String pilotCar = String.format(FORMULA_FOR_SIZE_LINE, DESIRED_DELIMITER + collect.get(i).getCarModel());
            if (i == SIZE_OF_WINNERS_TABLE) {
                result.append(Stream.generate(() -> String.valueOf('-')).limit(result.indexOf(NEW_LINE_SYMBOL)).collect(Collectors.joining())).append(NEW_LINE_SYMBOL);
            }
            result.append(pilotName).append(pilotCar).append(DESIRED_DELIMITER).append(collect.get(i).getTime()).append(NEW_LINE_SYMBOL);
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


    private void sort(List<Racer> strings) {
        strings.sort(new Comparator<Racer>() {
            public int compare(Racer firstString, Racer secondString) {
                return extractInt(firstString.getTime()) - extractInt(secondString.getTime());
            }

            int extractInt(String line) {
                String num = line.replaceAll(RIDERS_SORTING_FORMULA, "");
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });


    }

}
