package com.tonybuilder.aospinsight.repository;

import android.util.Log;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.AppExecutors;
import com.tonybuilder.aospinsight.dao.ProjectDao;
import com.tonybuilder.aospinsight.model.Project;
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
 * repository handle project lists
 */
@Singleton
public class ProjectRepo {
    private static final String TAG = "ProjectSummaryRepo";
    private final ProjectDao projectDao;
    private final RetrofitService retrofitService;
    private final AppExecutors appExecutors;

    @Inject
    ProjectRepo(AppExecutors appExecutors, ProjectDao projectDao, RetrofitService retrofitService) {
        this.projectDao = projectDao;
        this.retrofitService = retrofitService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Api<List<Project>>>> getProjects() {
        Log.i(TAG, "project id = ");
        return retrofitService.getProjects();
    }

    public LiveData<StatusResource<List<Project>>> getProjectsCached() {
        Log.i(TAG, "project id = ");
        return new NetworkBoundResource<List<Project>,Api<List<Project>>>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull Api<List<Project>> item) {
                for (Project project: item.getPayload()) {
                    projectDao.insert(project);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Project> data) {
                // TODO: repoListRateLimit.shouldFetch(owner)
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Project>> loadFromDb() {
                return projectDao.getAll();
            }

            @NonNull
            @Override
            protected LiveData<Resource<Api<List<Project>>>> createCall() {
                return retrofitService.getProjects();
            }
        }.asLiveData();
    }
}
