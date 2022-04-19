package com.xacml.Service.Impl;


import com.sun.tracing.dtrace.Attributes;
import com.xacml.Dao.UserDao;
import com.xacml.Entity.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//import static com.xacml.Service.Impl.RuleService.trim;

@Service
public class PolicySetServiceImpl implements com.xacml.Service.PolicySetService{


    XPath xPath =  XPathFactory.newInstance().newXPath();
    String expression = "/PolicySet/Policy/Rule/Condition/Apply";
    String exp_rule = "/PolicySet/Policy/Rule";
    String exp_policy= "/PolicySet/Policy";
    String exp_policyset= "/PolicySet";
    String exp_policy_target = "PolicySet/Policy/Target";
    String exp_policy_subject = "PolicySet/Policy/Target/Subjects";
    String exp_policy_resource = "PolicySet/Policy/Target/Resources";
    String exp_policy_environment = "PolicySet/Policy/Target/Environments";
    String exp_policy_action = "PolicySet/Policy/Target/Actions";
    String exp_rule_target = "PolicySet/Policy/Rule/Target";
    String exp_rule_subject = "PolicySet/Policy/Rule/Target/Subjects";
    String exp_rule_resource = "PolicySet/Policy/Rule/Target/Resources";
    String exp_rule_environment = "PolicySet/Policy/Rule/Target/Environments";
    String exp_rule_action = "PolicySet/Policy/Rule/Target/Actions";
    public static String trim(String input) {
        BufferedReader reader = new BufferedReader(new StringReader(input));
        StringBuffer result = new StringBuffer();
        try {
            String line;
            while ( (line = reader.readLine() ) != null)
                result.append(line.trim());
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //String FILENAME = "C:\\Users\\17238\\Documents\\Infobeyond Technology LLC\\Security Policy Tool\\Saved Files\\XACML Files\\XacmlTest.xml";
//    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//    InputStream is = new FileInputStream(FILENAME);
//    String result = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining(System.lineSeparator()));
//    String triim = trim(result);
//    InputStream is1 = IOUtils.toInputStream(triim, StandardCharsets.UTF_8);
//    DocumentBuilder db = dbf.newDocumentBuilder();
//    Document doc = db.parse(is1);

    @Autowired
    private UserDao userDao;

    @Override
    public void ParsePolicySetElement(Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        NodeList nodeList_PolicySet = (NodeList) xPath.compile(exp_policyset).evaluate(doc, XPathConstants.NODESET);

        for(int j=0;j<nodeList_PolicySet.getLength();j++){
            String PolicySetID = nodeList_PolicySet.item(j).getAttributes().getNamedItem("PolicySetId").getTextContent();
            String PCAlgID = nodeList_PolicySet.item(j).getAttributes().getNamedItem("PolicyCombiningAlgId").getTextContent();
            Element Description = (Element)nodeList_PolicySet.item(j);
            String description = Description.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();
            PolicySet policySet = new PolicySet(PolicySetID,PCAlgID,description);
            userDao.insertPolicySet(policySet);
            ParsePolicyElement(PolicySetID,doc);

//            System.out.println("PoliicySet:");
//            System.out.println(policySet);
        }
    }


    @Override
    public void ParsePolicyElement(String PolicySetID,Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {

        //插入policy
        NodeList nodeList_Policy = (NodeList) xPath.compile(exp_policy).evaluate(doc, XPathConstants.NODESET);

        for(int j=0;j<nodeList_Policy.getLength();j++){
            String PolicyID = nodeList_Policy.item(j).getAttributes().getNamedItem("PolicyId").getTextContent();
            String RCAlgID = nodeList_Policy.item(j).getAttributes().getNamedItem("RuleCombiningAlgId").getTextContent();
            Element Description = (Element)nodeList_Policy.item(j);
            String description = Description.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();
            Policy policy = new Policy(PolicyID,RCAlgID,description);
            Map<Object,Object> map = new HashMap<>();
            map.put("PolicySetID",PolicySetID);
            map.put("PolicyID",PolicyID);
//            System.out.println(map);
            userDao.insertPolicy(policy);
            userDao.insertSet_Pol(map);
            ParsePolicyTargetElement(PolicyID,j,doc);
            ParseRuleElement(PolicyID,doc);
            System.out.println(policy);

        }
    }
    @Override
    public void ParseRuleElement(String PolicyID,Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        NodeList nodeList_Rule = (NodeList) xPath.compile(exp_rule).evaluate(doc, XPathConstants.NODESET);

        for(int j=0;j<nodeList_Rule.getLength();j++){
            if(nodeList_Rule.item(j).getParentNode().getAttributes().getNamedItem("PolicyId").getTextContent()==PolicyID) {
                String RuleID1 = nodeList_Rule.item(j).getAttributes().getNamedItem("RuleId").getTextContent();
                String Effect = nodeList_Rule.item(j).getAttributes().getNamedItem("Effect").getTextContent();
                Element Description = (Element) nodeList_Rule.item(j);
                String description = Description.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();
                Rule rule = new Rule(RuleID1, Effect, description);
                Map<Object, Object> map = new HashMap<>();
                map.put("PolicyID", PolicyID);
                map.put("RuleID", RuleID1);
                System.out.println(map);
                userDao.insertRule(rule);
                userDao.insertPol_Rule(map);
                ParseRuleTargetElement(RuleID1, j,doc);
                ParseConditionElement(RuleID1,j,doc);
//                System.out.println(rule);
            }
//
        }
    }
    @Override
    public void ParseConditionElement(String RuleID,int i,Document doc) throws XPathExpressionException {
        try {
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
            Node nodeList1 = nodeList.item(i);
            while (nodeList1.hasChildNodes()) {
//                System.out.println(children.getLength());
                        if (nodeList1.getChildNodes().item(0).getNodeName() == "Apply") {
                            String FuncId = nodeList1.getAttributes().getNamedItem("FunctionId").getTextContent();
                            Conditions conditions = new Conditions(FuncId, null, null, null, null);
//                    System.out.println(conditions.getFuncID());
//                            System.out.println(conditions);
                            Map<Object,Object> map = new HashMap<>();
                            map.put("RuleID",RuleID);
                            map.put("FuncId",FuncId);
//                            System.out.println(map);
                            userDao.insertConditions(conditions);
                            userDao.insertRule_Con(map);
                            nodeList1 = nodeList1.getChildNodes().item(0);
                        } else {
                            Conditions conditions = new Conditions(null, null, null, null, null);
                            String FuncId = nodeList1.getAttributes().getNamedItem("FunctionId").getTextContent();
                            String SAttDesgDT = nodeList1.getChildNodes().item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                    System.out.println(SAttDesgDT);
                            String SAttDesgID = nodeList1.getChildNodes().item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                            String RCPath = nodeList1.getNextSibling().getChildNodes().item(0).getAttributes().getNamedItem("RequestContextPath").getTextContent();
                            String AttSeleDT = nodeList1.getNextSibling().getChildNodes().item(0).getAttributes().getNamedItem("DataType").getTextContent();
                            conditions.setFuncID(FuncId);
                            conditions.setSAttDesgDT(SAttDesgDT);
                            conditions.setSAttDesgID(SAttDesgID);
                            conditions.setRCPath(RCPath);
                            conditions.setAttSeleDT(AttSeleDT);
//
                            Map<Object,Object> map = new HashMap<>();
                            map.put("RuleID",RuleID);
                            map.put("FuncId",FuncId);
                            userDao.insertConditions(conditions);
                            System.out.println(map);
                            userDao.insertRule_Con(map);
                            System.out.println(conditions);

                            break;
                        }
                    }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void ParsePolicyTargetElement(String PolicyID,int j,Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        NodeList nodeList_policy_target = (NodeList) xPath.compile(exp_policy_target).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList = nodeList_policy_target.item(j).getChildNodes();

        for(int i=0;i<nodeList.getLength();i++){
            if(nodeList.item(i).getNodeName()=="Subjects"){
                ParsePolicySubject(PolicyID,doc);
            }
            if(nodeList.item(i).getNodeName()=="Resources"){
                ParsePolicyResource(PolicyID,doc);
            }
            if(nodeList.item(i).getNodeName()=="Actions"){
                ParsePolicyAction(PolicyID,doc);
            }
            if(nodeList.item(i).getNodeName()=="Environments"){
                ParsePolicyEnvironment(PolicyID,doc);
            }
        }
    }

    @Override
    public void ParseRuleTargetElement(String RuleID,int j,Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException {
        NodeList nodeList_policy_target = (NodeList) xPath.compile(exp_rule_target).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList = nodeList_policy_target.item(j).getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getNodeName() == "Subjects") {
                    ParseRuleSubject(RuleID,doc);
                }
                if (nodeList.item(i).getNodeName() == "Resources") {
                    ParseRuleResource(RuleID,doc);
                }
                if (nodeList.item(i).getNodeName() == "Actions") {
                    ParseRuleAction(RuleID,doc);
                }
                if (nodeList.item(i).getNodeName() == "Environments") {
                    ParseRuleEnvironment(RuleID,doc);
                }
            }
    }

    @Override
    public void ParsePolicySubject(String PolicyID,Document doc) throws XPathExpressionException {

        NodeList nodeList_Subjects = (NodeList) xPath.compile(exp_policy_subject).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList_Subject = nodeList_Subjects.item(0).getChildNodes();
        for(int c=0;c<nodeList_Subject.getLength();c++){
            Node node_temp = nodeList_Subject.item(c);
            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
                NodeList nodeList_SubjectMatchs = node_temp.getChildNodes();
                String SubjectMatchId = nodeList_SubjectMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
                Element SubjectMatchId_ele = (Element)nodeList_SubjectMatchs.item(b);
                String AttV = SubjectMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
                String AttDT = SubjectMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                String AttDesgID = SubjectMatchId_ele.getElementsByTagName("SubjectAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                String AttDesgDT = SubjectMatchId_ele.getElementsByTagName("SubjectAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                Subject subject1 = new Subject(SubjectMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
                Map<Object,Object> map = new HashMap<>();
                map.put("PolicyID",PolicyID);
                map.put("SubjectMatchId",SubjectMatchId);
                userDao.insertPolicySubject(subject1);
                userDao.insertPolID_SubID(map);
//                System.out.println(map);
                System.out.println(subject1);
//
            }
        }
    }

    @Override
    public void ParsePolicyResource(String PolicyID,Document doc) throws XPathExpressionException{

        NodeList nodeList_Resources = (NodeList) xPath.compile(exp_policy_resource).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList_Resource = nodeList_Resources.item(0).getChildNodes();
        for(int d=0;d<nodeList_Resource.getLength();d++){
            Node node_temp = nodeList_Resource.item(d);
            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
                NodeList nodeList_ResourceMatchs = node_temp.getChildNodes();
                String ResourceMatchId = nodeList_ResourceMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
                Element ResourceMatchId_ele = (Element)nodeList_ResourceMatchs.item(b);
                String AttV = ResourceMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
                String AttDT = ResourceMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                String AttDesgID = ResourceMatchId_ele.getElementsByTagName("ResourceAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                String AttDesgDT = ResourceMatchId_ele.getElementsByTagName("ResourceAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                Resource resource1 = new Resource(ResourceMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
                Map<Object,Object> map = new HashMap<>();
                map.put("PolicyID",PolicyID);
                map.put("ResourceMatchId",ResourceMatchId);
                userDao.insertPolicyResource(resource1);
                userDao.insertPolID_ResID(map);
//                System.out.println(map);
//                System.out.println(resource1);
//
            }
        }
    }

    @Override
    public void ParsePolicyEnvironment(String PolicyID,Document doc) throws XPathExpressionException{

        NodeList nodeList_Environments = (NodeList) xPath.compile(exp_policy_environment).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList_Environment= nodeList_Environments.item(0).getChildNodes();
        for(int d=0;d<nodeList_Environment.getLength();d++){
            Node node_temp = nodeList_Environment.item(d);
            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
                NodeList nodeList_EnvironmentMatchs = node_temp.getChildNodes();
                String EnvironmentMatchId = nodeList_EnvironmentMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
                Element EnvironmentMatchId_ele = (Element)nodeList_EnvironmentMatchs.item(b);
                String AttV = EnvironmentMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
                String AttDT = EnvironmentMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                String AttDesgID = EnvironmentMatchId_ele.getElementsByTagName("EnvironmentAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                String AttDesgDT = EnvironmentMatchId_ele.getElementsByTagName("EnvironmentAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                Environment environment= new Environment(EnvironmentMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
                Map<Object,Object> map = new HashMap<>();
                map.put("PolicyID",PolicyID);
                map.put("EnvironmentMatchId",EnvironmentMatchId);
                userDao.insertPolicyEnvironment(environment);
                userDao.insertPolID_EnvID(map);
//                System.out.println(map);
//                System.out.println(environment);
//
//
            }
        }
    }

    @Override
    public void ParsePolicyAction(String PolicyID,Document doc) throws XPathExpressionException{

        NodeList nodeList_Actions = (NodeList) xPath.compile(exp_policy_action).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList_Action = nodeList_Actions.item(0).getChildNodes();
        for(int a=0;a<nodeList_Action.getLength();a++){
            Node node_temp = nodeList_Action.item(a);
            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
                NodeList nodeList_ActionMatchs = node_temp.getChildNodes();
                String ActionMatchId = nodeList_ActionMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
                Element ActionMatchId_ele = (Element)nodeList_ActionMatchs.item(b);
                String AttV = ActionMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
                String AttDT = ActionMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                String AttDesgID = ActionMatchId_ele.getElementsByTagName("ActionAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                String AttDesgDT = ActionMatchId_ele.getElementsByTagName("ActionAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                Action action1 = new Action(ActionMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
                Map<Object,Object> map = new HashMap<>();
                map.put("PolicyID",PolicyID);
                map.put("ActionMatchId",ActionMatchId);
                userDao.insertPolicyAction(action1);
                userDao.insertPolID_ActID(map);
//                System.out.println(map);

//                System.out.println(action1);
//
            }
        }

    }
    @Override
    public void ParseRuleSubject(String RuleID,Document doc) throws XPathExpressionException {
        NodeList nodeList_Subjects = (NodeList) xPath.compile(exp_rule_subject).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList_Subject = nodeList_Subjects.item(0).getChildNodes();
        for(int c=0;c<nodeList_Subject.getLength();c++){
            Node node_temp = nodeList_Subject.item(c);
            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
                NodeList nodeList_SubjectMatchs = node_temp.getChildNodes();
                String SubjectMatchId = nodeList_SubjectMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
                Element SubjectMatchId_ele = (Element)nodeList_SubjectMatchs.item(b);
                String AttV = SubjectMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
                String AttDT = SubjectMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                String AttDesgID = SubjectMatchId_ele.getElementsByTagName("SubjectAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                String AttDesgDT = SubjectMatchId_ele.getElementsByTagName("SubjectAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                Subject subject1 = new Subject(SubjectMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
                Map<Object,Object> map = new HashMap<>();
                map.put("RuleID",RuleID);
                map.put("SubjectMatchId",SubjectMatchId);
//                System.out.println(map);
//                System.out.println(subject1);
                userDao.insertSubject(subject1);
                userDao.insertRule_RSub(map);
            }
        }
    }
    @Override
    public void ParseRuleResource(String RuleID,Document doc) throws XPathExpressionException{

        NodeList nodeList_Resources = (NodeList) xPath.compile(exp_rule_resource).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList_Resource = nodeList_Resources.item(0).getChildNodes();
        for(int d=0;d<nodeList_Resource.getLength();d++){
            Node node_temp = nodeList_Resource.item(d);
            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
                NodeList nodeList_ResourceMatchs = node_temp.getChildNodes();
                String ResourceMatchId = nodeList_ResourceMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
                Element ResourceMatchId_ele = (Element)nodeList_ResourceMatchs.item(b);
                String AttV = ResourceMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
                String AttDT = ResourceMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                String AttDesgID = ResourceMatchId_ele.getElementsByTagName("ResourceAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                String AttDesgDT = ResourceMatchId_ele.getElementsByTagName("ResourceAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                Resource resource1 = new Resource(ResourceMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
                Map<Object,Object> map = new HashMap<>();
                map.put("RuleID",RuleID);
                map.put("ResourceMatchId",ResourceMatchId);
//                System.out.println(map);
                System.out.println("resource");
                System.out.println(resource1);
                userDao.insertResource(resource1);
                userDao.insertRule_RRes(map);
            }
        }
    }
    @Override
    public void ParseRuleEnvironment(String RuleID,Document doc) throws XPathExpressionException{

        NodeList nodeList_Environments = (NodeList) xPath.compile(exp_rule_environment).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList_Environment= nodeList_Environments.item(0).getChildNodes();
        for(int d=0;d<nodeList_Environment.getLength();d++){
            Node node_temp = nodeList_Environment.item(d);
            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
                NodeList nodeList_EnvironmentMatchs = node_temp.getChildNodes();
                String EnvironmentMatchId = nodeList_EnvironmentMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
                Element EnvironmentMatchId_ele = (Element)nodeList_EnvironmentMatchs.item(b);
                String AttV = EnvironmentMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
                String AttDT = EnvironmentMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                String AttDesgID = EnvironmentMatchId_ele.getElementsByTagName("EnvironmentAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                String AttDesgDT = EnvironmentMatchId_ele.getElementsByTagName("EnvironmentAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                Environment environment= new Environment(EnvironmentMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
                Map<Object,Object> map = new HashMap<>();
                map.put("RuleID",RuleID);
                map.put("EnvironmentMatchId",EnvironmentMatchId);
//                System.out.println(map);
                userDao.insertEnvironment(environment);
                userDao.insertRule_REnv(map);
            }
        }
    }
    @Override
    public void ParseRuleAction(String RuleID,Document doc) throws XPathExpressionException{

        NodeList nodeList_Actions = (NodeList) xPath.compile(exp_rule_action).evaluate(doc, XPathConstants.NODESET);
        NodeList nodeList_Action = nodeList_Actions.item(0).getChildNodes();
        for(int a=0;a<nodeList_Action.getLength();a++){
            Node node_temp = nodeList_Action.item(a);
            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
                NodeList nodeList_ActionMatchs = node_temp.getChildNodes();
                String ActionMatchId = nodeList_ActionMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
                Element ActionMatchId_ele = (Element)nodeList_ActionMatchs.item(b);
                String AttV = ActionMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
                String AttDT = ActionMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                String AttDesgID = ActionMatchId_ele.getElementsByTagName("ActionAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
                String AttDesgDT = ActionMatchId_ele.getElementsByTagName("ActionAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
                Action action1 = new Action(ActionMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
                Map<Object,Object> map = new HashMap<>();
                map.put("RuleID",RuleID);
                map.put("ActionMatchId",ActionMatchId);
//                System.out.println(map);
//                System.out.println(action1);
                userDao.insertAction(action1);
                userDao.insertRule_RAct(map);
            }
        }

    }
    @Override
    public void ParsePolicySet(Document doc) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
        PolicySetServiceImpl policySetServiceImpl = new PolicySetServiceImpl();
        policySetServiceImpl.ParsePolicySetElement(doc);
//        userDao.insertResource(new Resource("b","a","a","a","a"));
    }

}
