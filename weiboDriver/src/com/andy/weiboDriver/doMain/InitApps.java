package com.andy.weiboDriver.doMain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class InitApps {

	public static void main(String[] args) {
		List<Object> weiboList = XMLConfig.getConfig().getList("weibo.weibo_username");
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		int weiboNum = weiboList.size();
		WebDriver fd = null;
		if ("false".equals(firefoxRun)) {
			fd = new HtmlUnitDriver();
		} else {
			fd = new FirefoxDriver();
		}

		iterateInitApp(fd, weiboNum);

	}

	private static void iterateInitApp(WebDriver fd, int weiboNum) {
		while (true) {
			try {
				for (int i = 0; i < weiboNum; i++) {
					String path = System.getProperty("user.dir") + File.separator;
					int num = 0;
					int numT = 0;
					String username = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_username");
					String password = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_password");
					String weiboUrl = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_url");
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					System.out.println(sf1.format(new Date()));
					path += username + sf.format(new Date()) + "_init.txt";
					String fileMess = username + "\n";
					FileUtil.write2FileEnd(path, fileMess);
					new WeiboSina().login(fd, username, password);
					if("http://weibo.com/nguide/interests".equals(fd.getCurrentUrl().split("\\u003F")[0])){
						fd.findElements(By.className("fav_tag_sel")).get(3).findElement(By.tagName("a")).click();
						for(int j=0;j<10;j++){
							int size = fd.findElements(By.cssSelector("div[node-type=\"interest_list\"]")).size();
							if(2 <= size ){
								break;
							}else{
								Thread.sleep(500);
							}
						}
						for(int j=0;j<10;j++){
							int size = fd.findElements(By.cssSelector("div[node-type=\"interest_list\"]")).size();
							if(2 <= size ){
								break;
							}else{
								Thread.sleep(500);
							}
						}
						fd.findElement(By.cssSelector("a[action-type=\"W_btn_big\"]")).click();
					}
					Thread.sleep(0);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
