package ua.com.alevel.impl.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.dao.ProblemDao;
import ua.com.alevel.impl.ApplicationMainImpl;
import ua.com.alevel.model.Problem;
import ua.com.alevel.service.models.ProblemService;

import java.util.List;

public class ProblemServiceImpl implements ProblemService {
    private static final BaseDao<Problem> problemDao = new ProblemDao();
    private static final Logger logger = LoggerFactory.getLogger(ProblemServiceImpl.class);
    @Override
    public void create(Problem entity) {
        if (!problemDao.isExist(entity))
            problemDao.create(entity);
        else{
            logger.warn("Problem from {} to {} has already been created",entity.getFromId(), entity.getToId() );
        }
    }

    @Override
    public List<Problem> findAll() {
        return problemDao.findAll();
    }

    @Override
    public boolean isExisted(Problem entity) {
        return problemDao.isExist(entity);
    }
}
