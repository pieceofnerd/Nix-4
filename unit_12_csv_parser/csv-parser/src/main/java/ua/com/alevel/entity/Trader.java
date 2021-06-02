package ua.com.alevel.entity;

import ua.com.alevel.annotation.CellCSV;

public class Trader {
    @CellCSV("name")
    private String name;

    @CellCSV("salary")
    private int salary;

    @CellCSV("marriage")
    private boolean marriage;

    @CellCSV("position")
    private Position position;

    @CellCSV("phone")
    private String phone;

    @Override
    public String toString() {
        return "Trader{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", marriage=" + marriage +
                ", position=" + String.valueOf(position).toLowerCase() +
                ", phone='" + phone + '\'' +
                '}';
    }
}
