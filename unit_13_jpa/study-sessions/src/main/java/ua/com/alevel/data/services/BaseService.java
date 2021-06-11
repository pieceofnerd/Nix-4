package ua.com.alevel.data.services;

import ua.com.alevel.data.exception.StudySessionDataLayerException;
import ua.com.alevel.data.exception.StudySessionDataNotFoundException;

import java.util.Optional;

public interface BaseService<T, R> {

    Optional<T> getById(Long id);

    T create(R saveRecord)
            throws StudySessionDataLayerException;

    void update(Long id, R saveRecord)
            throws StudySessionDataNotFoundException, StudySessionDataLayerException;

    void delete(Long id)
            throws StudySessionDataNotFoundException, StudySessionDataLayerException;

}
