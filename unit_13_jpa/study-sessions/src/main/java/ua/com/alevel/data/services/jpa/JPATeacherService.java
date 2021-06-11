package ua.com.alevel.data.services.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.CourseRecord;
import ua.com.alevel.data.model.dto.TeacherGroupRecord;
import ua.com.alevel.data.model.dto.TeacherRecord;
import ua.com.alevel.data.model.dto.TopicRecord;
import ua.com.alevel.data.model.dto.save.TeacherRecordSave;
import ua.com.alevel.data.model.entity.Teacher;
import ua.com.alevel.data.services.CourseService;
import ua.com.alevel.data.services.GroupService;
import ua.com.alevel.data.services.TeacherService;
import ua.com.alevel.data.services.TopicService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;



public class JPATeacherService implements TeacherService {

    private static final Logger logger = LoggerFactory.getLogger(JPATopicService.class);

    private final Supplier<EntityManager> em;

    public JPATeacherService(Supplier<EntityManager> em) {
        this.em = em;
    }

    @Override
    public TeacherGroupRecord defineTheMostSuccessGroupByTeacherId(Long id) throws StudySessionDataLayerException {
        GroupService groupService = new JPAGroupService(em);
        TopicService topicService = new JPATopicService(em);
        CourseService courseService = new JPACourseService(em);

        EntityManager jpa = em.get();

        EntityTransaction transaction= jpa.getTransaction();
        transaction.begin();

        String finalTestLessonIdQuery = "Select lesson.id " +
                " From (lesson inner join topic_lesson on lesson.id=topic_lesson.lesson_id) " +
                " inner join topic on topic_lesson.topic_id=topic.id " +
                " Where topic.title='Final test' and lesson.teacher_id=:id";


        String marksByGroupQuery = "Select student_group.id, assessment.assessment " +
                "From (student_group inner join student on student_group.id=student.group_id) " +
                "inner join assessment on assessment.student_id= student.id " +
                "Where assessment.lesson_id=:lessonID " +
                "Order by student_group.id, assessment.assessment";


        Query query = jpa.createNativeQuery(finalTestLessonIdQuery);
        query.setParameter("id", id);

        try {
            Long finalTestLessonId = Long.parseLong(query.getSingleResult().toString());
            query = jpa.createNativeQuery(marksByGroupQuery);
            query.setParameter("lessonID", finalTestLessonId);
            List<Object[]> resultSet = query.getResultList();

            TopicRecord topic = topicService.getTopicsByLessonId(finalTestLessonId).stream()
                    .findAny()
                    .orElse(null);

            try {
                CourseRecord course = courseService.findCourseByTopicId(Objects.requireNonNull(topic).id());
                Integer currentGroupId = calculateMedian(resultSet);
                transaction.commit();
                return new TeacherGroupRecord(
                        currentGroupId,
                        groupService.getById(Long.valueOf(currentGroupId)).get().title(),
                        course
                );
            } catch (NullPointerException e) {
                transaction.rollback();
                logger.error("object cannot be null");
                throw new StudySessionDataLayerException(e);
            }
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Problem occurs during getting the best group by Teacher id {}",id);
            throw new StudySessionDataLayerException(e);
        }


    }

    private Integer calculateMedian(List<Object[]> resultSet) {
        Integer groupId = (Integer) resultSet.stream()
                .findFirst()
                .get()[0];

        Integer currentGroupId = 0;
        double biggestMedian = 0;
        double currentMedian;
        int index = 0;
        for (int i = 0; i < resultSet.size(); i++) {
            if (groupId != resultSet.get(i)[0]) {
                if ((i - 1) % 2 == 0) {
                    currentMedian = (int) resultSet.get((index + i) / 2)[1];
                } else {
                    currentMedian = ((int) resultSet.get((index + i) / 2)[1] + (int) resultSet.get((index - 1 + i) / 2)[1]) / 2.0;
                }
                if (currentMedian > biggestMedian) {
                    biggestMedian = currentMedian;
                    currentGroupId = groupId;
                }
                groupId = (Integer) resultSet.get(i - 1)[0];
                index = i;
            }
        }
        return currentGroupId;
    }

    @Override
    public Optional<TeacherRecord> getById(Long id) {
        EntityManager jpa = em.get();

        Teacher entity = jpa.find(Teacher.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public TeacherRecord create(TeacherRecordSave saveRecord) throws StudySessionDataLayerException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Teacher entity = new Teacher();
            setEntity(saveRecord, entity);
            jpa.persist(entity);
            transaction.commit();
            logger.info("Teacher was successfully created");
            return mapEntityToRecord(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Creation failed");
            throw new StudySessionDataLayerException(e);
        }
    }

    @Override
    public void update(Long id, TeacherRecordSave saveRecord) throws StudySessionDataNotFoundException, StudySessionDataLayerException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Teacher entity = jpa.find(Teacher.class, id);
            if (Objects.isNull(entity)) {
                transaction.rollback();
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Teacher.class);
            }
            setEntity(saveRecord, entity);
            transaction.commit();
            logger.info("Teacher was successfully updated");
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
            Teacher entity = jpa.find(Teacher.class, id);
            if (Objects.isNull(entity)) {
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Teacher.class);
            }
            jpa.remove(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Deleting failed");
            throw new RuntimeException(e);
        }
    }

    private TeacherRecord mapEntityToRecord(Teacher entity) {
        return new TeacherRecord(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAge(),
                entity.getEmail(),
                entity.getWorkExperience()
        );
    }

    private void setEntity(TeacherRecordSave saveRecord, Teacher entity) {
        entity.setWorkExperience(saveRecord.workExperience());
        entity.setEmail(saveRecord.email());
        entity.setFirstName(saveRecord.firstName());
        entity.setLastName(saveRecord.lastName());
        entity.setAge(saveRecord.age());
    }

}
