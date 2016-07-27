package com.softdesign.devintensive.model.storage.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by skwmium on 20.07.16.
 */
@Entity(active = true, nameInDb = "REPOSITORIES")
public class DbRepository {
    @Id
    private Long id;

    @NotNull
    @Unique
    private String remoteId;
    private String userRemoteId;
    private String gitUrl;
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
    @Generated(hash = 1533241022)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDbRepositoryDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 838962788)
    private transient DbRepositoryDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public String getGitUrl() {
        return this.gitUrl;
    }
    public void setGitUrl(String gitUrl) {
        this.gitUrl = gitUrl;
    }
    public String getUserRemoteId() {
        return this.userRemoteId;
    }
    public void setUserRemoteId(String userRemoteId) {
        this.userRemoteId = userRemoteId;
    }
    public String getRemoteId() {
        return this.remoteId;
    }
    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 2022985980)
    public DbRepository(Long id, @NotNull String remoteId, String userRemoteId,
            String gitUrl) {
        this.id = id;
        this.remoteId = remoteId;
        this.userRemoteId = userRemoteId;
        this.gitUrl = gitUrl;
    }
    @Generated(hash = 1704705119)
    public DbRepository() {
    }
}
