package com.xacml.Entity;

public class Rule {
    private String RuleID;
    private String Effect;
    private String Description;

    @Override
    public String toString() {
        return "Rule{" +
                "RuleID='" + RuleID + '\'' +
                ", Effect='" + Effect + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

    public String getRuleID() {
        return RuleID;
    }

    public void setRuleID(String ruleID) {
        RuleID = ruleID;
    }

    public String getEffect() {
        return Effect;
    }

    public void setEffect(String effect) {
        Effect = effect;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Rule(String ruleID, String effect, String description) {
        RuleID = ruleID;
        Effect = effect;
        Description = description;
    }
}
