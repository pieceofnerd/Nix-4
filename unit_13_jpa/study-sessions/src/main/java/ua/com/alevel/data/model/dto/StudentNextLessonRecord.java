package ua.com.alevel.data.model.dto;

import java.time.Instant;

public record StudentNextLessonRecord(
        Instant lessonStart,
        Instant lessonEnd,
        TeacherRecord teacherRecord,
        TopicRecord topicRecord
) {
}
