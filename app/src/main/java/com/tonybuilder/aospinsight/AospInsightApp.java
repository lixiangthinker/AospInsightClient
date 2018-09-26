package com.tonybuilder.aospinsight;

import android.app.Activity;
import android.app.Application;

import com.tonybuilder.aospinsight.di.AppInjector;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class AospInsightApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}


// TODO: why could not use DaggerApplicatoin Instead
//public class AospInsightApp extends DaggerApplication {
//
//    @Override
//    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
//        return null;
//    }
//}
