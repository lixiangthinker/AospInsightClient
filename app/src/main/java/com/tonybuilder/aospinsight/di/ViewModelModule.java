package com.tonybuilder.aospinsight.di;

import com.tonybuilder.aospinsight.viewmodel.AospInsightViewModelFactory;
import com.tonybuilder.aospinsight.viewmodel.CommitListViewModel;
import com.tonybuilder.aospinsight.viewmodel.ProjectSummaryViewModel;
import com.tonybuilder.aospinsight.viewmodel.ProjectViewModel;

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
    @IntoMap
    @ViewModelKey(ProjectViewModel.class)
    abstract ViewModel bindProjectViewModel(ProjectViewModel projectViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CommitListViewModel.class)
    abstract ViewModel bindCommitListViewModel(CommitListViewModel commitListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AospInsightViewModelFactory factory);
}
