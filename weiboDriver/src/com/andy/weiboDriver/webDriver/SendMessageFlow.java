package com.andy.weiboDriver.webDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SendMessageFlow {
	
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
		WebDriverUtil.findElement4WaitClick(fd, by1, by2, 20).click();
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
}
