package com.andy.weiboDriver.test.util;

import java.util.Date;

import org.junit.Test;

public class T {

	@Test
	public void t(){
		long l = new Date().getTime();
		String aa = Long.toHexString(l);
		System.out.println(Long.toHexString(new Date().getTime()));
		System.out.println(l+""+Long.parseLong(aa, 16));
	}
}
