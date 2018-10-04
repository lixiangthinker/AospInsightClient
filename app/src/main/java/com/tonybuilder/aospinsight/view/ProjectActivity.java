package com.tonybuilder.aospinsight.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.google.android.material.snackbar.Snackbar;
import com.tonybuilder.aospinsight.MainActivity;
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
                    Log.i(TAG, apiResource.getResource().getPayload().size()+ " projects updated");
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
        mAdapter.setOnItemClickListener((view, data) -> {
            Log.i(TAG, "project name " + data.getProjectName() + " project id " + data.getProjectId());
            Intent intent = new Intent(this, ProjectSummaryActivity.class);
            intent.putExtra("projectId", data.getProjectId());
            intent.putExtra("projectName", data.getProjectName());
            startActivity(intent);
        });
    }

    private void initData() {
        Project project = new Project();
        project.setProjectId(0);
        project.setProjectName("Null project");
        mData = new ArrayList<>();
        mData.add(project);
    }
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Project data);
    }

    class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ProjectEntryViewHolder>
            implements View.OnClickListener
    {
        private OnRecyclerViewItemClickListener mOnItemClickListener = null;
        public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v,(Project)v.getTag());
            }
        }

        @Override
        public ProjectEntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from( ProjectActivity.this).inflate(R.layout.item_project_entry,
                    parent, false);
            ProjectEntryViewHolder holder = new ProjectEntryViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(ProjectEntryViewHolder holder, int position)
        {
            holder.tvProjectId.setText(String.valueOf(mData.get(position).getProjectId()));
            holder.tvProjectName.setText(mData.get(position).getProjectName());
            holder.itemView.setTag(mData.get(position));
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
            ImageView imvProjectDetail;

            public ProjectEntryViewHolder(View view)
            {
                super(view);
                tvProjectId = view.findViewById(R.id.tv_project_id);
                tvProjectName = view.findViewById(R.id.tv_project_name);
                imvProjectDetail = view.findViewById(R.id.imv_project_detail);
            }
        }
    }
}
