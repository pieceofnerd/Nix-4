package ua.com.alevel.util;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ua.com.alevel.entity.Fiction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserCSV {

    public static List<String[]> prepareCVSData(String header) {
        List<String[]> csvData = new ArrayList<>();
        String[] headerSplit = header.replace("\\s+", "\\s").split(",");
        csvData.add(headerSplit);

        return csvData;
    }

    public static void writeDataCSVBook(List<String[]> csvData, Fiction fiction, String file) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(file),'|',' ',' ',"\n")) {

            String[] csv = new String[3];
            csv[0] = fiction.getId();
            csv[1] = fiction.getTitle();
            csv[2] = fiction.getGenre();
            csvData.add(csv);

            writer.writeAll(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> readDataCSVBook(String file) {
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

}
