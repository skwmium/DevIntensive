package com.softdesign.devintensive.model.storage.entities;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.softdesign.devintensive.model.storage.entities.DbRepository;
import com.softdesign.devintensive.model.storage.entities.DbUser;
import com.softdesign.devintensive.model.storage.entities.DbUserAttribute;

import com.softdesign.devintensive.model.storage.entities.DbRepositoryDao;
import com.softdesign.devintensive.model.storage.entities.DbUserDao;
import com.softdesign.devintensive.model.storage.entities.DbUserAttributeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dbRepositoryDaoConfig;
    private final DaoConfig dbUserDaoConfig;
    private final DaoConfig dbUserAttributeDaoConfig;

    private final DbRepositoryDao dbRepositoryDao;
    private final DbUserDao dbUserDao;
    private final DbUserAttributeDao dbUserAttributeDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dbRepositoryDaoConfig = daoConfigMap.get(DbRepositoryDao.class).clone();
        dbRepositoryDaoConfig.initIdentityScope(type);

        dbUserDaoConfig = daoConfigMap.get(DbUserDao.class).clone();
        dbUserDaoConfig.initIdentityScope(type);

        dbUserAttributeDaoConfig = daoConfigMap.get(DbUserAttributeDao.class).clone();
        dbUserAttributeDaoConfig.initIdentityScope(type);

        dbRepositoryDao = new DbRepositoryDao(dbRepositoryDaoConfig, this);
        dbUserDao = new DbUserDao(dbUserDaoConfig, this);
        dbUserAttributeDao = new DbUserAttributeDao(dbUserAttributeDaoConfig, this);

        registerDao(DbRepository.class, dbRepositoryDao);
        registerDao(DbUser.class, dbUserDao);
        registerDao(DbUserAttribute.class, dbUserAttributeDao);
    }
    
    public void clear() {
        dbRepositoryDaoConfig.getIdentityScope().clear();
        dbUserDaoConfig.getIdentityScope().clear();
        dbUserAttributeDaoConfig.getIdentityScope().clear();
    }

    public DbRepositoryDao getDbRepositoryDao() {
        return dbRepositoryDao;
    }

    public DbUserDao getDbUserDao() {
        return dbUserDao;
    }

    public DbUserAttributeDao getDbUserAttributeDao() {
        return dbUserAttributeDao;
    }

}