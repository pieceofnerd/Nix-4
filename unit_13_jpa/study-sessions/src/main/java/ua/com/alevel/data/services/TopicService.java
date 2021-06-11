package ua.com.alevel.data.services;

import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.TopicRecord;
import ua.com.alevel.data.model.dto.save.TopicSaveRecord;

import java.util.Set;

public interface TopicService extends BaseService<TopicRecord, TopicSaveRecord> {

    Set<TopicRecord> getTopicsByLessonId(Long id);

    void addLessonById(Long topicId, Long lessonId)
            throws StudySessionDataNotFoundException;


}
