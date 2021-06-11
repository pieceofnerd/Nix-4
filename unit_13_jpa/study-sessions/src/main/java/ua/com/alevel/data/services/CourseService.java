package ua.com.alevel.data.services;

import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.CourseRecord;
import ua.com.alevel.data.model.dto.save.CourseRecordSave;

public interface CourseService extends BaseService<CourseRecord, CourseRecordSave>{

    void addTopic(Long courseId, Long topicId)
            throws StudySessionDataNotFoundException;

    void addGroup(Long courseId, Long groupId)
            throws StudySessionDataNotFoundException;

    void addTeacher(Long courseId, Long teacherId)
            throws StudySessionDataNotFoundException;

    CourseRecord findCourseByTopicId(Long id);

}
