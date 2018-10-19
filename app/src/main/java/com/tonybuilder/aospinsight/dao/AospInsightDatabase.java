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
public abstract class AospInsightDatabase extends RoomDatabase {
    private static final String DB_NAME = "AospInsightDatabase.db";

    private static volatile AospInsightDatabase instance;

    public static synchronized AospInsightDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AospInsightDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AospInsightDatabase.class,
                DB_NAME).build();
    }
    public abstract ProjectSummaryDao projectSummaryDao();
    public abstract ProjectDao projectDao();
}