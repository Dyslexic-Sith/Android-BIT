package com.project.sam.bitservices;

/**
 * Created by samuel.coianiz1 on 19/05/2017.
 */
public class DatabaseJob {

    //region Field Members
    private int id;
    private int clientId;
    private int contractorId;
    private int skillId;
    private String dateCreated;
    private String dateDue;
    private String dateCompleted;
    private String jobDescription;
    private int kms;
    private String status;
//endregion

    //region Getters and Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClientId() {
        return clientId;
    }

    public int getContractorId() {
        return contractorId;
    }

    public int getId() {
        return id;
    }

    public int getKms() {
        return kms;
    }

    public int getSkillId() {
        return skillId;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getDateDue() {
        return dateDue;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setContractorId(int contractorId) {
        this.contractorId = contractorId;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void setKms(int kms) {
        this.kms = kms;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }
    //endregion

    //region Constructors
    public DatabaseJob() {

    }


    public DatabaseJob(int id, String dateCreated, String dateDue, String dateCompleted, int skillId, int contractorId, int clientId, String jobDescription, int kms, String status) {
        this.id = id;
        this.clientId = clientId;
        this.contractorId = contractorId;
        this.skillId = skillId;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.dateCompleted = dateCompleted;
        this.jobDescription = jobDescription;
        this.kms = kms;
        this.status = status;
    }

    public DatabaseJob(String dateCreated, String dateDue, String dateCompleted, int skillId, int contractorId, int clientId, String jobDescription, int kms, String status) {
        this.clientId = clientId;
        this.contractorId = contractorId;
        this.skillId = skillId;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.dateCompleted = dateCompleted;
        this.jobDescription = jobDescription;
        this.kms = kms;
        this.status = status;
    }

    public DatabaseJob(int id, String dateCreated, String dateDue, String dateCompleted, int skillId, int contractorId, int clientId, String jobDescription, int kms) {
        this.id = id;
        this.clientId = clientId;
        this.contractorId = contractorId;
        this.skillId = skillId;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.dateCompleted = dateCompleted;
        this.jobDescription = jobDescription;
        this.kms = kms;
        status = "Submitted";
    }

    public DatabaseJob(String dateCreated, String dateDue, String dateCompleted, int skillId, int contractorId, int clientId, String jobDescription, int kms) {
        this.clientId = clientId;
        this.contractorId = contractorId;
        this.skillId = skillId;
        this.dateCreated = dateCreated;
        this.dateDue = dateDue;
        this.dateCompleted = dateCompleted;
        this.jobDescription = jobDescription;
        this.kms = kms;
        status = "Submitted";
    }
    //endregion

}
