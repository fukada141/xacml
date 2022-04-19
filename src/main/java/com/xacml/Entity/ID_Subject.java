package com.xacml.Entity;

public class ID_Subject {
    private String ID;
    private String SubMatchID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSubMatchID() {
        return SubMatchID;
    }

    public void setSubMatchID(String subMatchID) {
        SubMatchID = subMatchID;
    }

    public ID_Subject(String ID, String subMatchID) {
        this.ID = ID;
        SubMatchID = subMatchID;
    }

    @Override
    public String toString() {
        return "ID_Subject{" +
                "ID='" + ID + '\'' +
                ", SubMatchID='" + SubMatchID + '\'' +
                '}';
    }
}
