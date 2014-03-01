package com.andy.weiboDriver.doMain.yunpan;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LinkOut400gb {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Test
	public void test(){
		WebDriver fd = new FirefoxDriver();
		fd.get("http://newhome.400gb.com/?item=files&action=index&folder_id=5486597");
	}

}
