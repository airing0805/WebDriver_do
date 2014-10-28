package com.andy.weiboDriver.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class HtmlUnitTest {
	private static Logger logger = Logger.getLogger(  HtmlUnitTest.class);
	
	/*
	 * 第一次测试htmlunit
	 */
//	@Test
	public void testHtmlUnit(){
		try{
			String url = "http://www.baidu.com";
			WebDriver webDriver = null;
			webDriver = new HtmlUnitDriver();
			webDriver.get(url);
			WebElement inputElement = webDriver.findElement(By.id("kw"));
			inputElement.sendKeys("a");
			WebElement submitElement = webDriver.findElement(By.id("su"));
			submitElement.click();
			 //打印当前页面标题  
	        System.out.println("页面标题："+webDriver.getTitle());  
	        //返回当前页面的url  
	        System.out.println("页面url："+webDriver.getCurrentUrl());  
	        //返回当前的浏览器的窗口句柄  
	        System.out.println("窗口句柄："+webDriver.getWindowHandle());  
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}
	
	@Test
	public void baiduBook(){
		WebDriver driver=new HtmlUnitDriver(true);
		//打开百度首页
		driver.get("http://wenku.baidu.com/view/a303d56eaf1ffc4ffe47ac44.html");
		//打印页面标题
		System.out.println("页面标题："+driver.getTitle());
		WebElement current = driver.findElement(By.cssSelector("table[class=\"current\"]"));
		System.out.println(current.getText());
	}
	
	public void htmlUnitTest2(){
		WebDriver driver=new HtmlUnitDriver();
		//打开百度首页
		driver.get("http://www.baidu.com/");
		//打印页面标题
		System.out.println("页面标题："+driver.getTitle());
		//根据id获取页面元素输入框
		WebElement search=driver.findElement(By.id("kw"));
		//在id=“kw”的输入框输入“selenium”
		search.sendKeys("selenium");
		//根据id获取提交按钮
		WebElement submit=driver.findElement(By.id("su"));
		//点击按钮查询
		submit.click();
		//打印当前页面标题
	    System.out.println("页面标题："+driver.getTitle());
	    //返回当前页面的url
	    System.out.println("页面url："+driver.getCurrentUrl());
	    //返回当前的浏览器的窗口句柄
	    System.out.println("窗口句柄："+driver.getWindowHandle());
	}
	
	
//	WebClient webClient = new WebClient(BrowserVersion.CHROME);
//    webClient.getOptions().setThrowExceptionOnScriptError(false);
//    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//
//    webClient.getOptions().setJavaScriptEnabled(true);
//    webClient.getOptions().setActiveXNative(false);
//    webClient.getOptions().setCssEnabled(false);
//    webClient.getOptions().setThrowExceptionOnScriptError(false);
//    webClient.waitForBackgroundJavaScript(10*1000);
//    webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//
//    webClient.getOptions().setJavaScriptEnabled(true);
//    HtmlPage page = webClient.getPage("http://xiayanmei168.1688.com/page/albumlist.htm");
//    System.out.println(page.asXml());
//    System.out.println(page.getWebResponse().getContentAsString()); 


}
