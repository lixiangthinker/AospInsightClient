package com.tonybuilder.aospinsight.model;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Project {
    @PrimaryKey
    private int projectId;

    @ColumnInfo(name = "project_name")
    private String projectName;

    @ColumnInfo(name = "project_table_name")
    private String projectTableName;

    @ColumnInfo(name = "project_total_lines")
    private Double projectTotalLines;

    @ColumnInfo(name = "project_last_submit_date")
    private Date projectLastSubmitDate;

    @ColumnInfo(name = "project_is_external_src")
    private Byte projectIsExternalSrc;

    @ColumnInfo(name = "project_module_type")
    private int projectModuleType;

    @ColumnInfo(name = "project_is_discarded")
    private Byte projectIsDiscarded;

    @ColumnInfo(name = "project_path")
    private String projectPath;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectTableName() {
        return projectTableName;
    }

    public void setProjectTableName(String projectTableName) {
        this.projectTableName = projectTableName;
    }

    public Double getProjectTotalLines() {
        return projectTotalLines;
    }

    public void setProjectTotalLines(Double projectTotalLines) {
        this.projectTotalLines = projectTotalLines;
    }

    public Date getProjectLastSubmitDate() {
        return projectLastSubmitDate;
    }

    public void setProjectLastSubmitDate(Date projectLastSubmitDate) {
        this.projectLastSubmitDate = projectLastSubmitDate;
    }

    public Byte getProjectIsExternalSrc() {
        return projectIsExternalSrc;
    }

    public void setProjectIsExternalSrc(Byte projectIsExternalSrc) {
        this.projectIsExternalSrc = projectIsExternalSrc;
    }

    public int getProjectModuleType() {
        return projectModuleType;
    }

    public void setProjectModuleType(int projectModuleType) {
        this.projectModuleType = projectModuleType;
    }

    public Byte getProjectIsDiscarded() {
        return projectIsDiscarded;
    }

    public void setProjectIsDiscarded(Byte projectIsDiscarded) {
        this.projectIsDiscarded = projectIsDiscarded;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
