package com.andy.yunpanDriver.doMain;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.yunpanDriver.util.FileUtil;
import com.andy.yunpanDriver.util.Threads;
import com.andy.yunpanDriver.webDriver.WebDriverUtil;

public class Google {

	private static Logger logger = Logger.getLogger(Google.class);

	@Test
	public void test() {
		WebDriver fd = new FirefoxDriver();
		// fd.get("http://www.baidu.com");
		By listBy = By.id("lst-ib");
		WebDriverUtil.getUrl(fd, "https://www.google.com.hk/webhp?hl=zh-CN", listBy);
		fd.findElement(listBy).click();
		fd.findElement(listBy).sendKeys("site:yun.baidu.com torrent");;
		Threads.sleep(1000);
		fd.findElement(By.cssSelector("input[jsaction=\"sf.chk\"]")).click();
		WebDriverUtil.waitDisplay(fd, By.id("nav"), 10);
		int num =5;
		for(int i = 0; i < num; i++){
			logger.info("--------"+i+"--------");
			
			int nextnum = i+2;
			doNextPage(fd,nextnum);
		}

	}
	
	public void doNextPage(WebDriver fd, int nextnum){
		List<WebElement> tdElList = fd.findElement(By.id("nav")).findElements(By.tagName("td"));
		for (int j = 0; j < tdElList.size(); j++) {
			WebElement tdEl = tdElList.get(j);
			By linkBy = By.tagName("a") ;
			boolean flag = WebDriverUtil.hasElement(tdEl, linkBy);
			if(!flag){
				continue;
			}
			WebElement linkEl = tdEl.findElement(By.tagName("a"));
			String href = linkEl.getAttribute("href");
			String text = linkEl.getText();
			if (text.contains("上一页") || text.contains("下一页")) {
				continue;
			}
			if(nextnum==Integer.parseInt(text)){
				logger.info(text + "\n" + href);
				fd.get(href);
//				linkEl.click();
				break;
			}
		}
	}
}
