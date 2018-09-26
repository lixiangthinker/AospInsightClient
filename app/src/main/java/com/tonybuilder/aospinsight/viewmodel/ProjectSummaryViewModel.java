package com.tonybuilder.aospinsight.viewmodel;

import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.repository.ProjectSummaryRepo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class ProjectSummaryViewModel extends ViewModel {

    private ProjectSummaryRepo projectSummaryRepo;

    private Date since;
    private Date until;
    private LiveData<Integer> projectId;
    private LiveData<String> projectName;
    private LiveData<List<ProjectSummary>> projectSummaries;

    private LiveData<Calendar> calendarSince;
    private LiveData<Calendar> calendarUntil;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Inject
    public ProjectSummaryViewModel(ProjectSummaryRepo projectSummaryRepo) {
        this.projectSummaryRepo = projectSummaryRepo;
    }
}
