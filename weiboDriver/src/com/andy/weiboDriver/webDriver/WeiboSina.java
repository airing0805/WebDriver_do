package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.andy.weiboDriver.util.FileUtil;

public class WeiboSina {
	
	//自己的首页发微博
	public void SendMessage(WebDriver fd, String message) throws InterruptedException{
		//进入首页
		By indexBy  =By.cssSelector("div[class=\"gn_nav\"] > div[node-type=\"home\"] > a");
		WebElement indexEl = WebDriverUtil.findElement4Wait(fd, indexBy, 20);
		indexEl.sendKeys(Keys.DOWN);
		indexEl.click();
		//填入内容
		By textareaBy  = By.cssSelector("div[node-type=\"textElDiv\"] > textarea[node-type=\"textEl\"]");
		WebElement textareaEl = WebDriverUtil.findElement4Wait(fd,textareaBy,20);
		textareaEl.clear();
		textareaEl.sendKeys(message);
		textareaEl.sendKeys(Keys.DOWN);
		textareaEl.click();
		fd.findElement(By.cssSelector("a[node-type=\"publishBtn\"]")).click();
	}

	//个人主页，通过聊天组件发私信
	public void SendPrivateLetterAtUserIndex(WebDriver fd, String url,String message) throws InterruptedException {
		fd.get(url);
		//等待聊天组件加载
		By catBy = By.cssSelector("div[node-type=\"onlineBarFriend\"]");
		WebDriverUtil.findElement4Wait(fd, catBy, 20);
		// 等待私信按钮出现
		By by1 = By.cssSelector("div[node-type=\"catBtn\"] > a[class=\"W_btn_c\"] ");
		By by2 = By.cssSelector("div[class=\"pf_info_left\"] >div>div> span[class=\"name\"]");
		fd.findElement(by2).click();
		WebDriverUtil.findElement4Wait(fd, by1, 20).click();
		//等待聊天窗口出现
		By textAreaBy = By.cssSelector("div[node-type=\"_editorRoot\"] > textarea[node-type=\"_editorNode\"]  ");
		WebElement textAreaEl = WebDriverUtil.findElement4Wait(fd, textAreaBy, 20);
		textAreaEl.clear();
		textAreaEl.sendKeys(message);
		textAreaEl.click();
		//通过聊天窗口发送私信
		WebElement submitEl = fd.findElement(By.cssSelector("div[node-type=\"_sendBtnPanel\"] > a[node-type=\"_sendBtn\"]  "));
		submitEl.click();
		Thread.sleep(1000);
		//关闭聊天窗口
		WebElement closeEl = fd.findElement(By.cssSelector("div[class=\"wbim_tit2_rt\"] > a[node-type=\"WBIM_icon_close\"]"));
		closeEl.sendKeys(Keys.DOWN);
		closeEl.click();
	}
	
	//通过微博进行登录
	public void login(WebDriver fd,String username,String password) {
//		fd.get("http://www.weibo.com");
		fd.get("http://weibo.com/logout.php");
		By usernameBy = By.cssSelector("input[node-type=\"username\"]");
		WebElement usernameWe = WebDriverUtil.findElement4Wait(fd,usernameBy,50);
		usernameWe.clear();
		usernameWe.sendKeys(username);
		WebElement passwordWe = fd.findElement(By.cssSelector("input[node-type=\"password\"]"));
		passwordWe.clear();
		passwordWe.sendKeys(password);
//		fd.findElement(By.id("login_form_savestate")).click();
		WebElement loginDivWe = fd.findElement(By.cssSelector("div[node-type=\"normal_form\"]"));
		loginDivWe.findElement(By.cssSelector("a[node-type=\"submitBtn\"]")).click();
		WebDriverUtil.findElement4Wait(fd, By.id("pl_content_top"), 100);
		
	}
	
	public void logout(WebDriver fd){
		fd.get("http://weibo.com/logout.php");
	}
	
	//TODO需要测试
	public void GetUserFromFans(WebDriver fd) throws InterruptedException{
		fd.findElement(By.cssSelector("li[class=\"follower S_line1\"] > a[name=\"place\"]")).click();
		WebElement we2 =fd.findElement(By.cssSelector("a[page-limited=\"true\"]"));
		int num = Integer.parseInt(we2.getText());
		String href  = we2.getAttribute("href");
		int last = href.lastIndexOf("=");
		href = href.substring(0, last+1);
//		String uri = "http://weibo.com"+href;
		for(int i = 0;i<num;i++){
			href = href.substring(0, last+1)+(i+1);
			getUserList(fd,href);
		}
	}
	
	//这个是做什么的哦
	public void getUserList(WebDriver fd,String url) throws InterruptedException{
		fd.get(url);
		Thread.sleep(5000);
		List<WebElement> weList =fd.findElement(By.cssSelector("ul[node-type=\"userListBox\"]")).findElements(By.cssSelector(" a[class=\"W_f14 S_func1\"]"));
		
		System.out.println(weList.size());
		String filepath =System.getProperty("user.dir")+File.separator+"aa.txt";
		String text = "";
		for(WebElement we:weList){
			text =text+ we.getText()+"--@@##--"+we.getAttribute("href")+"\n";
		}
		System.out.println(filepath);
		System.out.println(text);
		FileUtil.write2FileEnd(filepath, text);
		
	}
}
