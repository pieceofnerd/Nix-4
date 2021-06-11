package ua.com.alevel.data.model.dto.save;

import ua.com.alevel.data.model.dto.GroupRecord;

public record StudentRecordSave (
        String firstName,
        String lastName,
        int age,
        String email,
        GroupRecord group
){
}
