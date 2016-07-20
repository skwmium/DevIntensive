package com.softdesign.devintensive.model.mappers;

import com.softdesign.devintensive.model.dto.RepositoryDto;
import com.softdesign.devintensive.model.network.dto.Repo;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by skwmium on 20.07.16.
 */
public class MapperRepoApiDto implements Func1<Repo, RepositoryDto> {
    @Inject
    public MapperRepoApiDto() {
    }

    @Override
    public RepositoryDto call(Repo repoApi) {
        return Observable.just(repoApi)
                .map(repo -> {
                    RepositoryDto dto = new RepositoryDto();
                    dto.setId(repo.getId());
                    dto.setTitle(repo.getTitle());
                    dto.setGitUrl(repo.getGitUrl());
                    return dto;
                })
                .toBlocking()
                .first();
    }
}
