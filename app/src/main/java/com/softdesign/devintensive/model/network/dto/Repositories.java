package com.softdesign.devintensive.model.network.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by skwmium on 27.06.16.
 */
@SuppressWarnings("unused")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Repositories extends BaseValueObject {
    @JsonProperty("repo")
    private List<Repo> mRepositories = new ArrayList<>();

    @JsonProperty("updated")
    private Date mUpdated;

    public List<Repo> getRepositories() {
        return mRepositories;
    }

    public void setRepositories(List<Repo> repositories) {
        mRepositories = repositories;
    }

    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date updated) {
        mUpdated = updated;
    }
}
