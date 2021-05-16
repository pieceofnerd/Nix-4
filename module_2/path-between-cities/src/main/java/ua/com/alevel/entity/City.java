package ua.com.alevel.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class City implements Comparable<City> {
    private int id;
    private String name;
    private Map<List<Integer>, Integer> neighbours;
    private double minDistance = Double.POSITIVE_INFINITY;
    private City previous;

    public City(int id, String name, Map<List<Integer>, Integer> neighbours) {
        this.id = id;
        this.name = name;
        this.neighbours = neighbours;
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int compareTo(City other) {
        return Double.compare(minDistance, other.minDistance);
    }
}
