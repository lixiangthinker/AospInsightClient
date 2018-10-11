package com.tonybuilder.aospinsight.repository;

import android.util.Log;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.AppExecutors;
import com.tonybuilder.aospinsight.dao.ProjectSummaryDao;
import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.net.RetrofitService;
import com.tonybuilder.aospinsight.net.model.Api;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.LiveData;

/**
 * repository handle project summary objects
 */

@Singleton
public class ProjectSummaryRepo {
    private static final String TAG = "ProjectSummaryRepo";
    private final ProjectSummaryDao projectSummaryDao;
    private final RetrofitService retrofitService;
    private final AppExecutors appExecutors;

    @Inject
    ProjectSummaryRepo(AppExecutors appExecutors, ProjectSummaryDao projectSummaryDao, RetrofitService retrofitService) {
        this.projectSummaryDao = projectSummaryDao;
        this.retrofitService = retrofitService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<Api<List<ProjectSummary>>>> loadProjectSummaries(Integer projectId, String since, String until) {
        Log.i(TAG, "project id = " + projectId + "since = " + since + " until = " + until);
        return retrofitService.getProjectSummary(projectId, since, until);

//        //TODO: fetch from net api or local database cache.
//        Observable<Api<List<ProjectSummary>>> observableProjectSummary = retrofitService.getProjectSummary(projectId, since, until);
//        observableProjectSummary.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new io.reactivex.Observer<Api<List<ProjectSummary>>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.i(TAG, "onSubscribe ");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.i(TAG, "onError " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.i(TAG, "onComplete ");
//                        LineChartManager.initDataStyle(mLineChart, mLineData, ProjectSummaryActivity.this);
//                    }
//
//                    @Override
//                    public void onNext(Api<List<ProjectSummary>> api) {
//                        Log.i(TAG, "onNext " + api.toString());
//                        List<String> xValues = new ArrayList<>();
//                        List<Float> yValues = new ArrayList<>();
//                        if (api.getPayload() == null || api.getPayload().size() == 0) {
//                            Log.e(TAG, "get null payload");
//                            return;
//                        }
//                        for (ProjectSummary ps : api.getPayload()) {
//                            Log.i(TAG, ps.toString());
//                            String strXDate = sdf.format(ps.getProjectSummarySince());
//                            xValues.add(strXDate);
//                            yValues.add((float) ps.getProjectSummaryTotal());
//                        }
//
//                        //设置图表的描述
//                        mLineChart.setDescription(null);
//                        mLineData = LineChartManager.creatSingleLineChart("changed lines", xValues, yValues);
//                    }
//                });
//    }

    }

//    public LiveData<Resource<ProjectSummary>> loadProjectSummary(String login) {
//        return new NetworkBoundResource<ProjectSummary,ProjectSummary>(appExecutors) {
//            @Override
//            protected void saveCallResult(@NonNull ProjectSummary item) {
//                projectSummaryDao.insert(item);
//            }
//
//            @Override
//            protected boolean shouldFetch(@Nullable ProjectSummary data) {
//                return data == null;
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<ProjectSummary> loadFromDb() {
//                return projectSummaryDao.findByLogin(login);
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<ApiResponse<ProjectSummary>> createCall() {
//                return retrofitService.getUser(login);
//            }
//        }.asLiveData();
//    }
}

