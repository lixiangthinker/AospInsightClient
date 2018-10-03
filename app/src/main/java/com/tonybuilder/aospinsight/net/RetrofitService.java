package com.tonybuilder.aospinsight.net;

import com.github.leonardoxh.livedatacalladapter.Resource;
import com.tonybuilder.aospinsight.model.Project;
import com.tonybuilder.aospinsight.model.ProjectSummary;
import com.tonybuilder.aospinsight.net.model.Api;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    /**
     * get summary info for each project
     * @param projectId  id for project
     * @param since  since date, format "yyyy-MM-dd"
     * @param until  until date, format "yyyy-MM-dd"
     */
    @GET("api/projectSummary/{projectId}/{since}/{until}")
    LiveData<Resource<Api<List<ProjectSummary>>>> getProjectSummary(@Path("projectId") Integer projectId,
                                                                    @Path("since") String since,
                                                                    @Path("until") String until);

    /**
     * get project list info
     */
    @GET("api/project")
    LiveData<Resource<Api<List<Project>>>> getProjects();
}