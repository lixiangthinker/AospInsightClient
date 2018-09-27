package com.tonybuilder.aospinsight;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tonybuilder.aospinsight.dao.AospInsightDatebase;
import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.view.ProjectActivity;
import com.tonybuilder.aospinsight.view.ProjectSummaryActivity;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivity";
    private AospInsightDatebase db;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_project_summary:
                    Intent intent = new Intent(MainActivity.this, ProjectSummaryActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_project:
                    Intent intentProject = new Intent(MainActivity.this, ProjectActivity.class);
                    startActivity(intentProject);
                    return true;
            }
            return false;
        }
    };

    private ProjectSummary getProjectSummary(int projectOrigId, String strSince, String strUntil) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date since = sdf.parse(strSince);
        Date until = sdf.parse(strUntil);
        ProjectSummary result = new ProjectSummary();
        result.setProjectSummaryAdded(1000);
        result.setProjectSummaryDeleted(500);
        result.setProjectSummaryTotal(1000 + 500);
        result.setProjectSummarySince(new Timestamp(since.getTime()));
        result.setProjectSummaryUntil(new Timestamp(until.getTime()));
        result.setProjectSummaryOrigId(projectOrigId);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /**
         *  Insert and get data using Database Async way
         */
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                AospInsightDatebase db = AospInsightDatebase.getInstance(MainActivity.this);

                int projectOrigId = 390;
                ProjectSummary projectSummary1 = null;
                ProjectSummary projectSummary2 = null;
                try {
                    projectSummary1 = getProjectSummary(projectOrigId, "2017-01-01", "2017-02-01");
                    projectSummary2 = getProjectSummary(projectOrigId, "2017-02-01", "2017-03-01");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if ((projectSummary1 != null) && (projectSummary2 != null)) {
                    db.projectSummaryDao().insertAll(projectSummary1, projectSummary2);
                }

                List<ProjectSummary> result = db.projectSummaryDao().findByProjectId(projectOrigId);
                for (ProjectSummary p : result) {
                    Log.d(TAG, "project " + p.getProjectSummaryOrigId()
                            + " since " + p.getProjectSummarySince()
                            + " with id " + p.getProjectSummaryId());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (db != null) {
            db.close();
        }

        super.onDestroy();
    }
}
