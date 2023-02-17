package com.evault.eb.entity;

import java.time.LocalDateTime;

public class AppPehchanDocView {
    private String incidentId;
    private String registrationNo;
    private String os;
    private String docType;
    private String version;
    private String docId;
    private String responseCode;
    private long fetchTimeStamp;
    private String dataRequired;
    private String year;
    private String event;
    private String officeId;
    private String newRegistrationNumber;
    private String oldEventValue;
    

	public String getOldEventValue() {
		return oldEventValue;
	}

	public void setOldEventValue(String oldEventValue) {
		this.oldEventValue = oldEventValue;
	}

	public String getNewRegistrationNumber() {
        return newRegistrationNumber;
    }

    public void setNewRegistrationNumber(String newRegistrationNumber) {
        this.newRegistrationNumber = newRegistrationNumber;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCOde) {
        this.responseCode = responseCOde;
    }

    public long getFetchTimeStamp() {
        return fetchTimeStamp;
    }

    public void setFetchTimeStamp(long l) {
        this.fetchTimeStamp = l;
    }

    public String getDataRequired() {
        return dataRequired;
    }

    public void setDataRequired(String dataRequired) {
        this.dataRequired = dataRequired;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }




}
