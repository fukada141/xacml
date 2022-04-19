package com.xacml.Entity;

public class Action {
    private String ActionMatchID;
    private String AttV;
    private String AttDT;
    private String AttDesgID;
    private String AttDesgDT;

    public String getActionMatchID() {
        return ActionMatchID;
    }

    public void setActionMatchID(String actionMatchID) {
        ActionMatchID = actionMatchID;
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

    public Action(String actionMatchID, String attV, String attDT, String attDesgID, String attDesgDT) {
        ActionMatchID = actionMatchID;
        AttV = attV;
        AttDT = attDT;
        AttDesgID = attDesgID;
        AttDesgDT = attDesgDT;
    }

    @Override
    public String toString() {
        return "Action{" +
                "ActionMatchID='" + ActionMatchID + '\'' +
                ", AttV='" + AttV + '\'' +
                ", AttDT='" + AttDT + '\'' +
                ", AttDesgID='" + AttDesgID + '\'' +
                ", AttDesgDT='" + AttDesgDT + '\'' +
                '}';
    }
}
