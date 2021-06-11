package ua.com.alevel.data.services.jpa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.LessonRecord;
import ua.com.alevel.data.model.dto.save.LessonRecordSave;
import ua.com.alevel.data.model.entity.Lesson;
import ua.com.alevel.data.model.entity.Teacher;
import ua.com.alevel.data.services.LessonService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class JPALessonService implements LessonService {

    private static final Logger logger = LoggerFactory.getLogger(JPATopicService.class);

    private final Supplier<EntityManager> em;

    public JPALessonService(Supplier<EntityManager> em) {
        this.em = em;
    }

    @Override
    public Optional<LessonRecord> getById(Long id) {
        EntityManager jpa = em.get();

        Lesson entity = jpa.find(Lesson.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public LessonRecord create(LessonRecordSave saveRecord) throws StudySessionDataLayerException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Lesson entity = new Lesson();
            setEntity(saveRecord, entity);
            jpa.persist(entity);
            transaction.commit();
            logger.info("Lesson was successfully created");
            return mapEntityToRecord(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Creation failed");
            throw new StudySessionDataLayerException(e);
        }
    }

    @Override
    public void update(Long id, LessonRecordSave saveRecord) throws StudySessionDataNotFoundException, StudySessionDataLayerException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Lesson entity = jpa.find(Lesson.class, id);
            if (Objects.isNull(entity)) {
                transaction.rollback();
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Lesson.class);
            }
            setEntity(saveRecord, entity);
            transaction.commit();
            logger.info("Lesson was successfully updated");
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
            Lesson entity = jpa.find(Lesson.class, id);
            if (Objects.isNull(entity)) {
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Lesson.class);
            }
            jpa.remove(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Deleting failed");
            throw new RuntimeException(e);
        }
    }

    private LessonRecord mapEntityToRecord(Lesson entity) {
        return new LessonRecord(
                entity.getId(),
                entity.getLessonStart(),
                entity.getLessonEnd()
        );
    }

    private void setEntity(LessonRecordSave saveRecord, Lesson entity) {
        entity.setLessonStart(saveRecord.lessonStart());
        entity.setLessonEnd(saveRecord.lessonEnd());
    }

    public Lesson mapRecordToEntity(LessonRecord record){
        return new Lesson(
                record.id(),
                record.lessonStart(),
                record.lessonEnd()
        );
    }

    @Override
    public void addTeacherById(Long lessonId, Long teacherId) throws StudySessionDataNotFoundException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();


        Lesson lesson = jpa.find(Lesson.class, lessonId);
        Teacher teacher = jpa.find(Teacher.class, teacherId);
        if (Objects.nonNull(teacher)) {
            if (Objects.nonNull(lesson)) {
                lesson.setTeacher(teacher);
                transaction.commit();
            } else {
                transaction.rollback();
                logger.error("There is no teacher with id {}", teacherId);
                throw new StudySessionDataNotFoundException(teacherId, Teacher.class);
            }
        } else {
            transaction.rollback();
            logger.error("There is no lesson with id {}", lessonId);
            throw new StudySessionDataNotFoundException(lessonId, Lesson.class);
        }
    }
}
