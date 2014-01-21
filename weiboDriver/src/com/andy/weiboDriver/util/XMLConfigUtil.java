package com.andy.weiboDriver.util;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

//只能用2.6的commons-lang包
public class XMLConfigUtil {
	private static XMLConfigUtil xmlConifgUtil;

	private XMLConfigUtil() {
	}

	public static String getProperty(String property) throws ConfigurationException {
		if (xmlConifgUtil == null) {
			xmlConifgUtil = new XMLConfigUtil();
		}
		return xmlConifgUtil.getPropertyPrivate(property);
	}

	private String getPropertyPrivate(String property)  {
		XMLConfiguration config = null;
		try {
			config = new XMLConfiguration("config/systemConfig.xml");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		String value = config.getString(property);
		return value;
	}
}