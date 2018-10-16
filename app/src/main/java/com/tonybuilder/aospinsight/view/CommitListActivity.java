package com.tonybuilder.aospinsight.view;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerAppCompatActivity;

import android.os.Bundle;

import com.tonybuilder.aospinsight.R;
import com.tonybuilder.aospinsight.model.Commit;
import com.tonybuilder.aospinsight.viewmodel.CommitListViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CommitListActivity extends DaggerAppCompatActivity {
    private static final String TAG = "CommitListActivity";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private CommitListViewModel commitListViewModel;

    RecyclerView rvCommitList;
    List<Commit> mData;
    private CommitListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_list);
        commitListViewModel = ViewModelProviders.of(this, viewModelFactory).get(CommitListViewModel.class);
        initRecyclerView();
        subscribe();
    }
    private void initData() {
        Commit commit = new Commit();
        commit.setCommitHashId("null");
        commit.setCommitAuthor(" nobody");
        mData = new ArrayList<>();
        mData.add(commit);
    }
    private void initRecyclerView() {
        initData();
        rvCommitList = findViewById(R.id.rv_commit_list);
        rvCommitList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CommitListAdapter(this);
        mAdapter.setCommitList(mData);
        rvCommitList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, data) -> {
            //TODO: navigate to commit detail page
        });
    }
    private void subscribe() {
        //TODO: subscribe live data event here.
    }
}
