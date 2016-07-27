package com.softdesign.devintensive.model.storage.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by skwmium on 20.07.16.
 */
@Entity(active = true, nameInDb = "USER_ATTRS")
public class DbUserAttribute {
    @Id
    private String userRemoteId;

    private boolean deleted;
    private long order;
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1515445257)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDbUserAttributeDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 34914361)
    private transient DbUserAttributeDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public long getOrder() {
        return this.order;
    }
    public void setOrder(long order) {
        this.order = order;
    }
    public boolean getDeleted() {
        return this.deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public String getUserRemoteId() {
        return this.userRemoteId;
    }
    public void setUserRemoteId(String userRemoteId) {
        this.userRemoteId = userRemoteId;
    }
    @Generated(hash = 1357126011)
    public DbUserAttribute(String userRemoteId, boolean deleted, long order) {
        this.userRemoteId = userRemoteId;
        this.deleted = deleted;
        this.order = order;
    }
    @Generated(hash = 97202692)
    public DbUserAttribute() {
    }
}
