package com.tonybuilder.aospinsight.repository;
/**
 * repository handle commit lists
 */

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.AppExecutors;
import com.tonybuilder.aospinsight.model.Commit;
import com.tonybuilder.aospinsight.net.RetrofitService;
import com.tonybuilder.aospinsight.net.model.Api;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

@Singleton
public class CommitRepo {
    private static final String TAG = "CommitRepo";
    private final RetrofitService retrofitService;
    private final AppExecutors appExecutors;

    @Inject
    CommitRepo(AppExecutors appExecutors, RetrofitService retrofitService) {
        this.retrofitService = retrofitService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Api<List<Commit>>>> getCommitsByMonth(int projectId, String month) {
        return retrofitService.getCommitsByMonth(projectId, month);
    }
}
