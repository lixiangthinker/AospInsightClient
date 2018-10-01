package com.tonybuilder.aospinsight.di;

import com.tonybuilder.aospinsight.viewmodel.AospInsightViewModelFactory;
import com.tonybuilder.aospinsight.viewmodel.ProjectSummaryViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProjectSummaryViewModel.class)
    abstract ViewModel bindProjectSummaryViewModel(ProjectSummaryViewModel projectSummaryViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AospInsightViewModelFactory factory);
}
