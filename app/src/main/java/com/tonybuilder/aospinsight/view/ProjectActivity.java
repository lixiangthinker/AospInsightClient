package com.tonybuilder.aospinsight.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tonybuilder.aospinsight.R;

public class ProjectActivity extends AppCompatActivity {

    private static final String TAG = "ProjectActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
    }
}
