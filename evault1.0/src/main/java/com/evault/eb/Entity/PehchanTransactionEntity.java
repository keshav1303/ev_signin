package com.evault.eb.Entity;

public class PehchanTransactionEntity {
    private String INCIDENTID;
    private String REGISTRATIONNUMBER;
    private String OS;
    private String DOCTYPE;
    private String VERSION;
    private String DOCID;
    private String RESPONSECODE;
    private long FETCHTIMESTAMP;
    private String DATAREQUIRED;

    public String getINCIDENTID() {
        return INCIDENTID;
    }

    public void setINCIDENTID(String INCIDENTID) {
        this.INCIDENTID = INCIDENTID;
    }

    public String getREGISTRATIONNUMBER() {
        return REGISTRATIONNUMBER;
    }

    public void setREGISTRATIONNUMBER(String REGISTRATIONNUMBER) {
        this.REGISTRATIONNUMBER = REGISTRATIONNUMBER;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public String getDOCTYPE() {
        return DOCTYPE;
    }

    public void setDOCTYPE(String DOCTYPE) {
        this.DOCTYPE = DOCTYPE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    public String getDOCID() {
        return DOCID;
    }

    public void setDOCID(String DOCID) {
        this.DOCID = DOCID;
    }

    public String getRESPONSECODE() {
        return RESPONSECODE;
    }

    public void setRESPONSECODE(String RESPONSECODE) {
        this.RESPONSECODE = RESPONSECODE;
    }

    public long getFETCHTIMESTAMP() {
        return FETCHTIMESTAMP;
    }

    public void setFETCHTIMESTAMP(long FETCHTIMESTAMP) {
        this.FETCHTIMESTAMP = FETCHTIMESTAMP;
    }

    public String getDATAREQUIRED() {
        return DATAREQUIRED;
    }

    public void setDATAREQUIRED(String DATAREQUIRED) {
        this.DATAREQUIRED = DATAREQUIRED;
    }

    @Override
    public String toString() {
        return "PehchanTransactionEntity{" +
                "INCIDENTID='" + INCIDENTID + '\'' +
                ", REGISTRATIONNUMBER='" + REGISTRATIONNUMBER + '\'' +
                ", OS='" + OS + '\'' +
                ", DOCTYPE='" + DOCTYPE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                ", DOCID='" + DOCID + '\'' +
                ", RESPONSECODE='" + RESPONSECODE + '\'' +
                ", FETCHTIMESTAMP=" + FETCHTIMESTAMP +
                ", DATAREQUIRED='" + DATAREQUIRED + '\'' +
                '}';
    }
}
