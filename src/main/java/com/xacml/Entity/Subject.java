package com.xacml.Entity;

public class Subject {
    private String SubMatchID;
    private String AttV;
    private String AttDT;
    private String AttDesgID;
    private String AttDesgDT;

    public String getSubMatchID() {
        return SubMatchID;
    }

    public void setSubMatchID(String subMatchID) {
        SubMatchID = subMatchID;
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

    public Subject(String subMatchID, String attV, String attDT, String attDesgID, String attDesgDT) {
        SubMatchID = subMatchID;
        AttV = attV;
        AttDT = attDT;
        AttDesgID = attDesgID;
        AttDesgDT = attDesgDT;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "SubMatchID='" + SubMatchID + '\'' +
                ", AttV='" + AttV + '\'' +
                ", AttDT='" + AttDT + '\'' +
                ", AttDesgID='" + AttDesgID + '\'' +
                ", AttDesgDT='" + AttDesgDT + '\'' +
                '}';
    }
}
