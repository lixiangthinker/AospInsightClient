package com.tonybuilder.aospinsight.repository;
/**
 * repository handle project lists
 */

import android.util.Log;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.AppExecutors;
import com.tonybuilder.aospinsight.dao.ProjectSummaryDao;
import com.tonybuilder.aospinsight.model.Project;
import com.tonybuilder.aospinsight.net.RetrofitService;
import com.tonybuilder.aospinsight.net.model.Api;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class ProjectRepo {
    private static final String TAG = "ProjectSummaryRepo";
    private final ProjectSummaryDao projectSummaryDao;
    private final RetrofitService retrofitService;
    private final AppExecutors appExecutors;

    @Inject
    ProjectRepo(AppExecutors appExecutors, ProjectSummaryDao projectSummaryDao, RetrofitService retrofitService) {
        this.projectSummaryDao = projectSummaryDao;
        this.retrofitService = retrofitService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Api<List<Project>>>> getProojects() {
        Log.i(TAG, "project id = ");
        return retrofitService.getProjects();
    }
}
