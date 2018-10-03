package com.tonybuilder.aospinsight.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.google.android.material.snackbar.Snackbar;
import com.tonybuilder.aospinsight.R;
import com.tonybuilder.aospinsight.model.Project;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.viewmodel.ProjectViewModel;

import java.util.List;

import javax.inject.Inject;

public class ProjectActivity extends DaggerAppCompatActivity {

    private static final String TAG = "ProjectActivity";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ProjectViewModel projectViewModel;

    RecyclerView rvProjectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        rvProjectList = findViewById(R.id.rv_project_list);

        projectViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProjectViewModel.class);

        projectViewModel.getProjects().observe(this, new Observer<Resource<Api<List<Project>>>>() {
            @Override
            public void onChanged(Resource<Api<List<Project>>> apiResource) {
                if (apiResource.isSuccess()) {
                    //TODO: show project in recycler view.
                    Log.e(TAG, apiResource.getResource().getPayload().size()+ " projects updated");
                    Snackbar.make(rvProjectList, apiResource.getResource().getPayload().size()+ " projects updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Log.e(TAG, apiResource.getError().getMessage());
                }
            }
        });
    }
}
