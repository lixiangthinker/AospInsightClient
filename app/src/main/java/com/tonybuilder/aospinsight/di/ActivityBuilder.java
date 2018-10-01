package com.tonybuilder.aospinsight.di;

import com.tonybuilder.aospinsight.MainActivity;
import com.tonybuilder.aospinsight.view.ProjectActivity;
import com.tonybuilder.aospinsight.view.ProjectSummaryActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    //@ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract ProjectActivity contributeProjectActivity();

    @ContributesAndroidInjector
    abstract ProjectSummaryActivity contributeProjectSummaryActivity();
}
