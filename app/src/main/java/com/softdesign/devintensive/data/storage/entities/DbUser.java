package com.softdesign.devintensive.data.storage.entities;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.List;

/**
 * Created by skwmium on 20.07.16.
 */
@Entity(active = true, nameInDb = "USERS")
public class DbUser {
    @Id
    private String remoteId;

    @NotNull
    private String name;

    @ToMany(joinProperties = {
            @JoinProperty(name = "remoteId", referencedName = "userRemoteId")
    })
    private List<DbRepository> mRepositories;

    @NotNull
    @ToOne(joinProperty = "remoteId")
    private DbUserAttribute attribute;

    private String mobilePhoneNumber;
    private String email;
    private String vkProfile;
    private String about;
    private String avatarUrl;
    private String photoUrl;
    private int rating;
    private int linesCount;
    private int projectCount;


    // ---------- GENERATED ----------

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

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 1361215124)
    public synchronized void resetMRepositories() {
        mRepositories = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 253978020)
    public List<DbRepository> getMRepositories() {
        if (mRepositories == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DbRepositoryDao targetDao = daoSession.getDbRepositoryDao();
            List<DbRepository> mRepositoriesNew = targetDao._queryDbUser_MRepositories(remoteId);
            synchronized (this) {
                if (mRepositories == null) {
                    mRepositories = mRepositoriesNew;
                }
            }
        }
        return mRepositories;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 695530781)
    public void setAttribute(DbUserAttribute attribute) {
        synchronized (this) {
            this.attribute = attribute;
            remoteId = attribute == null ? null : attribute.getUserRemoteId();
            attribute__resolvedKey = remoteId;
        }
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 1901454366)
    public DbUserAttribute getAttribute() {
        String __key = this.remoteId;
        if (attribute__resolvedKey == null || attribute__resolvedKey != __key) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DbUserAttributeDao targetDao = daoSession.getDbUserAttributeDao();
            DbUserAttribute attributeNew = targetDao.load(__key);
            synchronized (this) {
                attribute = attributeNew;
                attribute__resolvedKey = __key;
            }
        }
        return attribute;
    }

    @Generated(hash = 175820479)
    private transient String attribute__resolvedKey;

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1537992319)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDbUserDao() : null;
    }

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 927650436)
    private transient DbUserDao myDao;

    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public int getProjectCount() {
        return this.projectCount;
    }

    public void setProjectCount(int projectCount) {
        this.projectCount = projectCount;
    }

    public int getLinesCount() {
        return this.linesCount;
    }

    public void setLinesCount(int linesCount) {
        this.linesCount = linesCount;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAbout() {
        return this.about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getVkProfile() {
        return this.vkProfile;
    }

    public void setVkProfile(String vkProfile) {
        this.vkProfile = vkProfile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhoneNumber() {
        return this.mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemoteId() {
        return this.remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    @Generated(hash = 175127920)
    public DbUser(String remoteId, @NotNull String name, String mobilePhoneNumber,
                  String email, String vkProfile, String about, String avatarUrl,
                  String photoUrl, int rating, int linesCount, int projectCount) {
        this.remoteId = remoteId;
        this.name = name;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.email = email;
        this.vkProfile = vkProfile;
        this.about = about;
        this.avatarUrl = avatarUrl;
        this.photoUrl = photoUrl;
        this.rating = rating;
        this.linesCount = linesCount;
        this.projectCount = projectCount;
    }

    @Generated(hash = 762027100)
    public DbUser() {
    }
}
