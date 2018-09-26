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

import android.app.Application;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tonybuilder.aospinsight.dao.AospInsightDatebase;
import com.tonybuilder.aospinsight.dao.ProjectSummaryDao;
import com.tonybuilder.aospinsight.net.RetrofitService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//@Module(includes = ViewModelModule.class)
@Module
class AppModule {
    @Singleton @Provides
    RetrofitService provideGithubService() {
        return new Retrofit.Builder()
                .baseUrl("http://192.168.1.111:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                // TODO: change to new LiveDataCallAdapterFactory()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(RetrofitService.class);
    }

    @Singleton @Provides
    AospInsightDatebase provideDb(Application app) {
        return AospInsightDatebase.getInstance(app);
    }

    @Singleton @Provides
    ProjectSummaryDao provideProjectSummaryDao(AospInsightDatebase db) {
        return db.projectSummaryDao();
    }
}
