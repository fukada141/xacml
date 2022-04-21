//package com.xacml.Service.Impl;
//
//import com.xacml.Dao.UserDao;
//import com.xacml.Entity.*;
//import org.apache.commons.io.IOUtils;
//import org.apache.ibatis.javassist.runtime.Desc;
//import org.omg.CORBA.NO_IMPLEMENT;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//import sun.security.krb5.internal.crypto.Des;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.xpath.XPath;
//import javax.xml.xpath.XPathConstants;
//import javax.xml.xpath.XPathExpressionException;
//import javax.xml.xpath.XPathFactory;
//import java.io.*;
//import java.nio.Buffer;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.logging.Level;
//import java.util.stream.Collectors;
//
//@Service
//public class RuleService implements com.xacml.Service.RuleService {
//
//    @Autowired
//    private static UserDao userDao;
//
//    private static final String FILENAME = "C:\\Users\\17238\\Documents\\Infobeyond Technology LLC\\Security Policy Tool\\Saved Files\\XACML Files\\XacmlTest.xml";
//
//    public static void formalize(Node root){
//        if(root.hasChildNodes()){
//            NodeList Children = root.getChildNodes();
//            int count = Children.getLength();
//            for(int i= count-1;i>=0;i--){
//                Node child = Children.item(i);
//                if(child.getNodeType()== Node.TEXT_NODE){
//                    child.setTextContent(child.getTextContent().trim());
//                }
//                else{
//                    formalize(child);
//                }
//            }
//        }
//    }
//    public static String trim(String input) {
//        BufferedReader reader = new BufferedReader(new StringReader(input));
//        StringBuffer result = new StringBuffer();
//        try {
//            String line;
//            while ( (line = reader.readLine() ) != null)
//                result.append(line.trim());
//            return result.toString();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    public void insertCondition(String filename) throws IOException, ParserConfigurationException, SAXException, TransformerException {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        InputStream is = new FileInputStream(filename);
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(is);
//        doc.getDocumentElement().normalize();
//        formalize(doc);
//        XPath xPath =  XPathFactory.newInstance().newXPath();
//        String expression = "/PolicySet/Policy/Rule/Condition/Apply";
//        String RuleID = doc.getElementsByTagName("Rule").item(0).getAttributes().getNamedItem("RuleId").getTextContent();
//        try {
//            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
//            System.out.println(nodeList.item(0).getAttributes().getNamedItem("FunctionId"));
//            while(nodeList.item(0).hasChildNodes()){
//                NodeList children = nodeList.item(0).getChildNodes();
//                int count = children.getLength();
//                for(int i= count-1;i>=0;i--){
//                    Node child = children.item(i);
//                    if(child.getNodeName()=="#text"){
//                        child.getParentNode().removeChild(child);
//                    }
//                }
//                System.out.println(children.getLength());
//                if(children.item(0).getChildNodes().item(0).getNodeName()=="Apply"){
//                    String FuncId = nodeList.item(0).getAttributes().getNamedItem("FunctionId").getTextContent();
//                    Conditions conditions = new Conditions(FuncId,null,null,null,null);
//                    Map<Object,Object> map = new HashMap<>();
//                    map.put("RuleID",RuleID);
//                    map.put("FuncId",FuncId);
//                    System.out.println(map);
////                    userDao.insertRule_Con(map);
//                    nodeList= (NodeList) nodeList.item(0).getChildNodes();
////                    userDao.insertConditions(conditions);
//                    nodeList= children;
//                }
//                else{
//                    Conditions conditions = new Conditions(null,null,null,null,null);
//                    String FuncId = children.item(0).getAttributes().getNamedItem("FunctionId").getTextContent();
//                    String SAttDesgDT = children.item(0).getChildNodes().item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
//                    System.out.println(SAttDesgDT);
//                    String SAttDesgID = children.item(0).getChildNodes().item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                    String RCPath = children.item(1).getChildNodes().item(0).getAttributes().getNamedItem("RequestContextPath").getTextContent();
//                    String AttSeleDT = children.item(1).getChildNodes().item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                    Conditions conditions1 = new Conditions(FuncId,SAttDesgDT,SAttDesgID,RCPath,AttSeleDT);
//                    Map<Object,Object> map = new HashMap<>();
//                    map.put("RuleID",RuleID);
//                    map.put("FuncId",FuncId);
//                    System.out.println(map);
////                    userDao.insertRule_Con(map);
////                    userDao.insertConditions(conditions1);
//                    break;
//                }
//            }
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void insertActions(String exp){
//
//    }
//
//
//    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//
//        InputStream is = new FileInputStream(FILENAME);
//        String result = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining(System.lineSeparator()));
//        String triim = trim(result);
//        InputStream is1 = IOUtils.toInputStream(triim, StandardCharsets.UTF_8);
//        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(is1);
////        doc.getDocumentElement().normalize();
////        formalize(doc);
//
//        XPath xPath =  XPathFactory.newInstance().newXPath();
//        String expression = "/PolicySet/Policy/Rule/Condition/Apply";
//        String exp_rule = "/PolicySet/Policy/Rule";
//        String exp_policy= "/PolicySet/Policy";
//        String exp_policyset= "/PolicySet";
//
//        String RuleID = doc.getElementsByTagName("Rule").item(0).getAttributes().getNamedItem("RuleId").getTextContent();
////        System.out.println(RuleID);
//
//        try {
//            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
////            System.out.println(nodeList.item(0).getAttributes().getNamedItem("FunctionId"));
//            while(nodeList.item(0).hasChildNodes()){
////                System.out.println(children.getLength());
//                if(nodeList.item(0).getChildNodes().item(0).getNodeName()=="Apply"){
//                    String FuncId = nodeList.item(0).getAttributes().getNamedItem("FunctionId").getTextContent();
//
//                    Conditions conditions = new Conditions(FuncId,null,null,null,null);
////                    System.out.println(conditions.getFuncID());
//                    Map<Object,Object> map = new HashMap<>();
//                    map.put("RuleID",RuleID);
//                    map.put("FuncId",FuncId);
//                    System.out.println(conditions);
////                    userDao.insertRule_Con(map);
//                    nodeList= (NodeList) nodeList.item(0).getChildNodes();
//                }
//                else{
//                    Conditions conditions = new Conditions(null,null,null,null,null);
//                    String FuncId = nodeList.item(0).getAttributes().getNamedItem("FunctionId").getTextContent();
//                    String SAttDesgDT = nodeList.item(0).getChildNodes().item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
////                    System.out.println(SAttDesgDT);
//                    String SAttDesgID = nodeList.item(0).getChildNodes().item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                    String RCPath = nodeList.item(1).getChildNodes().item(0).getAttributes().getNamedItem("RequestContextPath").getTextContent();
//                    String AttSeleDT = nodeList.item(1).getChildNodes().item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                    conditions.setFuncID(FuncId);
//                    conditions.setSAttDesgDT(SAttDesgDT);
//                    conditions.setSAttDesgID(SAttDesgID);
//                    conditions.setRCPath(RCPath);
//                    conditions.setAttSeleDT(AttSeleDT);
//                    Map<Object,Object> map = new HashMap<>();
//                    map.put("RuleID",RuleID);
//                    map.put("FuncId",FuncId);
////                    userDao.insertRule_Con(map);
//                    System.out.println(conditions);
////                    System.out.println(conditions.getFuncID());
////                    System.out.println(conditions.getAttSeleDT());
////                    userDao.insertConditions();
//                    break;
//                }
//            }
//        } catch (XPathExpressionException e) {
//            e.printStackTrace();
//        }
//        //插入rule
//        NodeList nodeList_Rule = (NodeList) xPath.compile(exp_rule).evaluate(doc, XPathConstants.NODESET);
//        for(int j=0;j<nodeList_Rule.getLength();j++){
//            String RuleID1 = nodeList_Rule.item(j).getAttributes().getNamedItem("RuleId").getTextContent();
//            String Effect = nodeList_Rule.item(j).getAttributes().getNamedItem("Effect").getTextContent();
//            Element Description = (Element)nodeList_Rule.item(j);
//            String description = Description.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();
//            Rule rule = new Rule(RuleID1,Effect,description,null);
////            userDao.insertRule(rule);
////            System.out.println(rule);
//
//        }
//
//        //插入action
//        Action action = new Action(null,null,null,null,null);
//        String exp_action = "PolicySet/Policy/Rule/Target/Actions";
//        NodeList nodeList_Actions = (NodeList) xPath.compile(exp_action).evaluate(doc, XPathConstants.NODESET);
//        NodeList nodeList_Action = nodeList_Actions.item(0).getChildNodes();
//        for(int a=0;a<nodeList_Action.getLength();a++){
//            Node node_temp = nodeList_Action.item(a);
//            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
//                NodeList nodeList_ActionMatchs = node_temp.getChildNodes();
//                String ActionMatchId = nodeList_ActionMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
//                Element ActionMatchId_ele = (Element)nodeList_ActionMatchs.item(b);
//                String AttV = ActionMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
//                String AttDT = ActionMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                String AttDesgID = ActionMatchId_ele.getElementsByTagName("ActionAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
//                String AttDesgDT = ActionMatchId_ele.getElementsByTagName("ActionAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                Action action1 = new Action(ActionMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
////                System.out.println(action1);
////                userDao.insertAction(action1);
//            }
//        }
//        //插入Subjects
//        Subject subject = new Subject(null,null,null,null,null);
//        String exp_subject = "PolicySet/Policy/Rule/Target/Subjects";
//        NodeList nodeList_Subjects = (NodeList) xPath.compile(exp_subject).evaluate(doc, XPathConstants.NODESET);
//        NodeList nodeList_Subject = nodeList_Subjects.item(0).getChildNodes();
//        for(int c=0;c<nodeList_Subject.getLength();c++){
//            Node node_temp = nodeList_Subject.item(c);
//            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
//                NodeList nodeList_SubjectMatchs = node_temp.getChildNodes();
//                String SubjectMatchId = nodeList_SubjectMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
//                Element SubjectMatchId_ele = (Element)nodeList_SubjectMatchs.item(b);
//                String AttV = SubjectMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
//                String AttDT = SubjectMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                String AttDesgID = SubjectMatchId_ele.getElementsByTagName("SubjectAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
//                String AttDesgDT = SubjectMatchId_ele.getElementsByTagName("SubjectAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                Subject subject1 = new Subject(SubjectMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
////                System.out.println(subject1);
////                userDao.insertAction(action1);
//            }
//        }
//
//        //插入Resources
//        Resource resource = new Resource(null,null,null,null,null);
//        String exp_resource = "PolicySet/Policy/Target/Resources";
//        NodeList nodeList_Resources = (NodeList) xPath.compile(exp_resource).evaluate(doc, XPathConstants.NODESET);
//        NodeList nodeList_Resource = nodeList_Resources.item(0).getChildNodes();
//        for(int d=0;d<nodeList_Resource.getLength();d++){
//            Node node_temp = nodeList_Resource.item(d);
//            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
//                NodeList nodeList_ResourceMatchs = node_temp.getChildNodes();
//                String ResourceMatchId = nodeList_ResourceMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
//                Element ResourceMatchId_ele = (Element)nodeList_ResourceMatchs.item(b);
//                String AttV = ResourceMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
//                String AttDT = ResourceMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                String AttDesgID = ResourceMatchId_ele.getElementsByTagName("ResourceAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
//                String AttDesgDT = ResourceMatchId_ele.getElementsByTagName("ResourceAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                Resource resource1 = new Resource(ResourceMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
//                System.out.println(resource1);
////                userDao.insertAction(action1);
//            }
//        }
//
//        //插入Environments
//        String exp_environment = "PolicySet/Policy/Rule/Target/Environments";
//        NodeList nodeList_Environments = (NodeList) xPath.compile(exp_environment).evaluate(doc, XPathConstants.NODESET);
//        NodeList nodeList_Environment= nodeList_Environments.item(0).getChildNodes();
//        for(int d=0;d<nodeList_Environment.getLength();d++){
//            Node node_temp = nodeList_Environment.item(d);
//            for(int b=0;b<node_temp.getChildNodes().getLength();b++){
//                NodeList nodeList_EnvironmentMatchs = node_temp.getChildNodes();
//                String EnvironmentMatchId = nodeList_EnvironmentMatchs.item(b).getAttributes().getNamedItem("MatchId").getTextContent();
//                Element EnvironmentMatchId_ele = (Element)nodeList_EnvironmentMatchs.item(b);
//                String AttV = EnvironmentMatchId_ele.getElementsByTagName("AttributeValue").item(0).getFirstChild().getTextContent();
//                String AttDT = EnvironmentMatchId_ele.getElementsByTagName("AttributeValue").item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                String AttDesgID = EnvironmentMatchId_ele.getElementsByTagName("EnvironmentAttributeDesignator").item(0).getAttributes().getNamedItem("AttributeId").getTextContent();
//                String AttDesgDT = EnvironmentMatchId_ele.getElementsByTagName("EnvironmentAttributeDesignator").item(0).getAttributes().getNamedItem("DataType").getTextContent();
//                Environment environment= new Environment(EnvironmentMatchId,AttV,AttDT,AttDesgID,AttDesgDT);
////                System.out.println(environment);
////                userDao.insertAction(action1);
//            }
//        }
//        //插入policy
//        NodeList nodeList_Policy = (NodeList) xPath.compile(exp_policy).evaluate(doc, XPathConstants.NODESET);
//        for(int j=0;j<nodeList_Policy.getLength();j++){
//            String PolicyID = nodeList_Policy.item(j).getAttributes().getNamedItem("PolicyId").getTextContent();
//            String RCAlgID = nodeList_Policy.item(j).getAttributes().getNamedItem("RuleCombiningAlgId").getTextContent();
//            Element Description = (Element)nodeList_Policy.item(j);
//            String description = Description.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();
//            Policy policy = new Policy(PolicyID,RCAlgID,description);
////            userDao.insertRule(rule);
//            System.out.println(policy);
//        }
//        //插入policyset
//        NodeList nodeList_PolicySet = (NodeList) xPath.compile(exp_policyset).evaluate(doc, XPathConstants.NODESET);
//        for(int j=0;j<nodeList_PolicySet.getLength();j++){
//            String PolicySetID = nodeList_PolicySet.item(j).getAttributes().getNamedItem("PolicySetId").getTextContent();
//            String PCAlgID = nodeList_PolicySet.item(j).getAttributes().getNamedItem("PolicyCombiningAlgId").getTextContent();
//            Element Description = (Element)nodeList_PolicySet.item(j);
//            String description = Description.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();
//            PolicySet policySet = new PolicySet(PolicySetID,PCAlgID,description);
////            userDao.insertRule(rule);
//            System.out.println(policySet);
//        }
//
//
//    }
//}
