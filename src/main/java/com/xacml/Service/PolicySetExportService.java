package com.xacml.Service;

import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface PolicySetExportService {
    public String ExportFile(String PolicySetId) throws IOException, TransformerConfigurationException;
}
