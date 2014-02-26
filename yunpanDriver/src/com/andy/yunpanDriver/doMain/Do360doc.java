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

public class Do360doc {

	private static Logger logger = Logger.getLogger(Do360doc.class);

	@Test
	public void test() {
		WebDriver fd = new FirefoxDriver();
		// fd.get("http://www.baidu.com");
		By by = By.cssSelector("div.right_topnew");
		WebDriverUtil.getUrl(fd, "http://www.360doc.com/classarticle27.html", by);
		fd.findElement(by).findElements(By.tagName("div.left")).get(1).click();
		Threads.sleep(1000);
		WebDriverUtil.waitDisplay(fd, By.cssSelector("td.fenleitopl"), 5);
		List<WebElement> trElList = fd.findElement(By.cssSelector("table.numberwz")).findElements(By.cssSelector("tr"));
		for (int i = 0; i < trElList.size(); i++) {
			WebElement trEl = trElList.get(i);
			WebElement titleDivEl = trEl.findElement(By.cssSelector("div.fenleitit"));
			WebElement linkEl = titleDivEl.findElement(By.tagName("a"));
			logger.info(linkEl.getText() + "\n" + linkEl.getAttribute("href"));
		}

	}
}
