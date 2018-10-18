package com.tonybuilder.aospinsight.dao;

import android.content.Context;
import android.util.Log;

import com.tonybuilder.aospinsight.model.ProjectSummary;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class ProjectSummaryDaoTests {
    private static final String TAG = "ProjectSummaryDaoTests";
    private static final String DB_NAME = "AospInsightDatebase.db";
    private ProjectSummaryDao projectSummaryDao;
    private AospInsightDatebase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        //mDb = Room.inMemoryDatabaseBuilder(context, AospInsightDatebase.class).build();
        mDb = Room.databaseBuilder(context, AospInsightDatebase.class, DB_NAME).build();
        projectSummaryDao = mDb.projectSummaryDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    private ProjectSummary getProjectSummary(int projectOrigId, String strSince, String strUntil) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date since = sdf.parse(strSince);
        Date until = sdf.parse(strUntil);
        ProjectSummary result = new ProjectSummary();
        result.setProjectSummaryAdded(1000);
        result.setProjectSummaryDeleted(500);
        result.setProjectSummaryTotal(1000+500);
        result.setProjectSummarySince(new Timestamp(since.getTime()));
        result.setProjectSummaryUntil(new Timestamp(until.getTime()));
        result.setProjectSummaryOrigId(projectOrigId);
        return result;
    }

    @Test
    public void writeProjectSummaryAndReadInList() throws Exception {
//        int projectOrigId = 390;
//        ProjectSummary projectSummary = getProjectSummary(projectOrigId,"2017-01-01", "2017-02-01");
//        projectSummaryDao.insertAll(projectSummary);
//        projectSummary = getProjectSummary(projectOrigId,"2017-02-01", "2017-03-01");
//        projectSummaryDao.insertAll(projectSummary);
//
//        List<ProjectSummary> result = projectSummaryDao.findByProjectId(projectOrigId);
//        Assert.assertNotNull(result);
//        for (ProjectSummary p : result) {
//            Log.d(TAG,"project " + p.getProjectSummaryOrigId()
//                    + " since " + p.getProjectSummarySince()
//                    + " with id " + p.getProjectSummaryId());
//        }
    }
}
