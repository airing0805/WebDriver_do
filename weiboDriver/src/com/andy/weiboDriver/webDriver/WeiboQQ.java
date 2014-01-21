package com.andy.weiboDriver.webDriver;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public  class WeiboQQ {

	 @Test
	public void faWeiFlowT() throws InterruptedException {
		WebDriver fd = new FirefoxDriver();
		String url  = "http://t.qq.com/xiaohuacom123";
		long start = System.currentTimeMillis();
		getMessage(fd, url);;
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println(total);

		// fd.quit();
	}
	 
	 //从qq获取微博内容，没有水印
	 public void getMessage(WebDriver fd, String url) throws InterruptedException {
			fd.get(url);
			By closeLoginBy = By.cssSelector("div[class=\"DWrap\"] > a.DClose.close");
			WebElement closeLoginElement = WebDriverUtil.findElement4Wait(fd, closeLoginBy, 2);
			if (null != closeLoginElement) {
				closeLoginElement.click();
			}
			//点击原创等待加载
			fd.findElement(By.xpath("//*[@id=\"userAppTab\"]/ul/li[2]/a")).click();
			Thread.sleep(500);
			List<WebElement> messageLiList = fd.findElements(By.cssSelector("ul[id=\"talkList\"] > li"));
			for (int i = 0; i < messageLiList.size(); i++) {
				WebElement messageLi = messageLiList.get(i);
				WebElement messageDiv = messageLi.findElement(By.cssSelector("div[class=\"msgCnt\"]"));
				String message = messageDiv.getText().replace("\"", "“");
				System.out.println("{\""+message+"\",");
				WebElement picDiv = WebDriverUtil.findElement4Wait(messageLi, By.cssSelector("div[class=\"mediaWrap\"] > div > a.pic"),1);
				String picHref = "";
				if(null != picDiv){
					picHref = picDiv.getAttribute("href");
				}
				System.out.println("\""+picHref+"\"},");
			}
		}

}
