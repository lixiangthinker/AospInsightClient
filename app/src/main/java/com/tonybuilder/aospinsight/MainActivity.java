package com.tonybuilder.aospinsight;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tonybuilder.aospinsight.view.ProjectActivity;
import com.tonybuilder.aospinsight.view.ProjectSummaryActivity;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {
    private static final String TAG = "MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Log.i(TAG, "navigating to home");
                        return true;
                    case R.id.navigation_project_summary:
                        Log.i(TAG, "navigating to project summary");
                        Intent intent = new Intent(MainActivity.this, ProjectSummaryActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_project:
                        Log.i(TAG, "navigating to project list");
                        Intent intentProject = new Intent(MainActivity.this, ProjectActivity.class);
                        startActivity(intentProject);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
