package org.afeka.fi.backend.pojo.ocr;


import javax.xml.bind.annotation.XmlAttribute;

public class Task {
     @XmlAttribute(name="registrationTime")
    private String registrationTime;
    @XmlAttribute(name="filesCount")

    private String filesCount;
    @XmlAttribute(name="credits")

    private String credits;
    @XmlAttribute(name="resultUrl")
    public String resultUrl;

  /*  public String getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getFilesCount() {
        return filesCount;
    }

    public void setFilesCount(String filesCount) {
        this.filesCount = filesCount;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getStatusChangeTime() {
        return statusChangeTime;
    }

    public void setStatusChangeTime(String statusChangeTime) {
        this.statusChangeTime = statusChangeTime;
    }

    public String getEstimatedProcessingTime() {
        return estimatedProcessingTime;
    }

    public void setEstimatedProcessingTime(String estimatedProcessingTime) {
        this.estimatedProcessingTime = estimatedProcessingTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }*/

  @XmlAttribute(name="statusChangeTime")

    private String statusChangeTime;
  @XmlAttribute(name="estimatedProcessingTime")

    private String estimatedProcessingTime;
    @XmlAttribute(name="id")

    public String id;
    @XmlAttribute(name="status")

    public String status;
/*
    @Override
    public String toString()
    {
        return "ClassPojo [registrationTime = "+registrationTime+", filesCount = "+filesCount+", credits = "+credits+", statusChangeTime = "+statusChangeTime+", estimatedProcessingTime = "+estimatedProcessingTime+", id = "+id+", status = "+status+"]";
    }
}*/
}