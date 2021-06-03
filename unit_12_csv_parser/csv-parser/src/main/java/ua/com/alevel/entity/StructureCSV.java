package ua.com.alevel.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StructureCSV {
    private Map<String, Integer> titles;
    private List<String[]> data;


    public StructureCSV(String[] titles, List<String[]> data) {
        this.titles = new HashMap<>();
        this.data = data;

        for (int i = 0; i < titles.length; i++) {
            this.titles.put(titles[i], i);
        }
    }

    public String getElement(int row, int column) {
        if (row >= 0 && row < data.size() && column >= 0 && column < titles.size())
            return data.get(row)[column];
        else return null;
    }

    public String getElement(int row, String column) {
        int columnIndex = titles.get(column);
        if (columnIndex >= 0 && row >= 0 && row < data.size()) {
            return data.get(row)[columnIndex];
        } else return null;
    }

    public List<String[]> getData() {
        return data;
    }

}
