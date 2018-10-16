package com.tonybuilder.aospinsight.viewmodel;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.model.Commit;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.repository.CommitRepo;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CommitListViewModel extends ViewModel {
    private static final String TAG = "CommitListViewModel";
    private CommitRepo commitRepo;
    private LiveData<Resource<Api<List<Commit>>>> commits;

    @Inject
    public CommitListViewModel(CommitRepo commitRepo) {
        this.commitRepo = commitRepo;
    }
}
