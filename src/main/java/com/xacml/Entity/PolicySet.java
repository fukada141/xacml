package com.xacml.Entity;

public class PolicySet {
    private String PolicySetID;
    private String PCAlgID;
    private String Description;

    @Override
    public String toString() {
        return "PolicySet{" +
                "PolicySetID='" + PolicySetID + '\'' +
                ", PCAlgID='" + PCAlgID + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    public String getPolicySetID() {
        return PolicySetID;
    }

    public void setPolicySetID(String policySetID) {
        PolicySetID = policySetID;
    }

    public String getPCAlgID() {
        return PCAlgID;
    }

    public void setPCAlgID(String PCAlgID) {
        this.PCAlgID = PCAlgID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public PolicySet(String policySetID, String PCAlgID, String description) {
        PolicySetID = policySetID;
        this.PCAlgID = PCAlgID;
        Description = description;
    }


}
