package com.tonybuilder.aospinsight.viewmodel;

import android.util.Log;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.model.Project;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.repository.ProjectRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectViewModel extends ViewModel {
    private static final String TAG = "ProjectViewModel";
    private ProjectRepo projectRepo;
    private LiveData<Resource<Api<List<Project>>>> projects;

    private MutableLiveData<String> queryString = new MutableLiveData<>();

    @Inject
    public ProjectViewModel(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public LiveData<Resource<Api<List<Project>>>> getProjects() {
        return projectRepo.getProjects();
    }

    public LiveData<com.tonybuilder.aospinsight.repository.common.Resource<List<Project>>> getProjectsCached() {
        return projectRepo.getProjectsCached();
    }

    public LiveData<String> getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString.setValue(queryString);
    }

    public List<Project> doQuery(String query, List<Project> mData) {
        List<Project> result = new ArrayList<>();
        if (query == null || query.length() == 0) {
            Log.e(TAG, "error/null query string");
            return mData;
        }

        if (mData == null || mData.size() == 0) {
            Log.e(TAG, "error/null data set");
            return result;
        }

        for (Project p : mData) {
            if (p.getProjectName() != null && p.getProjectName().contains(query)) {
                result.add(p);
            }
        }

        return result;
    }
}
