package ua.com.alevel.data.model.dto.save;

import java.time.Instant;

public record LessonRecordSave(
        Instant lessonStart,
        Instant lessonEnd
) {

}
