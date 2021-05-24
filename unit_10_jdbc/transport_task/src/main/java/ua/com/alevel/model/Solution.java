package ua.com.alevel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Solution {
    private int problemId;
    private int cost;

    public Solution(int problemId, int cost) {
        this.problemId = problemId;
        this.cost = cost;
    }
}
