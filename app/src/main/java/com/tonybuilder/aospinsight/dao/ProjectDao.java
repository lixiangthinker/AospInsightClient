package com.tonybuilder.aospinsight.dao;

import com.tonybuilder.aospinsight.model.Project;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM project")
    List<Project> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Project... projects);

    @Delete
    void delete(Project... projects);

    @Update
    void updateProjects(Project... projects);
}
