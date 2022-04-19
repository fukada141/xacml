package com.xacml.Controller;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import com.xacml.Service.PolicySetExportService;
import com.xacml.Service.PolicySetService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import static com.xacml.Service.Impl.PolicySetServiceImpl.trim;


@Controller
@CrossOrigin
@RequestMapping("/file")
public class UploadFileController {

    @Autowired
    private PolicySetService policySetService;

    @Autowired
    private PolicySetExportService policySetExportService;

    @RequestMapping("/upload")
    public String upload2(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(request)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //记录上传过程起始时的时间，用来计算上传时间
                int pre = (int) System.currentTimeMillis();
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                InputStream is = file.getInputStream();
                String result = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining(System.lineSeparator()));
                String triim = trim(result);
                System.out.println(triim);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                InputStream is1 = IOUtils.toInputStream(triim, StandardCharsets.UTF_8);
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(is1);
                policySetService.ParsePolicySetElement(doc);
//                System.out.println(file);

                //记录上传该文件后的时间

            }

        }
        String msg = "success";
        return msg;
    }
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    @ResponseBody
    public String download(@RequestParam("PolicySetID") String PolicySetID) throws IOException, TransformerConfigurationException {
        String file = policySetExportService.ExportFile(PolicySetID);
        return file;
    }


}