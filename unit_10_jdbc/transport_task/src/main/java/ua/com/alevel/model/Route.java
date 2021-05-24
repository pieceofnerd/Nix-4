package ua.com.alevel.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Route {
    private int id;
    private int fromId;
    private int toId;
    private int cost;

    public Route(int fromId, int toId, int cost) {
        this.fromId = fromId;
        this.toId = toId;
        this.cost = cost;
    }

    public Route(int id, int fromId, int toId, int cost) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.cost = cost;
    }
}
