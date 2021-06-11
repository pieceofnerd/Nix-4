package ua.com.alevel.data.model.dto;

import java.time.Instant;

public record LessonRecord(
        Long id,
        Instant lessonStart,
        Instant lessonEnd
) {
}
