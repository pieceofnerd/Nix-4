package ua.com.alevel.data.services.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.AssessmentRecord;
import ua.com.alevel.data.model.dto.save.AssessmentRecordSave;
import ua.com.alevel.data.model.entity.Assessment;
import ua.com.alevel.data.services.AssessmentService;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class JPAAssessmentService implements AssessmentService {
    private static final Logger logger = LoggerFactory.getLogger(JPATopicService.class);

    private final Supplier<EntityManager> em;

    public JPAAssessmentService(Supplier<EntityManager> em) {
        this.em = em;
    }

    @Override
    public Optional<AssessmentRecord> getById(Long id) {
        EntityManager jpa = em.get();

        Assessment entity = jpa.find(Assessment.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public AssessmentRecord create(AssessmentRecordSave saveRecord) throws StudySessionDataLayerException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Assessment entity = new Assessment();
            setEntity(saveRecord, entity);
            jpa.persist(entity);
            transaction.commit();
            logger.info("Assessment was successfully created");
            return mapEntityToRecord(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Creation failed");
            throw new StudySessionDataLayerException(e);
        }
    }

    @Override
    public void update(Long id, AssessmentRecordSave saveRecord) throws StudySessionDataNotFoundException, StudySessionDataLayerException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            Assessment entity = jpa.find(Assessment.class, id);
            if (Objects.isNull(entity)) {
                transaction.rollback();
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Assessment.class);
            }
            setEntity(saveRecord, entity);
            transaction.commit();
            logger.info("Assessment was successfully updated");
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
            Assessment entity = jpa.find(Assessment.class, id);
            if (Objects.isNull(entity)) {
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, Assessment.class);
            }
            jpa.remove(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Deleting failed");
            throw new RuntimeException(e);
        }
    }

    private AssessmentRecord mapEntityToRecord(Assessment entity) {
        return new AssessmentRecord(
                entity.getId(),
                entity.getAssessment(),
                entity.getComment(),
                entity.getStudent(),
                entity.getLesson()
        );
    }

    private void setEntity(AssessmentRecordSave saveRecord, Assessment entity) {
        entity.setAssessment(saveRecord.assessment());
        entity.setComment(saveRecord.comment());
        entity.setStudent(new JPAStudentService(em).mapRecordToEntity(saveRecord.student()));
        entity.setLesson(new JPALessonService(em).mapRecordToEntity(saveRecord.lesson()));
    }
}
