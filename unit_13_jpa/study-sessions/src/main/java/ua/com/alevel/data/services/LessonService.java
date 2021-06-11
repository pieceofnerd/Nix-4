package ua.com.alevel.data.services;

import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.LessonRecord;
import ua.com.alevel.data.model.dto.save.LessonRecordSave;


public interface LessonService extends BaseService<LessonRecord, LessonRecordSave> {

    void addTeacherById(Long lessonId, Long teacherId) throws StudySessionDataNotFoundException;

}
