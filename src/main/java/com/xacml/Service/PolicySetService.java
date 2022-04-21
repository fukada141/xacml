package com.xacml.Service;

import com.xacml.Entity.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PolicySetService {
    public void ParsePolicySetElement(Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException;
    public void ParsePolicyElement(String PolicySetID,Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException;
    public void ParseRuleElement(String PolicyID,Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException;
    public void ParseConditionElement(String RuleID,int i,Document doc) throws XPathExpressionException;
    public void ParsePolicyTargetElement(String PolicyID,int j,Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException;
    public void ParseRuleTargetElement(String RuleID,int j,Document doc) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException;
    public void ParsePolicySubject(String PolicyID,Document doc) throws XPathExpressionException ;
    public void ParsePolicyResource(String PolicyID,Document doc) throws XPathExpressionException;
    public void ParsePolicyEnvironment(String PolicyID,Document doc) throws XPathExpressionException;
    public void ParsePolicyAction(String PolicyID,Document doc) throws XPathExpressionException;
    public void ParseRuleSubject(String RuleID,Document doc) throws XPathExpressionException ;
    public void ParseRuleResource(String RuleID,Document doc) throws XPathExpressionException;
    public void ParseRuleEnvironment(String RuleID,Document doc) throws XPathExpressionException;
    public void ParseRuleAction(String RuleID,Document doc) throws XPathExpressionException;
    public void ParsePolicySet(Document doc) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException;
    public List<String> queryPolicySetID();

}
