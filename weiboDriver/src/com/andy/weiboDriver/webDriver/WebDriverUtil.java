package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebDriverUtil {

	private static Logger logger = Logger.getLogger(  WebDriverUtil.class);
	/**
	 * driver范围内，在一定时间内查找元素，传入秒时间
	 * 
	 * @param wd
	 * @param by
	 * @param num
	 * @return
	 * @throws InterruptedException
	 */
	public static WebElement findElement4Wait(WebDriver wd, By by, int num) {
		WebElement we = null;
		if (num < 0) {
			num = 1000;
		}
		for (int i = 0; i < num * 2; i++) {
			try {
				we = wd.findElement(by);
				if (null != we)
					Thread.sleep(1000);
					break;
			} catch (RuntimeException e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					continue;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return we;
	}

	public static boolean hasElement(WebElement wddddd, By by) {
		try {
			wddddd.findElement(by);
		} catch (RuntimeException e) {
			return false;
		}
		return true;
	}

	/**
	 * 元素范围内，在一定时间内查找元素，传入秒时间
	 * 
	 * @param we
	 * @param by
	 * @param num
	 * @return
	 * @throws InterruptedException
	 */
	public static WebElement findElement4Wait(WebElement we, By by, int num) {
		WebElement we2 = null;
		if (num < 0) {
			num = 1000;
		}
		for (int i = 0; i < num * 2; i++) {
			try {
				we2 = we.findElement(by);
				if (null != we)
					Thread.sleep(1000);
					break;
			} catch (RuntimeException e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					continue;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return we2;
	}

	//对iframe的内容单独显示在浏览器的时候，有时候容易跑回到微博主题的iframe内。
	public static WebDriver getUrl(WebDriver fd, String url) {
		String urlSub = url.split("\\u003F")[0];
		if(urlSub.endsWith("/")){
			urlSub = urlSub.substring(0, urlSub.length()-1);
		}
		for(int i=0;i<100;i++){
			try {
				Thread.sleep(500);
				String currentUrl = fd.getCurrentUrl();
				String currentUrlSub = currentUrl.split("\\u003F")[0];
				//地址要相等
				if(currentUrlSub.endsWith("/")){
					currentUrlSub = currentUrlSub.substring(0, currentUrlSub.length()-1);
				}
				if (urlSub.equals(currentUrlSub)) {
					return fd;
				} else {
					fd.get(url);
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				continue;
			}
		}
		return fd;
	}

	public static Map<String,Integer> getNumInfoAtUrl(WebDriver fd, String url) {
		getUrl(fd, url);
		WebElement infoDiv = findElement4Wait(fd, By.id("Pl_Official_Header__1"), 10);
		WebElement followWe = findElement4Wait(infoDiv, By.cssSelector("strong[node-type=\"follow\"]"), 10);
		int num = Integer.parseInt(followWe.getText());
		logger.info("关注:" + num);
		WebElement fansWe = infoDiv.findElement(By.cssSelector("strong[node-type=\"fans\"]"));
		logger.info("粉丝:" + fansWe.getText());
		Map<String,Integer > map = new HashMap<String,Integer>();
		map.put("关注",num);
		map.put("粉丝",Integer.parseInt(fansWe.getText()));
		return map;
	}
	
	public static void takeScreenShot(WebDriver fd){  
		String path = System.getProperty("user.dir") + File.separator+"screenShot_";
        File scrFile = ((TakesScreenshot)fd).getScreenshotAs(OutputType.FILE);  
        try {  
            FileUtils.copyFile(scrFile, new File(path+ System.currentTimeMillis()+".png"));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }

}
