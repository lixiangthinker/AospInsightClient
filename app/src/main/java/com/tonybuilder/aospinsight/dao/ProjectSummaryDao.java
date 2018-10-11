package com.tonybuilder.aospinsight.dao;

import com.tonybuilder.aospinsight.model.ProjectSummary;

import java.sql.Timestamp;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProjectSummaryDao {
    @Query("SELECT * FROM projectsummary")
    LiveData<List<ProjectSummary>> getAll();

    @Query("SELECT * FROM projectsummary WHERE project_summary_orig_id = :projectId")
    LiveData<List<ProjectSummary>> findByProjectId(int projectId);

    @Query("SELECT * FROM projectsummary WHERE :projectId = project_summary_orig_id AND project_summary_since BETWEEN :since AND :until")
    LiveData<List<ProjectSummary>> findByProjectIdBetween(int projectId, Timestamp since, Timestamp until);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ProjectSummary... projectSummaries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProjectSummary projectSummary);

    @Delete
    void delete(ProjectSummary... projectSummaries);

    @Update
    void updateProjectSummaryrs(ProjectSummary... projectSummaries);
}
