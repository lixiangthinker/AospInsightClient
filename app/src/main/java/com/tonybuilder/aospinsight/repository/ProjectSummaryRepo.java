package com.tonybuilder.aospinsight.repository;

import android.util.Log;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.AppExecutors;
import com.tonybuilder.aospinsight.dao.ProjectSummaryDao;
import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.net.RetrofitService;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.repository.common.NetworkBoundResource;
import com.tonybuilder.aospinsight.repository.common.StatusResource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * repository handle project summary objects
 */

@Singleton
public class ProjectSummaryRepo {
    private static final String TAG = "ProjectSummaryRepo";
    private final ProjectSummaryDao projectSummaryDao;
    private final RetrofitService retrofitService;
    private final AppExecutors appExecutors;

    @Inject
    ProjectSummaryRepo(AppExecutors appExecutors, ProjectSummaryDao projectSummaryDao, RetrofitService retrofitService) {
        this.projectSummaryDao = projectSummaryDao;
        this.retrofitService = retrofitService;
        this.appExecutors = appExecutors;
    }

    public LiveData<StatusResource<List<ProjectSummary>>> loadProjectSummaries(Integer projectId, String since, String until) {
        Log.i(TAG, "project id = " + projectId + "since = " + since + " until = " + until);
        //return retrofitService.getProjectSummary(projectId, since, until);
        return new NetworkBoundResource<List<ProjectSummary>,Api<List<ProjectSummary>>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull Api<List<ProjectSummary>> item) {
                for (ProjectSummary projectSummary: item.getPayload()) {
                    projectSummaryDao.insert(projectSummary);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ProjectSummary> data) {
                // TODO: repoListRateLimit.shouldFetch(owner)
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ProjectSummary>> loadFromDb() {
                return projectSummaryDao.findByProjectId(projectId);
            }

            @NonNull
            @Override
            protected LiveData<Resource<Api<List<ProjectSummary>>>> createCall() {
                return retrofitService.getProjectSummary(projectId, since, until);
            }
        }.asLiveData();
    }
}

