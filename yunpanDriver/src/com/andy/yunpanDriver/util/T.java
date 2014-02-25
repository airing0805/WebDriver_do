package com.andy.yunpanDriver.util;

import org.apache.log4j.Logger;
import org.junit.Test;

public class T {

	private static Logger logger = Logger.getLogger(T.class);
	@Test
	public void t(){
		String aa = "asdf;jkl@@@";
		String[] bb = aa.split("@@@") ;
		logger.info(bb.length+"))))"+bb[0]);
		
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e.getMessage(),e);
			}
	}
}
