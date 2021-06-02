package ua.com.alevel;

import ua.com.alevel.entity.StructureCSV;
import ua.com.alevel.entity.Trader;
import ua.com.alevel.impl.CSVServiceImpl;
import ua.com.alevel.service.BaseCSVService;
import ua.com.alevel.util.CSVUtil;
import ua.com.alevel.util.FileUtil;

import java.util.List;
import java.util.Objects;

public class Application {

    private static final String csvFile = "testData.csv";

    public static void main(String[] args) {
        BaseCSVService<Trader> csvService = new CSVServiceImpl<>();
        StructureCSV structure = csvService.parse(csvFile);
        List<Trader> traders = csvService.mapAll(structure, Trader.class);

        traders.forEach(System.out::println);

        CSVUtil.outputElement(structure.getElement(1, 2));
        CSVUtil.outputElement(structure.getElement(0, "Salary"));
    }


}
