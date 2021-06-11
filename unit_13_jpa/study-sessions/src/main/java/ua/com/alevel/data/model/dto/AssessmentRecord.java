package ua.com.alevel.data.model.dto;

import ua.com.alevel.data.model.entity.Lesson;
import ua.com.alevel.data.model.entity.Student;

public record AssessmentRecord(
        Long id,
        int assessment,
        String comment,
        Student student,
        Lesson lesson
) {

}
