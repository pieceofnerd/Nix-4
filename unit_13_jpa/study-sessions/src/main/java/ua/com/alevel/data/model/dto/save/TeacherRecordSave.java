package ua.com.alevel.data.model.dto.save;

public record TeacherRecordSave(
        String firstName,
        String lastName,
        int age,
        String email,
        String workExperience
) {
}
