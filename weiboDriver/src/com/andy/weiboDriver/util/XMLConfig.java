package com.andy.weiboDriver.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

//只能用2.6的commons-lang包
public class XMLConfig {
	private static Logger logger = Logger.getLogger(  XMLConfig.class);
	
	private static XMLConfiguration xmlConifg;

	private XMLConfig() {
	}

	public static XMLConfiguration getConfig() {
		try {
			if (xmlConifg == null) {
				logger.info(System.getProperty("user.dir"));
				xmlConifg = new XMLConfiguration(System.getProperty("user.dir") + "/config/systemConfig.xml");
			}
		} catch (ConfigurationException e){
			e.printStackTrace();
		}
		return xmlConifg;
	}
	
	public static XMLConfiguration getConfig(String name) {
		try {
			if (xmlConifg == null) {
				String fileStr = System.getProperty("user.dir") + "/config/"+name+".xml";
				logger.info(fileStr);
				xmlConifg = new XMLConfiguration(fileStr);
			}
		} catch (ConfigurationException e){
			e.printStackTrace();
		}
		return xmlConifg;
	}

}