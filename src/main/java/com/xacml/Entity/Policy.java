package com.xacml.Entity;

public class Policy {
    private String PolicyID;
    private String RCAlgID;
    private String Description;

    public String getPolicyID() {
        return PolicyID;
    }

    public void setPolicyID(String policyID) {
        PolicyID = policyID;
    }

    public String getRCAlgID() {
        return RCAlgID;
    }

    public void setRCAlgID(String RCAlgID) {
        this.RCAlgID = RCAlgID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "PolicyID='" + PolicyID + '\'' +
                ", RCAlgID='" + RCAlgID + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    public Policy(String policyID, String RCAlgID, String description) {
        PolicyID = policyID;
        RCAlgID = RCAlgID;
        Description = description;
    }
}
