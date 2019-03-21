/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tonybuilder.aospinsight.di;

import android.util.Log;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;
import com.github.leonardoxh.livedatacalladapter.LiveDataResponseBodyConverterFactory;
import com.tonybuilder.aospinsight.AospInsightApp;
import com.tonybuilder.aospinsight.dao.AospInsightDatabase;
import com.tonybuilder.aospinsight.dao.ProjectDao;
import com.tonybuilder.aospinsight.dao.ProjectSummaryDao;
import com.tonybuilder.aospinsight.net.RetrofitService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message ->
                Log.i("NetApiLog", message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
        return client;
    }

    @Singleton
    @Provides
    RetrofitService provideGithubService(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.43.59:8080")
                //.baseUrl("http://lixiangthinker.eicp.net:8080")
                .client(client)
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(LiveDataResponseBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService.class);
    }

    @Singleton
    @Provides
    AospInsightDatabase provideDb(AospInsightApp app) {
        return AospInsightDatabase.getInstance(app);
    }

    @Singleton
    @Provides
    ProjectSummaryDao provideProjectSummaryDao(AospInsightDatabase db) {
        return db.projectSummaryDao();
    }

    @Singleton
    @Provides
    ProjectDao provideProjectDao(AospInsightDatabase db) {
        return db.projectDao();
    }
}
