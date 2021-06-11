package ua.com.alevel.data.services.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.StudentNextLessonRecord;
import ua.com.alevel.data.model.dto.StudentRecord;
import ua.com.alevel.data.model.dto.save.StudentRecordSave;
import ua.com.alevel.data.model.entity.Student;
import ua.com.alevel.data.services.StudentService;
import ua.com.alevel.data.services.TopicService;

import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class JPAStudentService implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(JPATopicService.class);

    private final Supplier<EntityManager> em;

    public JPAStudentService(Supplier<EntityManager> em) {
        this.em = em;
    }

    @Override
    public Optional<StudentRecord> getById(Long id) {
        EntityManager jpa = em.get();

        Student entity = jpa.find(Student.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public StudentRecord create(StudentRecordSave saveRecord) throws StudySessionDataLayerException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Student entity = new Student();
            setEntity(saveRecord, entity);
            jpa.persist(entity);
            transaction.commit();
            logger.info("Student was successfully created");
            return mapEntityToRecord(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Creation failed");
            throw new StudySessionDataLayerException(e);
        }
    }

    @Override
    public void update(Long id, StudentRecordSave saveRecord) throws StudySessionDataNotFoundException, StudySessionDataLayerException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Student entity = jpa.find(Student.class, id);
            if (Objects.isNull(entity)) {
                transaction.rollback();
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Student.class);
            }
            setEntity(saveRecord, entity);
            transaction.commit();
            logger.info("Student was successfully updated");
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
            Student entity = jpa.find(Student.class, id);
            if (Objects.isNull(entity)) {
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Student.class);
            }
            jpa.remove(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Deleting failed");
            throw new RuntimeException(e);
        }
    }

    @Override
    public StudentNextLessonRecord findNextLessonByStudentId(Long id) throws StudySessionDataLayerException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        String nextLessonQuery = "Select id " +
                "From Lesson  " +
                "Where lessonStart>current_timestamp() and  lesson_Start<( " +
                "Select min(lessonStart) From Lesson)";

        String informationQuery = "Select lesson_start, lesson_end, topic.id as topic, course.id as course " +
                "From (lesson inner join topic_lesson on lesson.id=topic_lesson.lesson_id) " +
                " inner join topic on topic.id=topic_lesson.topic_id " +
                "        inner join course on topic.course_id=course.id " +
                "        inner join student_group_course on student_group_course.course_id=course.id " +
                "        inner join student_group on student_group.id=student_group_course.group_id " +
                "        inner join student on student.group_id= student_group.id " +
                "        Where student.id=:id and lesson_id=:lessonId";

        try {

            Long lessonId = jpa.createQuery(nextLessonQuery, Long.class).getResultStream()
                    .findFirst().orElse(null);
            if (Objects.isNull(lessonId)) {
                return null;
            }
            TypedQuery<StudentNextLessonRecord> typedQuery = jpa.createQuery(informationQuery, StudentNextLessonRecord.class);
            typedQuery.setParameter("id", id);
            typedQuery.setParameter("lessonId", lessonId);
            transaction.commit();
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            transaction.rollback();
            logger.error("Problem occurs during finding appropriate lesson");
            throw new StudySessionDataLayerException(e);
        }
    }

    private StudentRecord mapEntityToRecord(Student entity) {
        return new StudentRecord(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge(),
                entity.getEmail(),
                entity.getGroup()
        );
    }

    public Student mapRecordToEntity(StudentRecord record) {
        return new Student(
                record.id(),
                record.firstName(),
                record.lastName(),
                record.age(),
                record.email(),
                record.group()
        );
    }

    private void setEntity(StudentRecordSave saveRecord, Student entity) {
        entity.setGroup(new JPAGroupService(em).mapRecordToEntity(saveRecord.group()));
        entity.setEmail(saveRecord.email());
        entity.setFirstName(saveRecord.firstName());
        entity.setLastName(saveRecord.lastName());
        entity.setAge(saveRecord.age());
    }

}
