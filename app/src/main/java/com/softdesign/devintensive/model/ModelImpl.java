package com.softdesign.devintensive.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.softdesign.devintensive.common.App;
import com.softdesign.devintensive.model.dto.RepositoryDto;
import com.softdesign.devintensive.model.dto.UserDto;
import com.softdesign.devintensive.model.managers.LocalUserAuthorizer;
import com.softdesign.devintensive.model.mappers.MapperListUserApiDto;
import com.softdesign.devintensive.model.mappers.MapperUserApiDto;
import com.softdesign.devintensive.model.mappers.MapperUserDbDto;
import com.softdesign.devintensive.model.mappers.MapperUserDtoDb;
import com.softdesign.devintensive.model.network.api.SoftdesignApiClient;
import com.softdesign.devintensive.model.network.dto.AuthResult;
import com.softdesign.devintensive.model.network.dto.BaseResponse;
import com.softdesign.devintensive.model.network.dto.PublicInfo;
import com.softdesign.devintensive.model.network.dto.UploadImageResult;
import com.softdesign.devintensive.model.network.dto.User;
import com.softdesign.devintensive.model.network.params.ParamAuth;
import com.softdesign.devintensive.model.network.params.ParamEdit;
import com.softdesign.devintensive.model.network.params.ParamForgotPassword;
import com.softdesign.devintensive.model.network.params.UploadPhotoType;
import com.softdesign.devintensive.model.storage.LocalUser;
import com.softdesign.devintensive.model.storage.PreferenceCache;
import com.softdesign.devintensive.model.storage.entities.DaoSession;
import com.softdesign.devintensive.model.storage.entities.DbRepository;
import com.softdesign.devintensive.model.storage.entities.DbUser;
import com.softdesign.devintensive.model.storage.entities.DbUserAttribute;
import com.softdesign.devintensive.model.storage.entities.DbUserAttributeDao;
import com.softdesign.devintensive.model.storage.entities.DbUserDao;
import com.softdesign.devintensive.utils.Const;
import com.softdesign.devintensive.utils.ContentUriUtils;
import com.softdesign.devintensive.utils.L;
import com.softdesign.devintensive.utils.Utils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

import static com.softdesign.devintensive.model.network.params.UploadPhotoType.AVATAR;
import static com.softdesign.devintensive.model.network.params.UploadPhotoType.PHOTO;

/**
 * Created by skwmium on 01.07.16.
 */
@SuppressWarnings("Convert2MethodRef")
public class ModelImpl implements Model {
    private final Observable.Transformer mSchedulersTransformer;

    @Inject
    SoftdesignApiClient mSoftdesignApiInterface;

    @Inject
    DaoSession daoSession;

    @Inject
    @Named(Const.UI_THREAD)
    Scheduler mSchedulerUi;

    @Inject
    @Named(Const.IO_THREAD)
    Scheduler mSchedulerIo;

    @Inject
    MapperUserApiDto mapperUserApiDto;

    @Inject
    MapperListUserApiDto mapperListUserApiDto;

    @Inject
    MapperUserDbDto mapperUserDbDto;

    @Inject
    MapperUserDtoDb mapperUserDtoDb;

    @Inject
    LocalUserAuthorizer localUserAuthoriser;

    public ModelImpl() {
        App.getAppComponent().inject(this);
        mSchedulersTransformer = o -> ((Observable) o)
                .subscribeOn(mSchedulerIo)
                .observeOn(mSchedulerUi);
    }

    @Override
    public Observable<AuthResult> autUser(String email, String password) {
        ParamAuth authParam = new ParamAuth(email, password);
        return mSoftdesignApiInterface
                .userAuth(authParam)
                .map(authResultBaseResponse -> authResultBaseResponse.getBody())
                .doOnNext(localUserAuthoriser)//auth local user
                .doOnNext(authResult -> PreferenceCache.cacheUser(authResult.getUser()))
                .compose(applySchedulers());
    }

    @Override
    public Observable<BaseResponse> userRestorePassword(String email) {
        ParamForgotPassword paramForgotPassword = new ParamForgotPassword(email);
        return mSoftdesignApiInterface
                .userRestorePassword(paramForgotPassword)
                .compose(applySchedulers());
    }

    @Override
    public Observable<UserDto> userEditProfile(ParamEdit editParam) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (Map.Entry<String, String> entry : editParam.getParamsMap().entrySet()) {
            String value = entry.getValue();
            if (value == null) continue;
            requestBodyMap.put(entry.getKey(),
                    RequestBody.create(Const.MEDIA_TYPE_MULTIPART_FORM_DATA, value));
        }
        return mSoftdesignApiInterface
                .userEdit(requestBodyMap)
                .map(response -> response.getBody())
                .map(result -> result.getUser())
                .map(mapperUserApiDto)
                .compose(applySchedulers());
    }

    @Override
    public Observable<UploadImageResult> updateProfilePhoto(String path) {
        String userId = LocalUser.getInst().getUserId();
        PublicInfo publicInfo = getCachedUserPublicInfo();
        if (Utils.isNullOrEmpty(userId) || Utils.isNullOrEmpty(path) ||
                publicInfo == null || publicInfo.getPhotoUrl().equalsIgnoreCase(path)) {
            return Observable.empty();
        }

        MultipartBody.Part part = getMultipartFromPath(PHOTO, path);
        if (part == null) {
            return Observable.error(new NullPointerException());
        }
        return mSoftdesignApiInterface
                .userUploadPhoto(userId, part)
                .map(response -> response.getBody())
                .compose(applySchedulers());
    }

    @Override
    public Observable<UploadImageResult> updateProfileAvatar(String path) {
        String userId = LocalUser.getInst().getUserId();
        PublicInfo publicInfo = getCachedUserPublicInfo();
        if (Utils.isNullOrEmpty(userId) || Utils.isNullOrEmpty(path) ||
                publicInfo == null || publicInfo.getAvatarUrl().equalsIgnoreCase(path)) {
            return Observable.empty();
        }

        MultipartBody.Part part = getMultipartFromPath(AVATAR, path);
        if (part == null) {
            return Observable.error(new NullPointerException());
        }
        return mSoftdesignApiInterface
                .userUploadAvatar(userId, part)
                .map(response -> response.getBody())
                .compose(applySchedulers());
    }

    @Override
    public Observable<UserDto> userGetMe() {
        String userId = LocalUser.getInst().getUserId();
        if (Utils.isNullOrEmpty(userId)) {
            return Observable.empty();
        }
        return mSoftdesignApiInterface
                .userGet(userId)
                .map(userBaseResponse -> userBaseResponse.getBody())
                .doOnNext(user -> PreferenceCache.cacheUser(user))
                .onErrorReturn(throwable -> PreferenceCache.getUserFromCache())
                .map(mapperUserApiDto)
                .compose(applySchedulers());
    }

    @Override
    public Observable setUserRemoved(String objectId) {
        return Observable.create(subscriber -> {
            DbUserAttribute entity = daoSession.getDbUserAttributeDao().load(objectId);
            if (entity != null) {
                L.e("setUserRemoved ", entity.getUserRemoteId(), " ", Thread.currentThread());
                entity.setDeleted(true);
                entity.update();
            } else {
                L.e("setUserRemoved: entity == null!");
            }
            subscriber.onNext(null);
            subscriber.onCompleted();
        }).compose(applySchedulers());
    }

    @Override
    public Observable changeUserOrder(int from, int to) {
        return Observable.create(subscriber -> {
            DbUserAttribute entity1 = daoSession.getDbUserAttributeDao().queryBuilder()
                    .where(DbUserAttributeDao.Properties.Order.eq(from)).unique();
            DbUserAttribute entity2 = daoSession.getDbUserAttributeDao().queryBuilder()
                    .where(DbUserAttributeDao.Properties.Order.eq(to)).unique();
            if (entity1 != null && entity2 != null) {
                long tmpOrder = entity1.getOrder();
                entity1.setOrder(entity2.getOrder());
                entity2.setOrder(tmpOrder);
                entity1.update();
                entity2.update();
            }
            subscriber.onNext(null);
            subscriber.onCompleted();
        }).compose(applySchedulers());
    }

    @Override
    public Observable<List<UserDto>> getUserList() {
        return getUserListLocal()
                .onErrorResumeNext(throwable1 -> getUserListRemote())
                .compose(applySchedulers());
    }

    @Nullable
    private MultipartBody.Part getMultipartFromPath(@NonNull UploadPhotoType type, @Nullable String path) {
        path = ContentUriUtils.uriToPath(App.getInst(), Uri.parse(path));
        if (path == null) return null;
        File file = new File(path);
        if (!file.exists() || !file.isFile())
            return null;

        RequestBody requestFile = RequestBody
                .create(Const.MEDIA_TYPE_MULTIPART_FORM_DATA, file);
        return MultipartBody.Part.createFormData(type.toString(), file.getName(), requestFile);
    }

    @Nullable
    private PublicInfo getCachedUserPublicInfo() {
        User userFromCache = PreferenceCache.getUserFromCache();
        return userFromCache == null ? null : userFromCache.getPublicInfo();
    }

    public Observable<List<UserDto>> getUserListLocal() {
        return Observable.create(new Observable.OnSubscribe<List<DbUser>>() {
            @Override
            public void call(Subscriber<? super List<DbUser>> subscriber) {
                L.e("getUserListLocal ", Thread.currentThread());
                DbUserDao userDao = daoSession.getDbUserDao();
                QueryBuilder<DbUser> queryBuilder = userDao.queryBuilder();
                queryBuilder.join(DbUserAttribute.class, DbUserAttributeDao.Properties.UserRemoteId)
                        .where(DbUserAttributeDao.Properties.Deleted.eq(false));
                List<DbUser> users = queryBuilder.list();
                subscriber.onNext(users);
                subscriber.onCompleted();
            }
        })
                .flatMap(dbUsers -> Observable.from(dbUsers))
                .map(mapperUserDbDto)
                .toSortedList((userDto, userDto2) -> {
                    long order = userDto.getOrder();
                    long order2 = userDto2.getOrder();
                    return order > order2 ? 1 : order == order2 ? 0 : -1;
                })
                .map(userDtos -> {
                    if (userDtos == null || userDtos.isEmpty()) {
                        throw new RuntimeException();
                    }
                    return userDtos;
                }).compose(applySchedulers());
    }

    public Observable<List<UserDto>> getUserListRemote() {
        L.e("getUserListRemote");
        return mSoftdesignApiInterface
                .userGetList()
                .map(listBaseResponse -> listBaseResponse.getBody())
                .map(mapperListUserApiDto)
                .doOnNext(userDtos -> initAndSaveAllUsersIntoDb(userDtos))
                .compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) mSchedulersTransformer;
    }

    private void initAndSaveAllUsersIntoDb(List<UserDto> userDtos) {
        try {
            int i = 0;
            List<DbUser> dbUserList = new ArrayList<>();
            List<DbRepository> dbRepositoryList = new ArrayList<>();
            List<DbUserAttribute> dbUserAttributeList = new ArrayList<>();
            for (UserDto dto : userDtos) {
                DbUser dbUser = new DbUser();
                dbUser.setRemoteId(dto.getRemoteId());
                dbUser.setName(dto.getFullName());
                dbUser.setRating(dto.getRating());
                dbUser.setLinesCount(dto.getCodeLinesCount());
                dbUser.setProjectCount(dto.getProjectCount());
                dbUser.setMobilePhoneNumber(dto.getPhoneNumber());
                dbUser.setEmail(dto.getEmail());
                dbUser.setVkProfile(dto.getVkProfileUrl());
                dbUser.setAvatarUrl(dto.getAvatarUrl());
                dbUser.setPhotoUrl(dto.getPhotoUrl());
                dbUser.setAbout(dto.getAbout());
                dbUserList.add(dbUser);

                for (RepositoryDto repositoryDto : dto.getRepositories()) {
                    DbRepository dbRepository = new DbRepository();
                    dbRepository.setRemoteId(repositoryDto.getId());
                    dbRepository.setUserRemoteId(dbUser.getRemoteId());
                    dbRepository.setGitUrl(repositoryDto.getGitUrl());
                    dbRepositoryList.add(dbRepository);
                }
                DbUserAttribute dbUserAttribute = new DbUserAttribute();
                dbUserAttribute.setUserRemoteId(dbUser.getRemoteId());
                dbUserAttribute.setOrder(i++);
                dbUserAttributeList.add(dbUserAttribute);
            }
            daoSession.getDbRepositoryDao().insertOrReplaceInTx(dbRepositoryList);
            daoSession.getDbUserDao().insertOrReplaceInTx(dbUserList);
            daoSession.getDbUserAttributeDao().insertInTx(dbUserAttributeList);
        } catch (Exception e) {
            L.e("Cache user list error: ", e);
        }
    }
}
