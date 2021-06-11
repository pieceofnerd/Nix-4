package ua.com.alevel.data.services.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;
import ua.com.alevel.data.model.dto.GroupRecord;

import ua.com.alevel.data.model.dto.save.GroupRecordSave;


import ua.com.alevel.data.model.entity.StudentGroup;
import ua.com.alevel.data.services.GroupService;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class JPAGroupService implements GroupService {
    private static final Logger logger = LoggerFactory.getLogger(JPATopicService.class);

    private final Supplier<EntityManager> em;

    public JPAGroupService(Supplier<EntityManager> em) {
        this.em = em;
    }


    @Override
    public Optional<GroupRecord> getById(Long id) {
        EntityManager jpa = em.get();

        StudentGroup entity = jpa.find(StudentGroup.class, id);

        return Optional.ofNullable(entity)
                .map(this::mapEntityToRecord);
    }

    @Override
    public GroupRecord create(GroupRecordSave saveRecord) throws StudySessionDataLayerException {
        EntityManager jpa = em.get();

        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            StudentGroup entity = new StudentGroup();
            setEntity(saveRecord, entity);
            jpa.persist(entity);
            transaction.commit();
            logger.info("Group was successfully created");
            return mapEntityToRecord(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Creation failed");
            throw new StudySessionDataLayerException(e);
        }
    }

    @Override
    public void update(Long id, GroupRecordSave saveRecord) throws StudySessionDataNotFoundException, StudySessionDataLayerException {
        EntityManager jpa = em.get();
        EntityTransaction transaction = jpa.getTransaction();
        transaction.begin();
        try {
            StudentGroup entity = jpa.find(StudentGroup.class, id);
            if (Objects.isNull(entity)) {
                transaction.rollback();
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, StudentGroup.class);
            }
            setEntity(saveRecord, entity);
            transaction.commit();
            logger.info("Group was successfully updated");
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
            StudentGroup entity = jpa.find(StudentGroup.class, id);
            if (Objects.isNull(entity)) {
                logger.error("There is no object with id {}", id);
                throw new StudySessionDataNotFoundException(id, StudentGroup.class);
            }
            jpa.remove(entity);
        } catch (RuntimeException e) {
            transaction.rollback();
            logger.error("Deleting failed");
            throw new RuntimeException(e);
        }
    }

    private GroupRecord mapEntityToRecord(StudentGroup entity) {
        return new GroupRecord(
                entity.getId(),
                entity.getTitle()
        );
    }

    @Override
    public StudentGroup mapRecordToEntity(GroupRecord group) {
        return new StudentGroup(group.id(), group.title());
    }

    private void setEntity(GroupRecordSave saveRecord, StudentGroup entity) {
        entity.setTitle(saveRecord.title());
    }

}
