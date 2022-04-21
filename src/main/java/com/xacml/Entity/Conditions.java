package com.xacml.Entity;

public class Conditions {
    private String FuncID;
    private String SAttDesgDT;
    private String SAttDesgID;
    private String RCPath;
    private String AttSeleDT;

    public Conditions(String funcID, String SAttDesgDT, String SAttDesgID, String RCPath, String attSeleDT) {
        FuncID = funcID;
        this.SAttDesgDT = SAttDesgDT;
        this.SAttDesgID = SAttDesgID;
        this.RCPath = RCPath;
        AttSeleDT = attSeleDT;
    }

    public String getFuncID() {
        return FuncID;
    }

    public void setFuncID(String funcID) {
        FuncID = funcID;
    }

    public String getSAttDesgDT() {
        return SAttDesgDT;
    }

    public void setSAttDesgDT(String SAttDesgDT) {
        this.SAttDesgDT = SAttDesgDT;
    }

    public String getSAttDesgID() {
        return SAttDesgID;
    }

    public void setSAttDesgID(String SAttDesgID) {
        this.SAttDesgID = SAttDesgID;
    }

    public String getRCPath() {
        return RCPath;
    }

    public void setRCPath(String RCPath) {
        this.RCPath = RCPath;
    }

    public String getAttSeleDT() {
        return AttSeleDT;
    }

    public void setAttSeleDT(String attSeleDT) {
        AttSeleDT = attSeleDT;
    }

    @Override
    public String toString() {
        return "Conditions{" +
                "FuncID='" + FuncID + '\'' +
                ", SAttDesgDT='" + SAttDesgDT + '\'' +
                ", SAttDesgID='" + SAttDesgID + '\'' +
                ", RCPath='" + RCPath + '\'' +
                ", AttSeleDT='" + AttSeleDT + '\'' +
                '}';
    }
}
