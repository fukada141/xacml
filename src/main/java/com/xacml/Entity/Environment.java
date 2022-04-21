package com.xacml.Entity;

public class Environment {
    private String EnvMatchID;
    private String AttV;
    private String AttDT;
    private String AttDesgID;
    private String AttDesgDT;
    public String getEnvMatchID() {
        return EnvMatchID;
    }

    public void setEnvMatchID(String envMatchID) {
        EnvMatchID = envMatchID;
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

    public Environment(String envMatchID, String attV, String attDT, String attDesgID, String attDesgDT) {
        EnvMatchID = envMatchID;
        AttV = attV;
        AttDT = attDT;
        AttDesgID = attDesgID;
        AttDesgDT = attDesgDT;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "EnvMatchID='" + EnvMatchID + '\'' +
                ", AttV='" + AttV + '\'' +
                ", AttDT='" + AttDT + '\'' +
                ", AttDesgID='" + AttDesgID + '\'' +
                ", AttDesgDT='" + AttDesgDT + '\'' +
                '}';
    }
}
