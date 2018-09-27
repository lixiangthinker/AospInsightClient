package com.tonybuilder.aospinsight.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tonybuilder.aospinsight.R;
import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.net.RetrofitService;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.view.utils.LineChartManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectSummaryActivity extends DaggerAppCompatActivity {
    private static final String TAG = "ProjectSummaryActivity";

    @Inject
    RetrofitService service;

    //private ActionBar actionBar;

    // in this example, a LineChart is initialized from xml
    private LineChart mLineChart;
    private LineData mLineData;

    private ProjectSummaryViewModel projectSummaryViewModel = new ProjectSummaryViewModel();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_summary);

        //init net service
        //initNetService();
        //MPAndroidChart
        initChart();
        refreshProjectSummaryChart(390, "2017-01-01", "2018-09-01");
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
                refreshProjectSummaryChart(projectSummaryViewModel.getProjectId(),
                        projectSummaryViewModel.getStringSince(),
                        projectSummaryViewModel.getStringUntil());
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
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.i(TAG, "onDateSet [" + year + "," + month + "," + dayOfMonth + "]");
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        projectSummaryViewModel.setCaSince(cal);
                    }
                },
                projectSummaryViewModel.getCaSince().get(Calendar.YEAR),
                projectSummaryViewModel.getCaSince().get(Calendar.MONTH),
                projectSummaryViewModel.getCaSince().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void handleChooseUntilDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.i(TAG, "onDateSet [" + year + "," + month + "," + dayOfMonth + "]");
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        projectSummaryViewModel.setCaUntil(cal);
                    }
                },
                projectSummaryViewModel.getCaUntil().get(Calendar.YEAR),
                projectSummaryViewModel.getCaUntil().get(Calendar.MONTH),
                projectSummaryViewModel.getCaUntil().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    private List<String> getDefaultXValues() {
        String[] array = {"2018-01", "2018-02", "2018-03", "2018-01", "2018-05", "2018-06"};
        List<String> xValues = Arrays.asList(array);
        return xValues;
    }

    private List<Float> getDefaultYValues() {
        Float[] array = {new Float(0), new Float(0), new Float(0),
                new Float(0), new Float(0), new Float(0)};
        List<Float> yValues = Arrays.asList(array);
        return yValues;
    }

    private void initChart() {
        mLineChart = findViewById(R.id.chartLine);
        //clear description
        mLineChart.setDescription(null);
        //设置x轴的数据
        List<String> xValues = getDefaultXValues();
        List<Float> yValues = getDefaultYValues();
        //设置y轴的数据
        mLineData = LineChartManager.creatSingleLineChart("changed lines", xValues, yValues);
        LineChartManager.initDataStyle(mLineChart, mLineData, this);
    }

//    private void initNetService() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.111:8080/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        service = retrofit.create(RetrofitService.class);
//    }

    private void refreshProjectSummaryChart(int projectId, String since, String until) {
        Log.i(TAG, "");
        Observable<Api<List<ProjectSummary>>> observableProjectSummary = service.getProjectSummary(projectId, since, until);
        observableProjectSummary.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Api<List<ProjectSummary>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete ");
                        LineChartManager.initDataStyle(mLineChart, mLineData, ProjectSummaryActivity.this);
                    }

                    @Override
                    public void onNext(Api<List<ProjectSummary>> api) {
                        Log.i(TAG, "onNext " + api.toString());
                        List<String> xValues = new ArrayList<>();
                        List<Float> yValues = new ArrayList<>();
                        if (api.getPayload() == null || api.getPayload().size() == 0) {
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
                    }
                });
    }

    class ProjectSummaryViewModel {
        private Date since;
        private Date until;
        private int projectId;
        private String projectName;
        private List<ProjectSummary> projectSummaries;

        private Calendar caSince;
        private Calendar caUntil;

        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        public ProjectSummaryViewModel() {
            try {
                since = sdf.parse("2017-01-01");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            until = new Date();
            projectId = 390;
            projectName = "platform/frameworks/base";
            projectSummaries = new ArrayList<>();
            caSince = Calendar.getInstance();
            caUntil = Calendar.getInstance();
        }

        public String getStringSince() {
            return sdf.format(since);
        }

        public String getStringUntil() {
            return sdf.format(until);
        }

        public Date getSince() {
            return since;
        }

        public void setSince(Date since) {
            this.since = since;
        }

        public Date getUntil() {
            return until;
        }

        public void setUntil(Date until) {
            this.until = until;
        }

        public int getProjectId() {
            return projectId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public List<ProjectSummary> getProjectSummaries() {
            return projectSummaries;
        }

        public void setProjectSummaries(List<ProjectSummary> projectSummaries) {
            this.projectSummaries = projectSummaries;
        }

        public Calendar getCaSince() {
            return caSince;
        }

        public void setCaSince(Calendar caSince) {
            this.caSince = caSince;

            if (caSince == null) {
                return;
            }

            since = new Date(caSince.getTimeInMillis());
        }

        public Calendar getCaUntil() {
            return caUntil;
        }

        public void setCaUntil(Calendar caUntil) {
            this.caUntil = caUntil;

            if (caUntil == null) {
                return;
            }

            until = new Date(caUntil.getTimeInMillis());
        }
    }

}
