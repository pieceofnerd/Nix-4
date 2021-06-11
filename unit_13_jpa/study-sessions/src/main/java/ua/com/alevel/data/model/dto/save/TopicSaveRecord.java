package ua.com.alevel.data.model.dto.save;

import ua.com.alevel.data.model.dto.CourseRecord;

public record TopicSaveRecord(
        String title,
        String description,
        CourseRecord course
) {

}
