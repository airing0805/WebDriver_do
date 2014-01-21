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
	
	public String getUserInfo(){
		//TODO
		return null;
	}
	
	public String getUserFromTopic(){
		//TODO 通过话题，获取用户
		return null;
	}
	
	//TODO需要测试
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