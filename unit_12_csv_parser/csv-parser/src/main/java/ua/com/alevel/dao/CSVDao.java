package ua.com.alevel.dao;

import ua.com.alevel.entity.StructureCSV;
import ua.com.alevel.util.CSVUtil;
import ua.com.alevel.util.FileUtil;

import java.util.List;

public class CSVDao implements BaseCSVDao {
    @Override
    public StructureCSV readCSV(String file) {
        List<String> csvData = FileUtil.read(file);
        List<String[]> csvSplitData = CSVUtil.parse(csvData);

        return new StructureCSV(csvSplitData.get(0), csvSplitData.subList(1, csvSplitData.size()));
    }
}
