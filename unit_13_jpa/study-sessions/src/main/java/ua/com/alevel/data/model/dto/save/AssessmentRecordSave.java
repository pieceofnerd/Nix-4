package ua.com.alevel.data.model.dto.save;

import ua.com.alevel.data.model.dto.LessonRecord;
import ua.com.alevel.data.model.dto.StudentRecord;

public record AssessmentRecordSave(
        int assessment,
        String comment,
        StudentRecord student,
        LessonRecord lesson) {
}
