package com.solveit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.io.Closeables;

public class Props implements Const{
	private static final Log LOG = LogFactory.getLog(Props.class);

	private static final Properties QA_PROP = loadProperties(APPLICATION_PROPERTIES);

	private Props(){}

	public static String get(String key){
		return QA_PROP.getProperty(key);
	}

	public static Properties loadProperties(String name) {
    	LOG.info("Loading property file: "+ name);
        Properties properties = new Properties();
        InputStream stream = null;
        try {
            stream = ClassUtil.getInputStreamFromClassPath(name);
            properties.load(stream);
        } catch (IOException e) {
        	LOG.error("Exception while loading the property file: "+ name, e);
        } finally {
            try {
            	Closeables.close(stream, true);
            } catch(IOException e) {
            	LOG.error("Exception while closing the property file: "+ name, e);
            }
        }
        return properties;
    }
}
