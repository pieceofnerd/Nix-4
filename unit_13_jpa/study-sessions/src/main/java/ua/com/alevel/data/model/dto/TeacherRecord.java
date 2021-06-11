package ua.com.alevel.data.model.dto;

public record TeacherRecord(
        Long id,
        String firstName,
        String lastName,
        int age,
        String email,
        String workExperience
) {
}
