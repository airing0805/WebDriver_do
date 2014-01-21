package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.andy.weiboDriver.util.FileUtil;

public class GetUserFromFans {

	
	public GetUserFromFans(WebDriver fd, String url) throws InterruptedException {
		fd.get(url);
		GetUserFromFans(fd);
	}
	public void GetUserFromFans(WebDriver fd) throws InterruptedException{
		fd.findElement(By.cssSelector("li[class=\"follower S_line1\"] > a[name=\"place\"]")).click();
		WebElement we2 =fd.findElement(By.cssSelector("a[page-limited=\"true\"]"));
		int num = Integer.parseInt(we2.getText());
		String href  = we2.getAttribute("href");
		int last = href.lastIndexOf("=");
		href = href.substring(0, last+1);
		String uri = "http://weibo.com"+href;
		for(int i = 0;i<num;i++){
			href = href.substring(0, last+1)+(i+1);
			getUserList(fd,href);
		}
	}
	
	public void getUserList(WebDriver fd,String url) throws InterruptedException{
		fd.get(url);
		Thread.sleep(5000);
//		List<WebElement> weList =fd.findElements(By.cssSelector("ul[node-type=\"userListBox\"] > li "));
//		List<WebElement> weList2 =fd.findElements(By.cssSelector("ul[node-type=\"userListBox\"] > li > a[class=\"W_f14 S_func1\"]"));
		List<WebElement> weList =fd.findElement(By.cssSelector("ul[node-type=\"userListBox\"]")).findElements(By.cssSelector(" a[class=\"W_f14 S_func1\"]"));
//		List<WebElement> weList4 =fd.findElement(By.cssSelector("ul[node-type=\"userListBox\"]")).findElements(By.tagName("a"));
		
		System.out.println(weList.size());
//		System.out.println(weList2.size());
//		System.out.println(weList3.size());
//		System.out.println(weList4.size());
		String filepath =System.getProperty("user.dir")+File.separator+"aa.txt";
		String text = "";
		for(WebElement we:weList){
//			we = we.findElement(By.cssSelector("a[class=\"W_f14 S_func1\"]"));
			text =text+ we.getText()+"--@@##--"+we.getAttribute("href")+"\n";
		}
		System.out.println(filepath);
		System.out.println(text);
		FileUtil.write2FileEnd(filepath, text);
		
	}

}
