package com.andy.weiboDriver.util;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

//只能用2.6的commons-lang包
public class XMLConfig {
	private static XMLConfiguration xmlConifg;

	private XMLConfig() {
	}
	
	

	public static XMLConfiguration getConfig() throws ConfigurationException {
		if (xmlConifg == null) {
			System.out.println(System.getProperty("user.dir"));              
			xmlConifg = new XMLConfiguration(System.getProperty("user.dir")+"/config/systemConfig.xml");
		}
		return xmlConifg;
	}

}