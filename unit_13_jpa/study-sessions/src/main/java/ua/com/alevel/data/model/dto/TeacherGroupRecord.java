package ua.com.alevel.data.model.dto;

public record TeacherGroupRecord(
        int groupId,
        String groupTitle,
        CourseRecord course
) {
}
