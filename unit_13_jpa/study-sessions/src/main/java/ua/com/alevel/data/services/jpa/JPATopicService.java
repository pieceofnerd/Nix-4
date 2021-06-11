package ua.com.alevel.data.services.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.TopicRecord;

import ua.com.alevel.data.model.dto.save.TopicSaveRecord;
import ua.com.alevel.data.model.entity.Lesson;
import ua.com.alevel.data.model.entity.Topic;
import ua.com.alevel.data.services.TopicService;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class JPATopicService implements TopicService {

    private static final Logger logger = LoggerFactory.getLogger(JPATopicService.class);

    private final Supplier<EntityManager> em;

    public JPATopicService(Supplier<EntityManager> em) {
        this.em = em;
    }

    @Override
    public TopicRecord create(TopicSaveRecord saveRecord) throws StudySessionDataLayerException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Topic entity = new Topic();
            setEntity(saveRecord, entity);
            jpa.persist(entity);
            transaction.commit();
            logger.info("Topic was successfully created");
            return mapEntityToRecord(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Creation failed");
            throw new StudySessionDataLayerException(e);
        }
    }

    @Override
    public void update(Long id, TopicSaveRecord saveRecord) throws StudySessionDataNotFoundException, StudySessionDataLayerException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Topic entity = jpa.find(Topic.class, id);
            if (Objects.isNull(entity)) {
                transaction.rollback();
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Topic.class);
            }
            setEntity(saveRecord, entity);
            transaction.commit();
            logger.info("Topic was successfully updated");
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Updating failed");
            throw new StudySessionDataLayerException(e);
        }
    }

    @Override
    public void delete(Long id) throws StudySessionDataNotFoundException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Topic entity = jpa.find(Topic.class, id);
            if (Objects.isNull(entity)) {
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Topic.class);
            }
            jpa.remove(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Deleting failed");
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<TopicRecord> getById(Long id) {
        EntityManager jpa = em.get();

        Topic entity = jpa.find(Topic.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public Set<TopicRecord> getTopicsByLessonId(Long id) {
        EntityManager jpa = em.get();

        Lesson lesson = jpa.find(Lesson.class, id);

        if (Objects.nonNull(lesson)) {
            return lesson.getTopics().stream()
                    .map(this::mapEntityToRecord)
                    .collect(Collectors.toSet());
        } else return null;
    }

    @Override
    public void addLessonById(Long topicId, Long lessonId) throws StudySessionDataNotFoundException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();

        Topic topic = jpa.find(Topic.class, topicId);
        Lesson lesson = jpa.find(Lesson.class, lessonId);
        if (Objects.nonNull(topic)) {
            if (Objects.nonNull(lesson)) {
                topic.setLessons(lesson);
                transaction.commit();
            } else {
                transaction.rollback();
                logger.error("There is no lesson with id {}", lessonId);
                throw new StudySessionDataNotFoundException(topicId, Lesson.class);
            }
        } else {
            transaction.rollback();
            logger.error("There is no topic with id {}", topicId);
            throw new StudySessionDataNotFoundException(topicId, Topic.class);
        }

    }

    private void setEntity(TopicSaveRecord saveRecord, Topic entity) {
        entity.setTitle(saveRecord.title());
        entity.setDescription(saveRecord.description());
        entity.setCourse(new JPACourseService(em).mapRecordToEntity(saveRecord.course()));
    }

    private TopicRecord mapEntityToRecord(Topic entity) {
        return new TopicRecord(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription()
        );
    }





}