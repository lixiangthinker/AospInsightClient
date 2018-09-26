package com.tonybuilder.aospinsight.repository;

import com.tonybuilder.aospinsight.AppExecutors;
import com.tonybuilder.aospinsight.dao.ProjectSummaryDao;
import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.model.Resource;
import com.tonybuilder.aospinsight.net.ApiResponse;
import com.tonybuilder.aospinsight.net.RetrofitService;

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
    private final ProjectSummaryDao projectSummaryDao;
    private final RetrofitService retrofitService;
    private final AppExecutors appExecutors;

    @Inject
    ProjectSummaryRepo(AppExecutors appExecutors, ProjectSummaryDao projectSummaryDao, RetrofitService retrofitService) {
        this.projectSummaryDao = projectSummaryDao;
        this.retrofitService = retrofitService;
        this.appExecutors = appExecutors;
    }
//
//    public LiveData<Resource<ProjectSummary>> loadUser(String login) {
//        return new NetworkBoundResource<ProjectSummary,ProjectSummary>(appExecutors) {
//            @Override
//            protected void saveCallResult(@NonNull ProjectSummary item) {
//                projectSummaryDao.insert(item);
//            }
//
//            @Override
//            protected boolean shouldFetch(@Nullable ProjectSummary data) {
//                return data == null;
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<ProjectSummary> loadFromDb() {
//                return projectSummaryDao.findByLogin(login);
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<ApiResponse<ProjectSummary>> createCall() {
//                return retrofitService.getUser(login);
//            }
//        }.asLiveData();
//    }
}

