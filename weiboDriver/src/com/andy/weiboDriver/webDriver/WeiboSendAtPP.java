package com.andy.weiboDriver.webDriver;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class WeiboSendAtPP {


	public void faWeiFlowT(WebDriver fd,String username,String password,String[][] messArr) throws InterruptedException {
		long start = System.currentTimeMillis();
		login(fd);
		Thread.sleep(1000);
		loginPP(fd,username,password);
		iterateMessage(fd,messArr);
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println(total);
		 fd.quit();
	}
	
	public void iterateMessage(WebDriver fd,String[][] messArr) throws InterruptedException{
		fd.findElement(By.id("timer_diff_1")).sendKeys("25");;
		for(int i=0 ;i<messArr.length;i++){
			ppSendTime(fd, messArr[i][0], messArr[i][1]);
			Thread.sleep(500);
		}
	}
	
	public void ppSendTime(WebDriver fd,String message,String picUrl) throws InterruptedException{
		//显示图片提示方式
		((JavascriptExecutor) fd).executeScript("$('#show_pic_list_1').show();");
		Thread.sleep(100);
		//显示图片提示方式
		WebElement picMoveWe2 = fd.findElement(By.cssSelector("span[id=\"span_open_pic_1\"]"));
		if(!picMoveWe2.isDisplayed()){
			picMoveWe2.sendKeys(Keys.DOWN);
			new Actions(fd).moveToElement(picMoveWe2).build().perform();
		}
		Thread.sleep(50);
		WebElement picLink  = WebDriverUtil.findElement4Wait(fd, By.cssSelector("a[id=\"open_pic_url_1\"]"),1);
		if(null != picUrl && picUrl.length()>0){
			//显示图片提示窗口
			picLink.click();
			Thread.sleep(10);
			//输入图片地址
			WebElement picLinkInput = fd.findElement(By.cssSelector("input[id=\"pic_url_1\"]"));
			picLinkInput.sendKeys(picUrl);
			Thread.sleep(10);
			//提交图片
			WebElement picLinkSubmit = fd.findElement(By.cssSelector("input[id=\"pic_url_send_1\"]"));
			picLinkSubmit.click();
			Thread.sleep(300);
		}
		//填微博
		WebElement MessageWe = fd.findElement(By.cssSelector("textarea[id=\"content_1\"]"));
		MessageWe.sendKeys(message);
		MessageWe.click();
		new Actions(fd).moveToElement(MessageWe).build().perform();
		//提交
		WebElement messageTimeSendBut = fd.findElement(By.cssSelector("input[id=\"button_1\"]"));
		messageTimeSendBut.click();
		//要给图片提交留一点时间
		//等待重复内容的提示
		Thread.sleep(3000);
		WebElement closeHas = WebDriverUtil.findElement4Wait(fd,By.cssSelector("a[id=\"dialog_close\"]"),1);
		if(closeHas.isDisplayed() && null != closeHas){
			closeHas.click();
			WebElement picClose = fd.findElement(By.cssSelector("strong[id=\"insert_picture_preview_1\"] > a"));
			if(picClose.isDisplayed()){
				picClose.click();
			}
		}
		MessageWe.clear();
	}
	
	public void ppTime(WebDriver fd,String text){
		Calendar calendar = Calendar.getInstance();
		Select select = new Select(fd.findElement(By.cssSelector("select[id=\"hour_1\"]")));
		select.selectByVisibleText(text);
	}
	
	public void loginPP4Oauth(WebDriver fd) throws InterruptedException{
		String ppUrl = "http://weibo.pp.cc/";
		fd.get(ppUrl);
		Thread.sleep(1000);
		String currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		WebElement sinaLoginWe = fd.findElement(By.cssSelector("a[id=\"account_login\"]"));
		sinaLoginWe.click();
		Thread.sleep(1000);
		currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		String ppOauthUrl = "https://api.weibo.com/oauth2/authorize?client_id=1967296247&redirect_uri=http%3A%2F%2Fweibo.pp.cc%2Fmember.php%3Fmod%3Dbind%26action%3Daccess%26type%3Dsina%26app%3Dtime%26sinav%3D3&response_type=code";
		fd.get(ppOauthUrl);
		WebElement userIdWe = fd.findElement(By.cssSelector("input[id=\"userId\"]"));
		userIdWe.sendKeys("yitest0805@sina.com");
		WebElement passwdWe = fd.findElement(By.cssSelector("input[id=\"passwd\"]"));
		passwdWe.sendKeys("andy0805");
		WebElement submitWe = fd.findElement(By.cssSelector("a[node-type=\"submit\"]"));
		submitWe.click();
	}

	//点击新浪登录
	public void loginPP4click(WebDriver fd) throws InterruptedException {
		String ppUrl = "http://weibo.pp.cc/";
		fd.get(ppUrl);
		String currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		Thread.sleep(1000);
		WebElement sinaLoginWe = fd.findElement(By.cssSelector("a[id=\"account_login\"]"));
		sinaLoginWe.sendKeys(Keys.DOWN);
		sinaLoginWe.click();
		Thread.sleep(1000);
		if (currentUrl.contains("time")) {
			System.out.println("contains time");
			return;
		}
		String timeUrl = "http://weibo.pp.cc/time/";
		fd.get(timeUrl);
	}
	// 登录到pp
	public void loginPP(WebDriver fd,String username,String password) {
		String ppUrl = "http://weibo.pp.cc/";
		fd.get(ppUrl);
		fd.findElement(By.id("username_login")).sendKeys(username);
		fd.findElement(By.id("password_login")).sendKeys(username);
		fd.findElement(By.id("submit_login")).click();
	}

	// 登录到新浪微博
	public void login(WebDriver fd) {
		fd.get("http://www.weibo.com");
		List<WebElement> webElementList = fd.findElements(By.tagName("input"));
		for (WebElement we : webElementList) {
			boolean flag1 = "username".equals(we.getAttribute("name"));
			boolean flag2 = "username".equals(we.getAttribute("node-type"));
			if (flag1 && flag2) {
				we.sendKeys("yitest0805@sina.com");
			}
			boolean flag3 = "password".equals(we.getAttribute("name"));
			boolean flag4 = "password".equals(we.getAttribute("node-type"));
			if (flag3 && flag4) {
				we.sendKeys("andy0805");
				break;
			}
		}
		List<WebElement> aList = fd.findElements(By.tagName("a"));
		for (WebElement we : aList) {
			boolean flag2 = "submitBtn".equals(we.getAttribute("node-type"));
			if (flag2) {
				we.click();
				break;
			}
		}
	}

}
