package ua.com.alevel.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseEntity {
    private String id;
    private LocalDateTime createDate;

    public BaseEntity() {
        this.createDate = LocalDateTime.now();
    }
}
