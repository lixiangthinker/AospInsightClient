package com.tonybuilder.aospinsight.view;

import dagger.android.support.DaggerAppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tonybuilder.aospinsight.R;

public class ProjectActivity extends DaggerAppCompatActivity {

    private static final String TAG = "ProjectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
    }
}
