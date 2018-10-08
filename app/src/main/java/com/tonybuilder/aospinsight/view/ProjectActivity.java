package com.tonybuilder.aospinsight.view;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;
import com.tonybuilder.aospinsight.R;
import com.tonybuilder.aospinsight.model.Project;
import com.tonybuilder.aospinsight.repository.common.Status;
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

        initRecyclerView();
        subscribe();
    }

    private void initData() {
        Project project = new Project();
        project.setProjectId(0);
        project.setProjectName("Null project");
        mData = new ArrayList<>();
        mData.add(project);
    }

    private void initRecyclerView() {
        initData();
        rvProjectList = findViewById(R.id.rv_project_list);
        rvProjectList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ProjectListAdapter(this);
        mAdapter.setProjectList(mData);
        rvProjectList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, data) -> {
            Log.i(TAG, "project name " + data.getProjectName() + " project id " + data.getProjectId());
            Intent intent = new Intent(this, ProjectSummaryActivity.class);
            intent.putExtra("projectId", data.getProjectId());
            intent.putExtra("projectName", data.getProjectName());
            startActivity(intent);
        });
    }
    private void subscribe() {
        // refresh project data
//        projectViewModel.getProjects().observe(this, apiResource -> {
//            if (apiResource.isSuccess()) {
//                Log.i(TAG, apiResource.getResource().getPayload().size() + " projects updated");
//                mData = apiResource.getResource().getPayload();
//                mAdapter.setProjectList(mData);
//                mAdapter.notifyDataSetChanged();
//                Snackbar.make(rvProjectList, apiResource.getResource().getPayload().size() + " projects updated", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            } else {
//                Log.e(TAG, apiResource.getError().getMessage());
//            }
//        });

        // refresh project data from NetworkBoundResource
        projectViewModel.getProjectsCached().observe(this, networkBoundResource -> {
            Log.i(TAG, "networkBoundResource.status = " + networkBoundResource.status);
            switch(networkBoundResource.status) {
                case SUCCESS:
                    Log.i(TAG, networkBoundResource.data.size() + " projects updated");
                    mData = networkBoundResource.data;
                    mAdapter.setProjectList(mData);
                    mAdapter.notifyDataSetChanged();
                    Snackbar.make(rvProjectList, networkBoundResource.data.size() + " projects updated", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                case ERROR:
                    Log.e(TAG, networkBoundResource.message);
                    break;
                case LOADING:
                    break;
                default:
                    Log.e(TAG, "error network resource status.");
                    break;
            }
        });

        // refresh search result
        projectViewModel.getQueryString().observe(this, query -> {
            Log.d(TAG, "query = " + query);
            List<Project> result = projectViewModel.doQuery(query, mData);
            mAdapter.setKeyWords(query);
            mAdapter.setProjectList(result);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        initSearchView(menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Map search view event to live data change.
     */
    private void initSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.menu_project_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setIconified(false);
        mSearchView.setQueryHint("input project name");

        mSearchView.setOnCloseListener(() -> {
            Log.i(TAG, "search view text closed");
            projectViewModel.setQueryString(null);
            return true;
        });

        mSearchView.setOnSearchClickListener(v -> Log.i(TAG, "search view opened"));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(TAG, "search view text submitted");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.i(TAG, "search view text changed");
                projectViewModel.setQueryString(s);
                return true;
            }
        });
    }
}
