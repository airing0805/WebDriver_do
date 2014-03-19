package com.andy.weiboDriver.doMain.yunpan;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.util.Threads;
import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.WebDriverUtil;

public class PanWu {

	private static Logger logger = Logger.getLogger(PanWu.class);

	public static void main(String[] args) {
		PanWu panWu = new PanWu();
		WebDriver fd = new FirefoxDriver();
		panWu.loginBaiduyun(fd);
		Threads.sleep(2000);
		String url = XMLConfig.getConfig("panWuConfig").getString("scanUrl");// "http://www.wangpanwu.com/zjgx/bt/";
		WebDriverUtil.getUrl(fd, url);
		String maxPageStr = fd.findElements(By.cssSelector("div.shuzi")).get(2).getText();
		int maxPage =  Integer.parseInt(maxPageStr.replace("共","").replace("页", "").trim());
		for (int i = 1; i < maxPage; i++) {
			int startPage = i + 1;
			logger.info("start page :" + startPage);
			panWu.getWuContent(fd);
			panWu.doNextPage(fd, startPage);
		}

	}

	public void getWuContent(WebDriver fd) {
		By listby = By.id("flist"); 
		boolean flag = WebDriverUtil.hasElement(fd, listby);
		if(!flag){
			listby = By.id("drlist");
			flag = WebDriverUtil.hasElement(fd, listby);
		}
		if(!flag){
			logger.info("没有找到内容，可能出错");
		}
		
		List<WebElement> liElList = fd.findElement(listby).findElements(By.tagName("li"));
		for (int i = 0; i < liElList.size(); i++) {
			String winHandleBefore = fd.getWindowHandle();
			WebElement liEl = liElList.get(i);
			WebElement linkEl = liEl.findElement(By.tagName("a"));
			String text = linkEl.getText();
			String href = linkEl.getAttribute("href");
			System.out.println(text + "\n" + href);
			linkEl.click();
			Threads.sleep(1000);
			switchToNewWindow(fd, winHandleBefore);
			gotoBaidu(fd, winHandleBefore);
			fd.switchTo().window(winHandleBefore);
		}
	}

	public void switchToNewWindow(WebDriver fd, String winHandleBefore) {
		for (String winHandle : fd.getWindowHandles()) {
			if (winHandle.equals(winHandleBefore)) {
				continue;
			}
			fd.switchTo().window(winHandle);
		}
	}

	public void gotoBaidu(WebDriver fd, String winHandleBefore) {
		String url = fd.getCurrentUrl();
		url = url.replace("/p/", "/turl/");
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1) + ".html";
		} else {
			url += ".html";
		}
		fd.get(url);
		Threads.sleep(5000);
		// fright
		// WebElement divEl = fd.findElement(By.cssSelector("div.fright"));
		// List<WebElement> linkElList = divEl.findElements(By.tagName("a"));
		// for (int i = 0; i < linkElList.size(); i++) {
		// WebElement linkEl = linkElList.get(0);
		// String hrefStr = linkEl.getAttribute("href");
		// logger.info(hrefStr);
		// if (hrefStr.contains("/turl/f")) {
		// logger.info("转存百度网盘");
		// linkEl.click();
		// break;
		// }
		// }
		// Threads.sleep(5000);
		// fd.close();
		// switchToNewWindow(fd, winHandleBefore);
		saveToBaidu(fd);

	}

	public void saveToBaidu(WebDriver fd) {
		// for (int i = 0; i < 10; i++) {
		// try {
		// String currentUrl = fd.getCurrentUrl();
		// if (currentUrl.startsWith("http://pan.baidu.com")) {
		// break;
		// } else {
		// Threads.sleep(1000);
		// }
		// } catch (Exception e) {
		// logger.info(e.getMessage());
		// }
		// }
		if (WebDriverUtil.hasElement(fd, By.id("share_nofound_des"))) {
			logger.info("文件取消了");
			fd.close();
			return;
		}
		By headersBy = By.id("nameCompareTrigger");
		boolean hasHeaders = WebDriverUtil.hasElement(fd, headersBy);
		if (hasHeaders) {
			fd.findElement(headersBy).findElement(By.tagName("dfn")).click();
			Threads.sleep(200);
			By barAllCmdTransferBy = By.id("barAllCmdTransfer");
			WebDriverUtil.waitDisplay(fd, barAllCmdTransferBy, 20);
			fd.findElement(barAllCmdTransferBy).click();
			Threads.sleep(5000);
		} else {
			By saveBy = By.id("emphsizeButton");
			WebDriverUtil.waitDisplay(fd, saveBy, 20);
			fd.findElement(saveBy).click();
			Threads.sleep(5000);
		}
		for (int i = 0; i < 20; i++) {
			By submitBy = By.id("_disk_id_" + i);
			boolean hasSubmitEl = WebDriverUtil.hasElement(fd, submitBy);
			if (hasSubmitEl) {
				WebElement submitEl = fd.findElement(submitBy);
				if(submitEl.getText().contains("确定")){
					submitEl.click();
					break;
				}
			}
			Threads.sleep(1000);
		}
		Threads.sleep(5000);
		
		for (int i = 0; i < 20; i++) {
			By submitBy = By.id("_disk_id_" + i);
			boolean hasSubmitEl = WebDriverUtil.hasElement(fd, submitBy);
			if (hasSubmitEl) {
				WebElement submitEl = fd.findElement(submitBy);
				if(submitEl.getText().contains("保存成功")){
					logger.info("保存成功");
					submitEl.click();
					break;
				}
			}
			Threads.sleep(1000);
		}
		logger.info("要关了");
		fd.close();

	}

	public void doNextPage(WebDriver fd, int startPage) {
		// http://www.wangpanwu.com/s-all/zhongzi/
		// shuzi
		WebElement pageDivEl = fd.findElements(By.cssSelector("div.shuzi")).get(0);
		List<WebElement> pageElList = pageDivEl.findElements(By.tagName("a"));
		int nextPageNum = startPage + 1;
		for (int i = 0; i < pageElList.size(); i++) {
			String text = pageElList.get(i).getText();
			if (nextPageNum == Integer.parseInt(text)) {
				String href = pageElList.get(i).getAttribute("href");
				WebDriverUtil.getUrl(fd, href);
				logger.info("准备进入"+ nextPageNum);
				Threads.sleep(5000);
				break;
			}
		}
	}

	// @Test
	public void testBaidu() {
		WebDriver fd = new FirefoxDriver();
		loginBaiduyun(fd);
	}

	public void loginBaiduyun(WebDriver fd) {
		WebDriverUtil.getUrl(fd, "http://yun.baidu.com/", By.id("doc_main"));
		// http://passport.baidu.com/?logout&u=http://yun.baidu.com/
		//
		// https://passport.baidu.com/?logout&u=http://pan.baidu.com
		WebElement usernameInputEl = fd.findElement(By.id("TANGRAM__PSP_4__userName"));
		usernameInputEl.click();
		usernameInputEl.sendKeys("yuanbaiduvip@163.com");
		WebElement passwordInputEl = fd.findElement(By.id("TANGRAM__PSP_4__password"));
		passwordInputEl.click();
		passwordInputEl.sendKeys("andy0805");
		WebElement submitEl = fd.findElement(By.id("TANGRAM__PSP_4__submit"));
		submitEl.click();

	}

}
