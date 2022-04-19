package com.xacml.Entity;

public class Resource {
    private String ResMatchID;
    private String AttV;
    private String AttDT;
    private String AttDesgID;
    private String AttDesgDT;

    public String getResMatchID() {
        return ResMatchID;
    }

    public void setResMatchID(String resMatchID) {
        ResMatchID = resMatchID;
    }

    public String getAttV() {
        return AttV;
    }

    public void setAttV(String attV) {
        AttV = attV;
    }

    public String getAttDT() {
        return AttDT;
    }

    public void setAttDT(String attDT) {
        AttDT = attDT;
    }

    public String getAttDesgID() {
        return AttDesgID;
    }

    public void setAttDesgID(String attDesgID) {
        AttDesgID = attDesgID;
    }

    public String getAttDesgDT() {
        return AttDesgDT;
    }

    public void setAttDesgDT(String attDesgDT) {
        AttDesgDT = attDesgDT;
    }

    public Resource(String resMatchID, String attV, String attDT, String attDesgID, String attDesgDT) {
        ResMatchID = resMatchID;
        AttV = attV;
        AttDT = attDT;
        AttDesgID = attDesgID;
        AttDesgDT = attDesgDT;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "ResMatchID='" + ResMatchID + '\'' +
                ", AttV='" + AttV + '\'' +
                ", AttDT='" + AttDT + '\'' +
                ", AttDesgID='" + AttDesgID + '\'' +
                ", AttDesgDT='" + AttDesgDT + '\'' +
                '}';
    }
}
