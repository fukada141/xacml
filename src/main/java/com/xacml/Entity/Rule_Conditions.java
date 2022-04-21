package com.xacml.Entity;

public class Rule_Conditions {
    private String RuleID_fk;
    private String FuncID_fk;

    public Rule_Conditions(String ruleID_fk, String funcID_fk) {
        RuleID_fk = ruleID_fk;
        FuncID_fk = funcID_fk;
    }
}
