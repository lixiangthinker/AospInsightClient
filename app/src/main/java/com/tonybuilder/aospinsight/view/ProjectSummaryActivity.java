package com.tonybuilder.aospinsight.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.tonybuilder.aospinsight.R;
import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.view.utils.LineChartManager;
import com.tonybuilder.aospinsight.viewmodel.ProjectSummaryViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.support.DaggerAppCompatActivity;

public class ProjectSummaryActivity extends DaggerAppCompatActivity {
    private static final String TAG = "ProjectSummaryActivity";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private ProjectSummaryViewModel projectSummaryViewModel;

    // in this example, a LineChart is initialized from xml
    private LineChart mLineChart;
    private LineData mLineData;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_summary);
        projectSummaryViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProjectSummaryViewModel.class);

        initProjectInfo(projectSummaryViewModel);
        initChart();
        subscribe();
    }

    private void initProjectInfo(ProjectSummaryViewModel viewModel) {
        String projectName = getIntent().getStringExtra("projectName");
        int projectId = getIntent().getIntExtra("projectId", 390);

        Map<Integer, String> projectMap = new HashMap<>();
        projectMap.put(projectId, projectName);
        viewModel.setProjectInfo(projectMap);
        TextView tvProjectName = findViewById(R.id.tv_project_name);
        tvProjectName.setText(projectName);
    }

    final Observer<Resource<Api<List<ProjectSummary>>>> projectSummariesObserver = resource -> {
        Log.i(TAG, "projectSummariesObserver onChange, refresh chart");
        if (resource.isSuccess()) {
            Api<List<ProjectSummary>> api = resource.getResource();
            Log.i(TAG, "onNext " + api.toString());
            List<String> xValues = new ArrayList<>();
            List<Float> yValues = new ArrayList<>();
            if (api.getResultCode() != Api.ERROR_CODE_NONE || api.getPayload() == null || api.getPayload().size() == 0) {
                Log.e(TAG, "get null payload");
                return;
            }
            for (ProjectSummary ps : api.getPayload()) {
                Log.i(TAG, ps.toString());
                String strXDate = sdf.format(ps.getProjectSummarySince());
                xValues.add(strXDate);
                yValues.add((float) ps.getProjectSummaryTotal());
            }

            //设置图表的描述
            mLineChart.setDescription(null);
            mLineData = LineChartManager.creatSingleLineChart("changed lines", xValues, yValues);

            LineChartManager.initDataStyle(mLineChart, mLineData, ProjectSummaryActivity.this);
        } else {
            Log.e(TAG, resource.getError().getMessage());
        }
    };

    private void subscribe() {
        // Create the observer which updates the UI.
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        projectSummaryViewModel.getProjectSummaries().observe(this, projectSummariesObserver);

        final Observer<Map<Integer, String>> projectIdNameObserver = integerStringMap -> {
            for (Map.Entry<Integer, String> entry : integerStringMap.entrySet()) {
                Log.i(TAG, "project = [" + entry.getKey() + "," + entry.getValue() + "]");
            }
        };
        projectSummaryViewModel.getProjectIdNameMap().observe(this, projectIdNameObserver);

        final Observer<Calendar> calendarSinceObserver = calendar -> {
            Log.i(TAG, "since = " + calendar);
        };
        projectSummaryViewModel.getCalendarSince().observe(this, calendarSinceObserver);

        final Observer<Calendar> calendarUntilObserver = calendar -> {
            Log.i(TAG, "until = " + calendar);
        };
        projectSummaryViewModel.getCalendarUntil().observe(this, calendarUntilObserver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_project_summary, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_refresh:
                Log.i(TAG, "menu_item_refresh clicked.");
                projectSummaryViewModel.getProjectSummaries().observe(this, projectSummariesObserver);
                return true;
            case R.id.menu_item_choose_project:
                Log.i(TAG, "menu_item_choose_project clicked.");
                // TODO: implement choose project function
                return true;
            case R.id.menu_item_date_since:
                Log.i(TAG, "menu_item_date_since clicked.");
                handleChooseSinceDate();
                return true;
            case R.id.menu_item_date_until:
                Log.i(TAG, "menu_item_date_until clicked.");
                handleChooseUntilDate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void handleChooseSinceDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Log.i(TAG, "onDateSet [" + year + "," + month + "," + dayOfMonth + "]");
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    projectSummaryViewModel.setCalendarSince(cal);
                },
                // TODO: need to set default value before getValue()
                projectSummaryViewModel.getCalendarSince().getValue().get(Calendar.YEAR),
                projectSummaryViewModel.getCalendarSince().getValue().get(Calendar.MONTH),
                projectSummaryViewModel.getCalendarSince().getValue().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void handleChooseUntilDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    Log.i(TAG, "onDateSet [" + year + "," + month + "," + dayOfMonth + "]");
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    projectSummaryViewModel.setCalendarUntil(cal);
                },
                // TODO: need to set default value before getValue()
                projectSummaryViewModel.getCalendarUntil().getValue().get(Calendar.YEAR),
                projectSummaryViewModel.getCalendarUntil().getValue().get(Calendar.MONTH),
                projectSummaryViewModel.getCalendarUntil().getValue().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    private List<String> getDefaultXValues() {
        String[] array = {"2018-01", "2018-02", "2018-03", "2018-01", "2018-05", "2018-06"};
        return Arrays.asList(array);
    }

    private List<Float> getDefaultYValues() {
        Float[] array = {(float) 0, (float) 0, (float) 0, (float) 0, (float) 0, (float) 0};
        return Arrays.asList(array);
    }

    private void initChart() {
        mLineChart = findViewById(R.id.chartLine);
        //clear description
        mLineChart.setDescription(null);

        List<String> xValues = getDefaultXValues();
        List<Float> yValues = getDefaultYValues();

        mLineData = LineChartManager.creatSingleLineChart("changed lines", xValues, yValues);
        LineChartManager.initDataStyle(mLineChart, mLineData, this);
    }
}
