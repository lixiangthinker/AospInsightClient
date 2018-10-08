package com.tonybuilder.aospinsight.dao;

import android.content.Context;

import com.tonybuilder.aospinsight.model.Project;
import com.tonybuilder.aospinsight.model.ProjectSummary;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ProjectSummary.class, Project.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AospInsightDatebase extends RoomDatabase {
    private static final String DB_NAME = "AospInsightDatebase.db";

    private static volatile AospInsightDatebase instance;

    public static synchronized AospInsightDatebase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AospInsightDatebase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AospInsightDatebase.class,
                DB_NAME).build();
    }
    public abstract ProjectSummaryDao projectSummaryDao();
    public abstract ProjectDao projectDao();
}