package com.tonybuilder.aospinsight.testutil.net;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory;
import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.model.Commit;
import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.net.RetrofitService;
import com.tonybuilder.aospinsight.net.model.Api;
import com.tonybuilder.aospinsight.net.model.PagingInfo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tonybuilder.aospinsight.testutil.LiveDataTestUtil.getValue;

@RunWith(JUnit4.class)
public class RetrofitServiceTest {
    private RetrofitService retrofitService;
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setupTest() {
        retrofitService = initLiveDataNetService();
    }

    private RetrofitService initLiveDataNetService() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.111:8080/")
                .client(client)
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService.class);
    }

    @Test
    public void getProjectSummary() throws InterruptedException {
        Resource<Api<List<ProjectSummary>>> resource =
                getValue(retrofitService.getProjectSummary(390, "2018-01-01", "2018-09-01"));
        System.out.println("resource.isSuccess" + resource.isSuccess());
        System.out.println("resource.getError()" + resource.getError());

        Api<List<ProjectSummary>> api = resource.getResource();
        Assert.assertNotNull(api);
        System.out.println("api " + api);
        List<ProjectSummary> payload = api.getPayload();
        for (ProjectSummary ps : payload) {
            System.out.println(ps);
        }
    }

    @Test
    public void getCommitList() throws InterruptedException {
        Resource<Api<List<Commit>>> resource =
                getValue(retrofitService.getCommitsByMonth(390, "2018-01", 1,10));
        System.out.println("resource.isSuccess" + resource.isSuccess());
        System.out.println("resource.getError()" + resource.getError());

        Api<List<Commit>> api = resource.getResource();
        Assert.assertNotNull(api);
        System.out.println("api " + api);
        List<Commit> payload = api.getPayload();
        for (Commit commit : payload) {
            System.out.println(commit);
        }
        PagingInfo page = api.getPageInfo();
        System.out.println("pageInfo = " + page);
    }
}