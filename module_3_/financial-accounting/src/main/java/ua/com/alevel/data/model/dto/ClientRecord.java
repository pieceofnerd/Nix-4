package ua.com.alevel.data.model.dto;

public record ClientRecord(
        Long id,
        String firstName,
        String lastName,
        int age,
        String email
) {
}
