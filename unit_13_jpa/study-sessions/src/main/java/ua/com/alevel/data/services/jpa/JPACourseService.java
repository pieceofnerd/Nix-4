package ua.com.alevel.data.services.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.CourseRecord;
import ua.com.alevel.data.model.dto.save.CourseRecordSave;
import ua.com.alevel.data.model.entity.*;
import ua.com.alevel.data.services.CourseService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;


public class JPACourseService implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(JPATopicService.class);

    private final Supplier<EntityManager> em;

    public JPACourseService(Supplier<EntityManager> em) {
        this.em = em;
    }

    @Override
    public Optional<CourseRecord> getById(Long id) {
        EntityManager jpa = em.get();

        Course entity = jpa.find(Course.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public CourseRecord create(CourseRecordSave saveRecord) throws StudySessionDataLayerException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Course entity = new Course();
            setEntity(saveRecord, entity);
            jpa.persist(entity);
            transaction.commit();
            logger.info("Course was successfully created");
            return mapEntityToRecord(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Creation failed");
            throw new StudySessionDataLayerException(e);
        }
    }

    @Override
    public void update(Long id, CourseRecordSave saveRecord) throws StudySessionDataNotFoundException, StudySessionDataLayerException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Course entity = jpa.find(Course.class, id);
            if (Objects.isNull(entity)) {
                transaction.rollback();
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Course.class);
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
            Course entity = jpa.find(Course.class, id);
            if (Objects.isNull(entity)) {
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Course.class);
            }
            jpa.remove(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Deleting failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTopic(Long courseId, Long topicId) throws StudySessionDataNotFoundException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();

        Topic topic = jpa.find(Topic.class, topicId);
        Course course = jpa.find(Course.class, courseId);
        if (Objects.nonNull(topic)) {
            if (Objects.nonNull(course)) {
                course.setTopics(topic);
                transaction.commit();
            } else {
                transaction.rollback();
                logger.error("There is no course with id {}", courseId);
                throw new StudySessionDataNotFoundException(courseId, Course.class);
            }
        } else {
            transaction.rollback();
            logger.error("There is no topic with id {}", topicId);
            throw new StudySessionDataNotFoundException(topicId, Topic.class);
        }
    }

    @Override
    public void addGroup(Long courseId, Long groupId) throws StudySessionDataNotFoundException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();

        StudentGroup group = jpa.find(StudentGroup.class, groupId);
        Course course = jpa.find(Course.class, courseId);
        if (Objects.nonNull(group)) {
            if (Objects.nonNull(course)) {
                course.setGroups(group);
                transaction.commit();
            } else {
                transaction.rollback();
                logger.error("There is no course with id {}", courseId);
                throw new StudySessionDataNotFoundException(courseId, Course.class);
            }
        } else {
            transaction.rollback();
            logger.error("There is no group with id {}", groupId);
            throw new StudySessionDataNotFoundException(groupId, StudentGroup.class);
        }
    }

    @Override
    public CourseRecord findCourseByTopicId(Long id) {
        EntityManager jpa = em.get();

        Topic topic = jpa.find(Topic.class, id);

        if (Objects.nonNull(topic)) {
            return mapEntityToRecord(topic.getCourses());
        } else return null;
    }
    @Override
    public void addTeacher(Long courseId, Long teacherId) throws StudySessionDataNotFoundException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();

        Teacher teacher = jpa.find(Teacher.class, teacherId);
        Course course = jpa.find(Course.class, courseId);
        if (Objects.nonNull(teacher)) {
            if (Objects.nonNull(course)) {
                course.setTeachers(teacher);
                transaction.commit();
            } else {
                transaction.rollback();
                logger.error("There is no course with id {}", courseId);
                throw new StudySessionDataNotFoundException(courseId, Course.class);
            }
        } else {
            transaction.rollback();
            logger.error("There is no Teacher with id {}", teacherId);
            throw new StudySessionDataNotFoundException(teacherId, StudentGroup.class);
        }
    }


    private CourseRecord mapEntityToRecord(Course entity) {
        return new CourseRecord(
                entity.getId(),
                entity.getCourseName(),
                entity.getHoursNumber()
        );
    }
    public Course mapRecordToEntity(CourseRecord record) {
        return new Course(
                record.id(),
                record.courseName(),
                record.hoursNumber()
        );
    }


    private void setEntity(CourseRecordSave saveRecord, Course entity) {
        entity.setCourseName(saveRecord.courseName());
        entity.setHoursNumber(saveRecord.hoursNumber());
    }
}
