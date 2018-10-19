package com.tonybuilder.aospinsight.viewmodel;

import android.util.SparseArray;

import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.repository.ProjectSummaryRepo;
import com.tonybuilder.aospinsight.repository.common.StatusResource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProjectSummaryViewModel extends ViewModel {

    private static final int DEFAULT_PROJECT_ID = 390;

    private ProjectSummaryRepo projectSummaryRepo;
    private MutableLiveData<SparseArray<String>> projectIdNameArray = new MutableLiveData<>();

    private LiveData<StatusResource<List<ProjectSummary>>> projectSummaries;

    private MutableLiveData<Calendar> calendarSince = new MutableLiveData<>();
    private MutableLiveData<Calendar> calendarUntil = new MutableLiveData<>();

    @Inject
    public ProjectSummaryViewModel(ProjectSummaryRepo projectSummaryRepo) {
        this.projectSummaryRepo = projectSummaryRepo;
    }

    public LiveData<SparseArray<String>> getProjectIdNameArray() {
        return projectIdNameArray;
    }

    private String getDateString(LiveData<Calendar> date, @NonNull String defaultValue) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calDate = date.getValue();
        if (calDate == null) {
            return defaultValue;
        }
        return sdf.format(calDate.getTime());
    }

    private Integer getProjectIdLocal(LiveData<SparseArray<String>> liveArray) {
        SparseArray<String> projectIdArray = liveArray.getValue();
        if (projectIdArray == null || projectIdArray.size() == 0) {
            return DEFAULT_PROJECT_ID;
        }
        return projectIdArray.keyAt(0);
    }

    public LiveData<StatusResource<List<ProjectSummary>>> getProjectSummaries() {

        String since = getDateString(calendarSince, "2017-01-01");
        String until = getDateString(calendarUntil,"2018-09-01");

        Integer projectId = getProjectIdLocal(projectIdNameArray);

        projectSummaries = projectSummaryRepo.loadProjectSummaries(projectId, since, until);

        return projectSummaries;
    }

    public void setCalendarSince(Calendar cal) {
        calendarSince.setValue(cal);
    }
    public LiveData<Calendar> getCalendarSince() {
        return calendarSince;
    }
    public void setCalendarUntil(Calendar cal) {
        calendarUntil.setValue(cal);
    }
    public LiveData<Calendar> getCalendarUntil() {
        return calendarUntil;
    }

    public void setProjectInfo(SparseArray<String> projectArray) {
        projectIdNameArray.setValue(projectArray);
    }

    public void onValueSelected(String month) {
    }
}
