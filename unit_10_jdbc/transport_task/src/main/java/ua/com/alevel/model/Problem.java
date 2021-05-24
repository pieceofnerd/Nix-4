package ua.com.alevel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Problem {
    private int id;
    private int fromId;
    private int toId;

    public Problem(int fromId, int toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public Problem(int id, int fromId, int toId) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
    }
}
