package ua.com.alevel.impl.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.BaseDao;
import ua.com.alevel.dao.SolutionDao;
import ua.com.alevel.impl.ApplicationMainImpl;
import ua.com.alevel.model.Solution;
import ua.com.alevel.service.models.SolutionService;

import java.util.List;

public class SolutionServiceImpl implements SolutionService {
    private static final BaseDao<Solution> solutionDao = new SolutionDao();
    private static final Logger logger = LoggerFactory.getLogger(SolutionServiceImpl.class);

    @Override
    public void create(Solution entity) {
        if (!solutionDao.isExist(entity)) {
            solutionDao.create(entity);
        }
        else{
            logger.warn("Solution of {} problem has already created",entity.getProblemId() );
        }
    }

    @Override
    public List<Solution> findAll() {
        return solutionDao.findAll();
    }

    @Override
    public boolean isExisted(Solution entity) {
        return false;
    }
}
