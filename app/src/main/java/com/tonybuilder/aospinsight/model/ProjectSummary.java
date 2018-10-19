package com.tonybuilder.aospinsight.model;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProjectSummary {
    @PrimaryKey
    private int projectSummaryId;

    @ColumnInfo(name = "project_summary_orig_id")
    private int projectSummaryOrigId;

    @ColumnInfo(name = "project_summary_added")
    private int projectSummaryAdded;

    @ColumnInfo(name = "project_summary_deleted")
    private int projectSummaryDeleted;

    @ColumnInfo(name = "project_summary_total")
    private int projectSummaryTotal;

    @ColumnInfo(name = "project_summary_since")
    private Date projectSummarySince;

    @ColumnInfo(name = "project_summary_until")
    private Date projectSummaryUntil;

    public int getProjectSummaryId() {
        return projectSummaryId;
    }

    public void setProjectSummaryId(int projectSummaryId) {
        this.projectSummaryId = projectSummaryId;
    }

    public int getProjectSummaryOrigId() {
        return projectSummaryOrigId;
    }

    public void setProjectSummaryOrigId(int projectSummaryOrigId) {
        this.projectSummaryOrigId = projectSummaryOrigId;
    }

    public int getProjectSummaryAdded() {
        return projectSummaryAdded;
    }

    public void setProjectSummaryAdded(int projectSummaryAdded) {
        this.projectSummaryAdded = projectSummaryAdded;
    }

    public int getProjectSummaryDeleted() {
        return projectSummaryDeleted;
    }

    public void setProjectSummaryDeleted(int projectSummaryDeleted) {
        this.projectSummaryDeleted = projectSummaryDeleted;
    }

    public int getProjectSummaryTotal() {
        return projectSummaryTotal;
    }

    public void setProjectSummaryTotal(int projectSummaryTotal) {
        this.projectSummaryTotal = projectSummaryTotal;
    }

    public Date getProjectSummarySince() {
        return projectSummarySince;
    }

    public void setProjectSummarySince(Date projectSummarySince) {
        this.projectSummarySince = projectSummarySince;
    }

    public Date getProjectSummaryUntil() {
        return projectSummaryUntil;
    }

    public void setProjectSummaryUntil(Date projectSummaryUntil) {
        this.projectSummaryUntil = projectSummaryUntil;
    }
    @NonNull
    @Override
    public String toString() {
        return "[id = " + projectSummaryId
                + ", origId = " + projectSummaryOrigId
                + ", added = " + projectSummaryAdded
                + ", deleted = " + projectSummaryDeleted
                + ", total = " + projectSummaryTotal
                + ", since = " + projectSummarySince
                + ", until = " + projectSummaryUntil +"]";
    }
}
