package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
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
		for (int i = 0; i < num ; i++) {
			try {
				we = wd.findElement(by);
				if (null != we)
					we = wd.findElement(by);
					break;
			} catch (RuntimeException e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					continue;
				}
			} 
		}
		return we;
	}
	
	public static WebElement getElementOrNot(WebElement wddddd, By by) {
		WebElement we = null;
		try {
			we = wddddd.findElement(by);
		} catch (RuntimeException e) {
			return null;
		}
		return we;
	}
	
	public static WebElement getElementOrNot(WebDriver wddddd, By by) {
		WebElement we = null;
		try {
			we = wddddd.findElement(by);
		} catch (RuntimeException e) {
			return null;
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
	
	public static boolean hasElement(WebDriver wddddd, By by) {
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
		for (int i = 0; i < num ; i++) {
			try {
				we2 = we.findElement(by);
				if (null != we)
					we2 = we.findElement(by);
					break;
			} catch (RuntimeException e) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					continue;
				}
			} 
		}
		return we2;
	}
	
	public static void getUrl(WebDriver driver,String url,By by) {
		int actionCount =10;
	    boolean inited = false;  
	    int maxLoadTime =100;
	    int index = 0, timeout = 10;  
	    while (!inited && index < actionCount){  
	        timeout = (index == actionCount - 1) ? maxLoadTime : 10;//最后一次跳转使用最大的默认超时时间  
	        inited = navigateAndLoad(driver,url,timeout,by);  
	        index ++;  
	    }  
	    if (!inited && index == actionCount){//最终跳转失败则抛出运行时异常，退出运行  
	        throw new RuntimeException("can not get the url [" + url + "] after retry " + actionCount + "times!");  
	    }  
	}
	
	private static boolean navigateAndLoad(WebDriver driver,String url ,int timeout ,By by ){  
	    try {  
	        driver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);  
	        driver.get(url);  
	        
	        String urlSub = url.split("\\u003F")[0];
			if(urlSub.endsWith("/")){
				urlSub = urlSub.substring(0, urlSub.length()-1);
			}
			
	        String currentUrl = driver.getCurrentUrl();
			String currentUrlSub = currentUrl.split("\\u003F")[0];
			//地址要相等
			if(currentUrlSub.endsWith("/")){
				currentUrlSub = currentUrlSub.substring(0, currentUrlSub.length()-1);
			}
			if (!urlSub.equals(currentUrlSub)) {
				return false;
			}
			
			WebElement el = driver.findElement(by);
			if(null == el){
				return false;
			}
	    } catch (TimeoutException e) {  
	        return false;//超时的情况下返回false  
	    } catch (Exception e) {  
	        logger.info(e.getMessage(),e);
	        return false;
	    }finally{  
	        driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);  
	    }  
	    return true;
	}

	//对iframe的内容单独显示在浏览器的时候，有时候容易跑回到微博主题的iframe内。
	public static WebDriver getUrl1(WebDriver fd, String url) {
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
		getUrl(fd, url,By.id("Pl_Official_Header__1"));
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
