package com.andy.weiboDriver.util;

import java.util.Date;

import org.junit.Test;

public class T {

	@Test
	public void t(){
		String aa = "asdf;jkl@@@";
		String[] bb = aa.split("@@@") ;
		System.out.println(bb.length+"))))"+bb[0]);
	}
}
