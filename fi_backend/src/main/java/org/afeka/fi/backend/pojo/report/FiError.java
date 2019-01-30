package org.afeka.fi.backend.pojo.report;

public class FiError {
    private FiErrorName fiErrorName;
    private String title;
    private String description;
    private String projectId;
    private String fiId;
    private String version;
    public FiErrorName getFiErrorName() {
        return fiErrorName;
    }

    public void setFiErrorName(FiErrorName fiErrorName) {
        this.fiErrorName = fiErrorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getFiId() {
        return fiId;
    }

    public void setFiId(String fiId) {
        this.fiId = fiId;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
