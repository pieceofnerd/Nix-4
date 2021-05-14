package ua.com.alevel.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ua.com.alevel.entity.Author;
import ua.com.alevel.entity.Fiction;

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

    private static String[] writeBookIntoDatabase(Object fiction) {
        try {
            Fiction book = (Fiction) fiction;
            String[] csv = new String[4];
            csv[0] = book.getId();
            csv[1] = book.getTitle();
            csv[2] = book.getGenre();
            csv[3] = String.valueOf(book.isVisible());
            return csv;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static <T> void writeIntoDatabase(T object, String file) {
        List<String[]> allDataFromDb = readAllDataFromDatabase(file);
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            List<String[]> csvData = new ArrayList<>(allDataFromDb);
            if (object.getClass().isAssignableFrom(Fiction.class))
                csvData.add(writeBookIntoDatabase(object));
            else if (object.getClass().isAssignableFrom(Author.class))
                csvData.add(writeAuthorIntoDatabase(object));
            writer.writeAll(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> String[] writeAuthorIntoDatabase(T t) {
        try {
            Author author = (Author) t;
            String[] csv = new String[4];
            csv[0] = author.getId();
            csv[1] = author.getFirstName();
            csv[2] = author.getLastName();
            csv[3] = String.valueOf(author.isVisible());
            return csv;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static void writeBookAuthorIntoDatabase(String authorId, String bookId, String file) {
        try {
            List<String[]> allDataFromDb = readAllDataFromDatabase(file);
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                List<String[]> csvData = new ArrayList<>(allDataFromDb);
                String[] csv = new String[2];
                csv[0] = authorId;
                csv[1] = bookId;
                csvData.add(csv);
                writer.writeAll(csvData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void rewriteBookTable(List<String[]> books, String file) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            writer.writeAll(books);
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
