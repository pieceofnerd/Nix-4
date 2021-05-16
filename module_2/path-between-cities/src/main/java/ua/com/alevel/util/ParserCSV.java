package ua.com.alevel.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ua.com.alevel.entity.City;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParserCSV {

    public static List<String[]> readAllDataFromDatabase(String file) {
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            return reader.readAll();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        } catch (CsvException e) {
            throw new RuntimeException();
        }
    }

    private static String[] writeCityIntoDatabase(Object o) {
        try {
            City city = (City) o;
            String[] csv = new String[2];
            csv[0] = String.valueOf(city.getId());
            csv[1] = city.getName();
            return csv;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static <T> void writeIntoDatabase(T object, String file) {
        List<String[]> allDataFromDb = readAllDataFromDatabase(file);
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            List<String[]> csvData = new ArrayList<>(allDataFromDb);
            if (object.getClass().isAssignableFrom(City.class))
                csvData.add(writeCityIntoDatabase(object));
            writer.writeAll(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writePathIntoDatabase(Integer firstCity,  Integer secondCity, Integer path, String file) {
        try {
            List<String[]> allDataFromDb = readAllDataFromDatabase(file);
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                List<String[]> csvData = new ArrayList<>(allDataFromDb);
                String[] csv = new String[3];
                csv[0] = String.valueOf(firstCity);
                csv[1] = String.valueOf(secondCity);
                csv[2] = String.valueOf(path);
                csvData.add(csv);
                writer.writeAll(csvData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   public static void writeFindCityIntoDatabase(String firstCity, String secondCity, String file){
       try {
           List<String[]> allDataFromDb = readAllDataFromDatabase(file);
           try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
               List<String[]> csvData = new ArrayList<>(allDataFromDb);
               String[] csv = new String[2];
               csv[0] = String.valueOf(firstCity);
               csv[1] = String.valueOf(secondCity);
               csvData.add(csv);
               writer.writeAll(csvData);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

    public static void initializeTable(String header, String file) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            String[] headerSplit = header.split(",");
            writer.writeAll(Collections.singleton(headerSplit));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
