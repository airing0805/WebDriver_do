package com.andy.weiboDriver.webDriver;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WeiboQQ {

	public String getMessageFlow(WebDriver fd, String url) throws InterruptedException {
		// String url = "http://t.qq.com/xiaohuacom123";
		long start = System.currentTimeMillis();
		String message = getMessage(fd, url);
		;
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println(total);
		return message;
		// fd.quit();
	}

	// 从qq获取微博内容，没有水印
	public String getMessage(WebDriver fd, String url) throws InterruptedException {
		StringBuffer sb = new StringBuffer();
		fd.get(url);
		By closeLoginBy = By.cssSelector("div[class=\"DWrap\"] > a.DClose.close");
		WebElement closeLoginElement = WebDriverUtil.findElement4Wait(fd, closeLoginBy, 2);
		if (null != closeLoginElement) {
			closeLoginElement.click();
		}
		// 点击原创等待加载
		By originalBy = By.xpath("//*[@id=\"userAppTab\"]/ul/li[2]/a");
		WebElement  originalWe = WebDriverUtil.findElement4Wait(fd, originalBy, 1);
		if (null != closeLoginElement) {
			String text = originalWe.getText();
			if(text.contains("原创")){
				originalWe.click();
			}
		}
		Thread.sleep(500);
		List<WebElement> messageLiList = fd.findElements(By.cssSelector("ul[id=\"talkList\"] > li"));
		for (int i = 0; i < messageLiList.size(); i++) {
			WebElement messageLi = messageLiList.get(i);
			WebElement messageDiv = messageLi.findElement(By.cssSelector("div[class=\"msgCnt\"]"));
			String message = messageDiv.getText().replace("\"", "“");
			sb.append(("{\"" + message + "\","));
			WebElement picDiv = WebDriverUtil.findElement4Wait(messageLi, By.cssSelector("div[class=\"mediaWrap\"] > div > a.pic"), 1);
			String picHref = "";
			if (null != picDiv) {
				picHref = picDiv.getAttribute("href");
			}
			sb.append(("\"" + picHref + "\"},"));
		}
		return sb.substring(0, sb.length() - 1).toString();
	}

}
