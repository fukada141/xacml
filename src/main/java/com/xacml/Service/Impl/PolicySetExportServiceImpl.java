package com.xacml.Service.Impl;

import com.xacml.Dao.UserDao;
import com.xacml.Entity.*;
import com.xacml.Service.PolicySetExportService;
import org.apache.ibatis.javassist.runtime.Desc;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.jboss.security.xacml.jaxb.PolicyType;
import org.jboss.security.xacml.sunxacml.Target;
import org.jboss.security.xacml.sunxacml.attr.AttributeValue;
import org.jboss.security.xacml.sunxacml.combine.RuleCombiningAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Attr;
import sun.security.jca.GetInstance;
import sun.security.krb5.internal.crypto.Des;


import javax.print.Doc;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class PolicySetExportServiceImpl implements PolicySetExportService {

    @Autowired
    private UserDao userDao;

    @Override
    public String ExportFile(String PolicySetID) throws IOException, TransformerConfigurationException {
            Document document = DocumentHelper.createDocument();
            Element PolicySet = document.addElement("PolicySet");
            String SetDes = userDao.getSetDesBySetID_g(PolicySetID);
            String PolCmbAlg = userDao.getAlgBySetID_g(PolicySetID);
            System.out.println("PolicySetId");
            System.out.println(PolicySetID);
            System.out.println("SetDes");
            System.out.println(SetDes);
            PolicySet.addAttribute("PolicyCombiningAlgId",PolCmbAlg);
            PolicySet.addAttribute("PolicySetId",PolicySetID);
            Element Description_Set = PolicySet.addElement("Description");
            if(SetDes!=null) {Description_Set.addText(SetDes);}
            Element Target_Set = PolicySet.addElement("Target");
            List<String> PolIDList = userDao.queryPolIDBySetID_g(PolicySetID);
            List<Policy> policies = new ArrayList<>();
            for(int pol_num_=0;pol_num_<PolIDList.size();pol_num_++)
            {
                Policy policy_temp = userDao.queryPolByID_g(PolIDList.get(pol_num_));
                policies.add(policy_temp);
            }
//            List<Policy> policies = userDao.queryPolByID(userDao.queryPolIDBySetID(PolicySetId));
            for(int pol_num=0;pol_num<policies.size();pol_num++){ //policies length replace pol_num
                Element Policy = PolicySet.addElement("Policy");
                String PolicyIdTmp = policies.get(pol_num).getPolicyID();
                String RuleAlgTmp = policies.get(pol_num).getRCAlgID();
                String DesTmp = policies.get(pol_num).getDescription();
                Policy.addAttribute("PolicyId",PolicyIdTmp);
                Policy.addAttribute("RuleCombiningAlgId",RuleAlgTmp);
                Element Description_Pol = Policy.addElement("Description");
                if(DesTmp!=null){
                Description_Pol.addText(DesTmp);}
                Element Target_Pol = Policy.addElement("Target");

                List<String> PolSubTmp = userDao.getPolSubID_g(PolicyIdTmp);
                List<String> PolResTmp = userDao.getPolResID_g(PolicyIdTmp);
                List<String> PolActTmp = userDao.getPolActID_g(PolicyIdTmp);
                List<String> PolEnvTmp = userDao.getPolEnvID_g(PolicyIdTmp);
                if(PolSubTmp.size()!=0){ //if has subjects
                    Element Subjects = Target_Pol.addElement("Subjects");
                    Element Subject = Subjects.addElement("Subject");
//                    List<com.xacml.Entity.Subject>subjects =
                    for(int pol_sub_num=0;pol_sub_num<PolSubTmp.size();pol_sub_num++){//num of subs
                        com.xacml.Entity.Subject subject_tmp = userDao.querySubByID_g(PolSubTmp.get(pol_sub_num));
                        Element SubjectMatch = Subject.addElement("SubjectMatch");
                        SubjectMatch.addAttribute("MatchId",subject_tmp.getSubMatchID());
                        Element AttributeValue = SubjectMatch.addElement("AttributeValue");
                        AttributeValue.addAttribute("DataType",subject_tmp.getAttDT());
                        if(subject_tmp.getAttV()!=null){
                        AttributeValue.addText(subject_tmp.getAttV());}
                        Element SubjectAttributeDesignator = SubjectMatch.addElement("SubjectAttributeDesignator");
                        SubjectAttributeDesignator.addAttribute("AttributeId",subject_tmp.getAttDesgID());
                        SubjectAttributeDesignator.addAttribute("DataType",subject_tmp.getAttDesgDT());
                    }
                }

                if(PolResTmp.size()!=0){ //if has resources
                    Element Resources = Target_Pol.addElement("Resources");
                    Element Resource = Resources.addElement("Resource");
                    for(int pol_res_num=0;pol_res_num<PolResTmp.size();pol_res_num++){//num of subs
                        com.xacml.Entity.Resource resource_tmp = userDao.queryResByID_g(PolResTmp.get(pol_res_num));
                        Element ResourceMatch = Resource.addElement("ResourceMatch");
                        ResourceMatch.addAttribute("MatchId",resource_tmp.getResMatchID());
                        Element AttributeValue = ResourceMatch.addElement("AttributeValue");
                        AttributeValue.addAttribute("DataType",resource_tmp.getAttDT());
                        if(resource_tmp.getAttV()!=null){
                        AttributeValue.addText(resource_tmp.getAttV());}
                        Element ResourceAttributeDesignator = ResourceMatch.addElement("ResourceAttributeDesignator");
                        ResourceAttributeDesignator.addAttribute("AttributeId",resource_tmp.getAttDesgID());
                        ResourceAttributeDesignator.addAttribute("DataType",resource_tmp.getAttDesgDT());
                    }
                }

                if(PolEnvTmp.size()!=0){ //if has resources
                    Element Environments = Target_Pol.addElement("Environments");
                    Element Environment = Environments.addElement("Environment");
                    for(int pol_env_num=0;pol_env_num<PolEnvTmp.size();pol_env_num++){//num of subs
                        com.xacml.Entity.Environment environment_tmp = userDao.queryEnvByID_g(PolEnvTmp.get(pol_env_num));
                        Element EnvironmentMatch = Environment.addElement("EnvironmentMatch");
                        EnvironmentMatch.addAttribute("MatchId",environment_tmp.getEnvMatchID());
                        Element AttributeValue = EnvironmentMatch.addElement("AttributeValue");
                        AttributeValue.addAttribute("DataType",environment_tmp.getAttDT());
                        if(environment_tmp.getAttV()!=null){
                        AttributeValue.addText(environment_tmp.getAttV());}
                        Element EnvironmentAttributeDesignator = EnvironmentMatch.addElement("EnvironmentAttributeDesignator");
                        EnvironmentAttributeDesignator.addAttribute("AttributeId",environment_tmp.getAttDesgID());
                        EnvironmentAttributeDesignator.addAttribute("DataType",environment_tmp.getAttDesgDT());
                    }
                }

                if(PolActTmp.size()!=0){ //if has resources
                    Element Actions = Target_Pol.addElement("Actions");
                    Element Action = Actions.addElement("Action");
                    for(int pol_act_num=0;pol_act_num<PolActTmp.size();pol_act_num++){//num of subs
                        com.xacml.Entity.Action action_tmp = userDao.queryActByID_g(PolActTmp.get(pol_act_num));
                        Element ActionMatch = Action.addElement("ActionMatch");
                        ActionMatch.addAttribute("MatchId",action_tmp.getActionMatchID());
                        Element AttributeValue = ActionMatch.addElement("AttributeValue");
                        AttributeValue.addAttribute("DataType",action_tmp.getAttDT());
                        if(action_tmp.getAttV()!=null){
                        AttributeValue.addText(action_tmp.getAttV());}
                        Element ActionAttributeDesignator = ActionMatch.addElement("ActionAttributeDesignator");
                        ActionAttributeDesignator.addAttribute("AttributeId",action_tmp.getAttDesgID());
                        ActionAttributeDesignator.addAttribute("DataType",action_tmp.getAttDesgDT());
                    }
                }

                List<String> RuleIdTmps = userDao.queryRulIDByPolID_g(PolicyIdTmp);
                List<com.xacml.Entity.Rule> rules = new ArrayList<>();

                for(int rul_num_=0;rul_num_<RuleIdTmps.size();rul_num_++){
                    Rule rule = userDao.queryRuleByID(RuleIdTmps.get(rul_num_));
                    rules.add(rule);
                }
                for(int rul_num=0;rul_num<RuleIdTmps.size();rul_num++) {
                    Element Rule = Policy.addElement("Rule");
                    String RuleIdTmp = RuleIdTmps.get(rul_num);
                    Rule.addAttribute("Effect", rules.get(rul_num).getEffect());
                    Rule.addAttribute("RuleId", rules.get(rul_num).getRuleID());
                    Element Description_Rul = Rule.addElement("Description");
                    Description_Rul.addText(rules.get(rul_num).getDescription());
                    Element Target_Rul = Rule.addElement("Target");

                    List<String> RulSubTmp = userDao.getRulSubID_g(RuleIdTmp);
                    List<String> RulResTmp = userDao.getRulResID_g(RuleIdTmp);
                    List<String> RulActTmp = userDao.getRulActID_g(RuleIdTmp);
                    List<String> RulEnvTmp = userDao.getRulEnvID_g(RuleIdTmp);

                    if(RulSubTmp.size()!=0){ //if has subjects
                        Element Subjects = Target_Rul.addElement("Subjects");
                        Element Subject = Subjects.addElement("Subject");
                        for(int rul_sub_num=0;rul_sub_num<RulSubTmp.size();rul_sub_num++){//num of subs
                            com.xacml.Entity.Subject rul_subject_tmp = userDao.queryRulSubByID_g(PolSubTmp.get(rul_sub_num));
                            Element SubjectMatch = Subject.addElement("SubjectMatch");
                            SubjectMatch.addAttribute("MatchId",rul_subject_tmp.getSubMatchID());
                            Element AttributeValue = SubjectMatch.addElement("AttributeValue");
                            AttributeValue.addAttribute("DataType",rul_subject_tmp.getAttDT());
                            if(rul_subject_tmp.getAttV()!=null){
                                AttributeValue.addText(rul_subject_tmp.getAttV());}
                            Element SubjectAttributeDesignator = SubjectMatch.addElement("SubjectAttributeDesignator");
                            SubjectAttributeDesignator.addAttribute("AttributeId",rul_subject_tmp.getAttDesgID());
                            SubjectAttributeDesignator.addAttribute("DataType",rul_subject_tmp.getAttDesgDT());
                        }
                    }

                    if(RulResTmp.size()!=0){ //if has resources
                        Element Resources = Target_Rul.addElement("Resources");
                        Element Resource = Resources.addElement("Resource");
                        for(int rul_res_num=0;rul_res_num<RulResTmp.size();rul_res_num++){//num of subs
                            com.xacml.Entity.Resource rul_resource_tmp = userDao.queryRulResByID_g(RulResTmp.get(rul_res_num));
                            Element ResourceMatch = Resource.addElement("ResourceMatch");
                            ResourceMatch.addAttribute("MatchId",rul_resource_tmp.getResMatchID());
                            Element AttributeValue = ResourceMatch.addElement("AttributeValue");
                            AttributeValue.addAttribute("DataType",rul_resource_tmp.getAttDT());
                            if(rul_resource_tmp.getAttV()!=null){
                                AttributeValue.addText(rul_resource_tmp.getAttV());}
                            Element ResourceAttributeDesignator = ResourceMatch.addElement("ResourceAttributeDesignator");
                            ResourceAttributeDesignator.addAttribute("AttributeId",rul_resource_tmp.getAttDesgID());
                            ResourceAttributeDesignator.addAttribute("DataType",rul_resource_tmp.getAttDesgDT());
                        }
                    }

                    if(RulEnvTmp.size()!=0){ //if has resources
                        Element Environments = Target_Rul.addElement("Environments");
                        Element Environment = Environments.addElement("Environment");
                        for(int rul_env_num=0;rul_env_num<RulEnvTmp.size();rul_env_num++){//num of subs
                            com.xacml.Entity.Environment rul_environment_tmp = userDao.queryRulEnvByID_g(RulEnvTmp.get(rul_env_num));
                            Element EnvironmentMatch = Environment.addElement("EnvironmentMatch");
                            EnvironmentMatch.addAttribute("MatchId",rul_environment_tmp.getEnvMatchID());
                            Element AttributeValue = EnvironmentMatch.addElement("AttributeValue");
                            AttributeValue.addAttribute("DataType",rul_environment_tmp.getAttDT());
                            if(rul_environment_tmp.getAttV()!=null){
                                AttributeValue.addText(rul_environment_tmp.getAttV());}
                            Element EnvironmentAttributeDesignator = EnvironmentMatch.addElement("EnvironmentAttributeDesignator");
                            EnvironmentAttributeDesignator.addAttribute("AttributeId",rul_environment_tmp.getAttDesgID());
                            EnvironmentAttributeDesignator.addAttribute("DataType",rul_environment_tmp.getAttDesgDT());
                        }
                    }

                    if(RulActTmp.size()!=0){ //if has resources
                        Element Actions = Target_Rul.addElement("Actions");
                        Element Action = Actions.addElement("Action");
                        for(int rul_act_num=0;rul_act_num<PolActTmp.size();rul_act_num++){//num of subs
                            com.xacml.Entity.Action rul_action_tmp = userDao.queryRulActByID_g(RulActTmp.get(rul_act_num));
                            Element ActionMatch = Action.addElement("ActionMatch");
                            ActionMatch.addAttribute("MatchId",rul_action_tmp.getActionMatchID());
                            Element AttributeValue = ActionMatch.addElement("AttributeValue");
                            AttributeValue.addAttribute("DataType",rul_action_tmp.getAttDT());
                            if(rul_action_tmp.getAttV()!=null){
                                AttributeValue.addText(rul_action_tmp.getAttV());}
                            Element ActionAttributeDesignator = ActionMatch.addElement("ActionAttributeDesignator");
                            ActionAttributeDesignator.addAttribute("AttributeId",rul_action_tmp.getAttDesgID());
                            ActionAttributeDesignator.addAttribute("DataType",rul_action_tmp.getAttDesgDT());
                        }
                    }
                    Element Condition = Rule.addElement("Condition");
                    List<String> FuncIdTmp = userDao.queryFuncIDByRulID(RuleIdTmp);
                    List<Conditions> conditionsList = new ArrayList<>();
                    for(int a=0;a<FuncIdTmp.size();a++){
                        Conditions condition = userDao.queryConByID(FuncIdTmp.get(a));
                        conditionsList.add(condition);
                    }
                    System.out.println("ConditionlIST:");
                    System.out.println(conditionsList);
//                    Conditions condition = userDao.queryConByID();
                    for(int apply_num=0;apply_num<conditionsList.size();apply_num++){
                        if(conditionsList.get(apply_num).getAttSeleDT()==null){
                            Element apply_tmp = Condition.addElement("Apply");
                            apply_tmp.addAttribute("FunctionId",conditionsList.get(apply_num).getFuncID());
                            Condition = apply_tmp;
                        }
                        else{
                            Element apply_tmp1 = Condition.addElement("Apply");
                            apply_tmp1.addAttribute("FunctionId",conditionsList.get(apply_num).getFuncID());
                            Element SubjectAttributeDesignator = apply_tmp1.addElement("SubjectAttributeDesignator");
                            SubjectAttributeDesignator.addAttribute("AttributeId",conditionsList.get(apply_num).getSAttDesgID());
                            SubjectAttributeDesignator.addAttribute("DataType",conditionsList.get(apply_num).getSAttDesgDT());
                            Element apply_tmp2 = Condition.addElement("Apply");
                            apply_tmp2.addAttribute("FunctionId",conditionsList.get(apply_num).getFuncID());
                            Element AttributeSelector = apply_tmp2.addElement("AttributeSelector");
                            AttributeSelector.addAttribute("DataType",conditionsList.get(apply_num).getAttSeleDT());
                            AttributeSelector.addAttribute("RequestContextPath",conditionsList.get(apply_num).getRCPath());
                            break;
                        }
                    }
                }
            }

//        Element Condition = Rule.addElement("Condition");
//
//            OutputFormat outputFormat = OutputFormat.createPrettyPrint();
//            outputFormat.setEncoding("UTF-8");
//            outputFormat.setIndentSize(4);
//            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(new File()), outputFormat);
//            xmlWriter.write(PolicySet);
//            xmlWriter.close();

//        Element Policy = DocumentHelper.createElement("Policy");
//        Policy.appendContent(Rule);
        System.out.println(PolicySet.asXML());
//        documentCondition.getRootElement().appendContent(Rule)
         //创建TransformerFactory对象
//        TransformerFactory tff = TransformerFactory.newInstance();
//        // 创建Transformer对象
//        Transformer tf = tff.newTransformer();
//        // 设置输出数据时换行
//        tf.setOutputProperty(OutputKeys.INDENT, "yes");
//        tf.transform(new DOMSource(document), new StreamResult(new File("xacmltest.xml")));
        return PolicySet.asXML();
        }


    public static void main(String[] args) throws IOException {
        PolicySetExportServiceImpl policySetExportService = new PolicySetExportServiceImpl();
//        policySetExportService.ExportFile("pls-0001");
        List<String> a = new ArrayList(){
            {
                add("pol-0001");
                add("pol-0002");
                add("1");
            }
        };
        System.out.println(a.get(0));

    }

}
