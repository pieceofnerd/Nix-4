package ua.com.alevel.entity;

import java.util.List;

public class StructureCSV {
    private String[] titles;
    private List<String[]> data;

    public StructureCSV(String[] titles, List<String[]> data) {
        this.titles = titles;
        this.data = data;
    }

    public String getElement(int row, int column) {
        if (row >= 0 && row < data.size() && column >= 0 && column < titles.length)
            return data.get(row)[column];
        else return null;
    }

    public String getElement(int row, String column) {
        int columnIndex = indexOfTitle(column);
        if (columnIndex >= 0 && row >= 0 && row < data.size()) {
            return data.get(row)[columnIndex];
        } else return null;
    }

    public List<String[]> getData() {
        return data;
    }

    private int indexOfTitle(String column) {
        for (int i = 0; i < titles.length; i++) {
            if (column.equals(titles[i])) {
                return i;
            }
        }
        return -1;
    }
}
