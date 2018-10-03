package com.tonybuilder.aospinsight.viewmodel;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.model.Project;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.repository.ProjectRepo;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProjectViewModel extends ViewModel {

    private ProjectRepo projectRepo;
    private LiveData<Resource<Api<List<Project>>>> projects;

    @Inject
    public ProjectViewModel(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public LiveData<Resource<Api<List<Project>>>> getProjects() {
        return projectRepo.getProojects();
    }
}
