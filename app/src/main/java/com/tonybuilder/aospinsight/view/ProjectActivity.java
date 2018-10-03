package com.tonybuilder.aospinsight.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.google.android.material.snackbar.Snackbar;
import com.tonybuilder.aospinsight.R;
import com.tonybuilder.aospinsight.model.Project;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ProjectActivity extends DaggerAppCompatActivity {

    private static final String TAG = "ProjectActivity";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ProjectViewModel projectViewModel;

    RecyclerView rvProjectList;
    List<Project> mData;
    private ProjectListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        projectViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProjectViewModel.class);

        projectViewModel.getProjects().observe(this, new Observer<Resource<Api<List<Project>>>>() {
            @Override
            public void onChanged(Resource<Api<List<Project>>> apiResource) {
                if (apiResource.isSuccess()) {
                    //TODO: show project in recycler view.
                    Log.e(TAG, apiResource.getResource().getPayload().size()+ " projects updated");
                    mData = apiResource.getResource().getPayload();
                    mAdapter.notifyDataSetChanged();
                    Snackbar.make(rvProjectList, apiResource.getResource().getPayload().size()+ " projects updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Log.e(TAG, apiResource.getError().getMessage());
                }
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        initData();
        rvProjectList = findViewById(R.id.rv_project_list);
        rvProjectList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProjectListAdapter();
        rvProjectList.setAdapter(mAdapter);
    }

    private void initData() {
        Project project = new Project();
        project.setProjectId(0);
        project.setProjectName("Null project");
        mData = new ArrayList<>();
        mData.add(project);
    }

    class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectEntryViewHolder>
    {
        @Override
        public ProjectEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            ProjectEntryViewHolder holder = new ProjectEntryViewHolder(LayoutInflater.from(
                    ProjectActivity.this).inflate(R.layout.item_project_entry, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ProjectEntryViewHolder holder, int position)
        {
            holder.tvProjectId.setText(String.valueOf(mData.get(position).getProjectId()));
            holder.tvProjectName.setText(mData.get(position).getProjectName());
        }

        @Override
        public int getItemCount()
        {
            return mData.size();
        }

        class ProjectEntryViewHolder extends RecyclerView.ViewHolder
        {
            TextView tvProjectName;
            TextView tvProjectId;

            public ProjectEntryViewHolder(View view)
            {
                super(view);
                tvProjectId = view.findViewById(R.id.tv_project_id);
                tvProjectName = view.findViewById(R.id.tv_project_name);
            }
        }
    }
}
