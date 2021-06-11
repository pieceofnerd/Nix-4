package ua.com.alevel.data.model.dto;


import ua.com.alevel.data.model.entity.StudentGroup;

public record StudentRecord(
        Long id,
        String firstName,
        String lastName,
        int age,
        String email,
        StudentGroup group
) {
}
