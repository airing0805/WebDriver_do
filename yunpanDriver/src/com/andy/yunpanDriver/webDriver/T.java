package com.andy.yunpanDriver.webDriver;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;


public class T {
	private static Logger logger = Logger.getLogger(  T.class);
	
	public static void main(String[] args) throws ClientProtocolException, IOException{
		String aa = "aa?bb";
		String bb = "aabb";
		logger.info(aa.substring(0,1));
		logger.info(bb.split("\\u003F")[0]);

	}
}
