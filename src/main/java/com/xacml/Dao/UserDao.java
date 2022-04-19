package com.xacml.Dao;

import com.xacml.Entity.*;

import java.util.List;
import java.util.Map;

public interface UserDao {

    public int insertPolicySet(PolicySet policySet);

    public int insertPolicy(Policy policy);

    public void insertRule(Rule rule);

    public void insertConditions(Conditions conditions);

    public void insertRule_Con(Map map);

    public void insertAction(Action action);

    public void insertPolicyAction(Action action);

    public void insertRule_RAct(Map map);

    public void insertPolID_ActID(Map map);

    public void insertSubject(Subject subject);

    public void insertPolicySubject(Subject subject);

    public void insertRule_RSub(Map map);

    public void insertPolID_SubID(Map map);

    public void insertResource(Resource resource);

    public void insertPolicyResource(Resource resource);

    public void insertRule_RRes(Map map);

    public void insertPolID_ResID(Map map);

    public void insertEnvironment(Environment environment);

    public void insertPolicyEnvironment(Environment environment);

    public void insertRule_REnv(Map map);

    public void insertPolID_EnvID(Map map);

    public void insertPol_Rule(Map map);

    public void insertSet_Pol(Map map);

    public String getSetDesBySetID_g(String PolicySetID);

    public String getAlgBySetID_g(String PolicySetId);

    public List<String> queryPolIDBySetID_g(String PolicySetId);

    public Policy queryPolByID_g(String PolicyId);

    public List<String> queryRulIDByPolID_g(String PolicyId);

    public Rule queryRuleByID(String RuleID);

    public Subject querySubByID_g(String SubjectMatchID);

    public Resource queryResByID_g(String ResourceMatchID);

    public Action queryActByID_g(String ActionMatchID);

    public Environment queryEnvByID_g(String EnvironmentMatchID);

    public Subject queryRulSubByID_g(String SubjectMatchID);

    public Resource queryRulResByID_g(String ResourceMatchID);

    public Action queryRulActByID_g(String ActionMatchID);

    public Environment queryRulEnvByID_g(String EnvironmentMatchID);

    public List<String> getPolSubID_g(String PolicyId);

    public List<String> getPolResID_g(String PolicyId);

    public List<String> getPolActID_g(String PolicyId);

    public List<String> getPolEnvID_g(String PolicyId);

    public List<String> getRulSubID_g(String RuleId);

    public List<String> getRulResID_g(String RuleId);

    public List<String> getRulActID_g(String RuleId);

    public List<String> getRulEnvID_g(String RuleId);

    public List<String> queryFuncIDByRulID(String RuleId);

    public Conditions queryConByID(String FuncId);

}
