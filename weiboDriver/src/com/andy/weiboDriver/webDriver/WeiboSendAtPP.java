package com.andy.weiboDriver.webDriver;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.Threads;
import com.andy.weiboDriver.util.XMLConfig;

public class WeiboSendAtPP {
	private static Logger logger = Logger.getLogger(  WeiboSendAtPP.class);
	
	
	public WeiboSendAtPP() {
		super();
	}

	public void sendAtPPFlow(WebDriver fd, int weiboNum) throws ConfigurationException, InterruptedException {
		for (int i = 0; i < weiboNum; i++) {
			long start = System.currentTimeMillis();
			
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").pp_username");
			String password = XMLConfig.getConfig().getString("weibo(" + i + ").pp_password");
			logger.info(username);
			String path = System.getProperty("user.dir") + File.separator + username + ".txt";
			StringBuffer sb = FileUtil.readFileByLines(path);
			String[][] messArr = str2Arr(sb.toString());
			loginPP(fd, username, password);
			Threads.sleep(1000);
			iterateMessage(fd, messArr);

			long end = System.currentTimeMillis();
			long total = end - start;
			logger.info(total);
		}
	}

	private String[][] str2Arr(String message) {
		String[] messArr = message.split("~mashang~");
		String[][] mashangArr = new String[messArr.length][2];
		int num = messArr.length;
		for (int i = 0; i < num; i++) {
			
			String[] laiqianArr = messArr[i].split("~laiqian~");
			mashangArr[i][0] = laiqianArr[0];
			if (laiqianArr.length > 1) {
				mashangArr[i][1] = laiqianArr[1];
			} else {
				mashangArr[i][1] = "";
			}
		}
		return mashangArr;
	}


	private void iterateMessage(WebDriver fd, String[][] messArr) throws ConfigurationException {
		WebElement timer_diffWe = WebDriverUtil.findElement4Wait(fd, By.id("timer_diff_1"), 10);
		timer_diffWe.clear();
		timer_diffWe.sendKeys(XMLConfig.getConfig().getString("timer_diff"));
		String order = XMLConfig.getConfig().getString("sendMessageOrder");
		if("desc".equals(order)){
			ppSentDesc(fd,messArr);
		}else if("asc".equals(order)){
			ppSentAsc(fd,messArr);
		}
	}
	
	public void ppSentDesc(WebDriver fd, String[][] messArr){
		int num = messArr.length;
		for (int i = messArr.length-1; i > 0; i--) {
			logger.info("总共_"+num+"条"+"_发送"+(i+1)+"条");
			try {
				boolean flag = ppSendTime(fd, messArr[i][0], messArr[i][1]);
				//到达15天跳出迭代
				if(!flag){
					break;
				}
			} catch (Exception e) {
				logger.info("desc 失败的内容 ：" + messArr[i][0]);
				e.printStackTrace();
				i -=1;
				continue;
			}
		}
	}
	
	public void ppSentAsc(WebDriver fd, String[][] messArr){
		int num = messArr.length;
		for (int i = 0; i < messArr.length; i++) {
			logger.info("总共_"+num+"条"+"_发送"+(i+1)+"条");
			try {
				boolean flag = ppSendTime(fd, messArr[i][0], messArr[i][1]);
				//到达15天跳出迭代
				if(!flag){
					break;
				}
			} catch (Exception e) {
				logger.info("asc 失败的内容 ：" + messArr[i][0]);
				e.printStackTrace();
				i +=1;
				continue;
			}
		}
	}

	private boolean ppSendTime(WebDriver fd, String message, String picUrl)  {
		// 页面显示
		WebDriverUtil.findElement4Wait(fd, By.cssSelector("textarea[id=\"content_1\"]"), 10);
		// 显示图片提示方式
		((JavascriptExecutor) fd).executeScript("$('#show_pic_list_1').show();");
		Threads.sleep(100);
		// 显示图片提示方式
		WebElement picMoveWe2 = fd.findElement(By.cssSelector("span[id=\"span_open_pic_1\"]"));
		if (!picMoveWe2.isDisplayed()) {
			picMoveWe2.sendKeys(Keys.DOWN);
			new Actions(fd).moveToElement(picMoveWe2).build().perform();
		}
		Threads.sleep(100);
		WebElement picLink = WebDriverUtil.getElementOrNot(fd, By.cssSelector("a[id=\"open_pic_url_1\"]"));
		if (null != picUrl && picUrl.length() > 0) {
			// 显示图片提示窗口
			picLink.click();
			Threads.sleep(100);
			// 输入图片地址
			WebElement picLinkInput = fd.findElement(By.cssSelector("input[id=\"pic_url_1\"]"));
			picLinkInput.clear();
			picLinkInput.sendKeys(picUrl);

			Threads.sleep(10);
			// 提交图片
			WebElement picLinkSubmit = fd.findElement(By.cssSelector("input[id=\"pic_url_send_1\"]"));
			picLinkSubmit.click();
			Threads.sleep(300);
		}
		// 填微博
		WebElement MessageWe = fd.findElement(By.cssSelector("textarea[id=\"content_1\"]"));
		MessageWe.clear();
		MessageWe.sendKeys(message);
		MessageWe.click();
		new Actions(fd).moveToElement(MessageWe).build().perform();
		// 提交
		WebElement messageTimeSendBut = fd.findElement(By.cssSelector("input[id=\"button_1\"]"));
		messageTimeSendBut.click();
		// 要给图片提交留一点时间
		// 等待重复内容的提示
		Threads.sleep(3000);
		//关闭提示窗口		
		WebElement closeHas = WebDriverUtil.getElementOrNot(fd, By.cssSelector("a[id=\"dialog_close\"]"));
		if (null != closeHas && closeHas.isDisplayed()) {
			closeHas.click();
			//是否到达15天控制范围
			WebElement messageContent = WebDriverUtil.getElementOrNot(fd, By.cssSelector("input[id=\"dialog_message\"]"));
			if(null != messageContent && messageContent.isDisplayed()){
				messageContent.getText().contains("15天");
				logger.info("满15天了");
				return false;
			}
			// 等待一下，让图片地址自动关闭。
			Threads.sleep(100);
		}
		MessageWe.clear();
		// 上传完成后，关闭图片，防止中断。
		WebElement picClose = WebDriverUtil.getElementOrNot(fd, By.cssSelector("strong[id=\"insert_picture_preview_1\"] > a"));
		if (null != picClose && picClose.isDisplayed()) {
			picClose.click();
		}
		//调整不发送的时间
		Select hourSelect = new Select(fd.findElement(By.cssSelector("select[id=\"hour_1\"]")));
		WebElement hourSelectedWe = hourSelect.getFirstSelectedOption();
		if("1".equals(hourSelectedWe.getText())){
			hourSelect.selectByVisibleText("7");
			Select minuteSelect = new Select(fd.findElement(By.cssSelector("select[id=\"minute_1\"]")));
			minuteSelect.selectByVisibleText("30");
		}
		//只有满了15天才返回false
		return true;
	}

	@SuppressWarnings("unused")
	private void ppSendTimeBak(WebDriver fd, String message, String picUrl)  {
		// 显示图片提示方式
		((JavascriptExecutor) fd).executeScript("$('#show_pic_list_1').show();");
		Threads.sleep(100);
		// 显示图片提示方式
		WebElement picMoveWe2 = fd.findElement(By.cssSelector("span[id=\"span_open_pic_1\"]"));
		if (!picMoveWe2.isDisplayed()) {
			picMoveWe2.sendKeys(Keys.DOWN);
			new Actions(fd).moveToElement(picMoveWe2).build().perform();
		}
		Threads.sleep(50);
		WebElement picLink = WebDriverUtil.findElement4Wait(fd, By.cssSelector("a[id=\"open_pic_url_1\"]"), 1);
		if (null != picUrl && picUrl.length() > 0) {
			// 显示图片提示窗口
			picLink.click();
			Threads.sleep(100);
			// 输入图片地址
			WebElement picLinkInput = fd.findElement(By.cssSelector("input[id=\"pic_url_1\"]"));
			picLinkInput.clear();
			picLinkInput.sendKeys(picUrl);

			Threads.sleep(10);
			// 提交图片
			WebElement picLinkSubmit = fd.findElement(By.cssSelector("input[id=\"pic_url_send_1\"]"));
			picLinkSubmit.click();
			Threads.sleep(300);
		}
		// 填微博
		WebElement MessageWe = fd.findElement(By.cssSelector("textarea[id=\"content_1\"]"));
		MessageWe.clear();
		MessageWe.sendKeys(message);
		MessageWe.click();
		new Actions(fd).moveToElement(MessageWe).build().perform();
		// 提交
		WebElement messageTimeSendBut = fd.findElement(By.cssSelector("input[id=\"button_1\"]"));
		messageTimeSendBut.click();
		// 要给图片提交留一点时间
		// 等待重复内容的提示
		Threads.sleep(1000);
		WebElement closeHas = WebDriverUtil.findElement4Wait(fd, By.cssSelector("a[id=\"dialog_close\"]"), 1);
		if (null != closeHas && closeHas.isDisplayed()) {
			closeHas.click();
			// 等待一下，让图片地址自动关闭。
			Threads.sleep(100);
		}
		MessageWe.clear();
		// 上传完成后，关闭图片，防止中断。
		WebElement picClose = WebDriverUtil.findElement4Wait(fd, By.cssSelector("strong[id=\"insert_picture_preview_1\"] > a"), 2);
		if (null != picClose && picClose.isDisplayed()) {
			picClose.click();
		}
	}

	// 登录到pp
	private void loginPP(WebDriver fd, String username, String password)  {
		// 这个注释要保留
		// String ppUrl = "http://weibo.pp.cc/";
		// 退出的链接地址，
		String ppUrl = "http://login.pp.cc/logout.html?redirect=http://weibo.pp.cc/logout.php";
		fd.get(ppUrl);
		// 先保证退出
		WebDriverUtil.findElement4Wait(fd, By.id("changeMyMenu"), 1);
		WebElement usernameWe = fd.findElement(By.id("username_login"));
		usernameWe.clear();
		usernameWe.sendKeys(username);
		WebElement passwordWe =  fd.findElement(By.id("password_login"));
		passwordWe.clear();
		passwordWe.sendKeys(password);
		fd.findElement(By.id("submit_login")).click();
	}

	@SuppressWarnings("unused")
	private void ppTime(WebDriver fd, String text) {
		// 在晚上1点到7点的时候不发送
		// 当
//		Calendar calendar = Calendar.getInstance();
//		Select select = new Select(fd.findElement(By.cssSelector("select[id=\"hour_1\"]")));
//		select.selectByVisibleText(text);
	}

	@SuppressWarnings("unused")
	private void loginPP4Oauth(WebDriver fd)  {
		String ppUrl = "http://weibo.pp.cc/";
		fd.get(ppUrl);
		Threads.sleep(1000);
		String currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		WebElement sinaLoginWe = fd.findElement(By.cssSelector("a[id=\"account_login\"]"));
		sinaLoginWe.click();
		Threads.sleep(1000);
		currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		String ppOauthUrl = "https://api.weibo.com/oauth2/authorize?client_id=1967296247&redirect_uri=http%3A%2F%2Fweibo.pp.cc%2Fmember.php%3Fmod%3Dbind%26action%3Daccess%26type%3Dsina%26app%3Dtime%26sinav%3D3&response_type=code";
		fd.get(ppOauthUrl);
		WebElement userIdWe = fd.findElement(By.cssSelector("input[id=\"userId\"]"));
		userIdWe.clear();
		userIdWe.sendKeys("yitest0805@sina.com");
		WebElement passwdWe = fd.findElement(By.cssSelector("input[id=\"passwd\"]"));
		passwdWe.clear();
		passwdWe.sendKeys("andy0805");
		WebElement submitWe = fd.findElement(By.cssSelector("a[node-type=\"submit\"]"));
		submitWe.click();
	}

	// 点击新浪登录
	@SuppressWarnings("unused")
	private void loginPP4click(WebDriver fd)  {
		String ppUrl = "http://weibo.pp.cc/";
		fd.get(ppUrl);
		String currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		Threads.sleep(1000);
		WebElement sinaLoginWe = fd.findElement(By.cssSelector("a[id=\"account_login\"]"));
		sinaLoginWe.sendKeys(Keys.DOWN);
		sinaLoginWe.click();
		Threads.sleep(1000);
		if (currentUrl.contains("time")) {
			return;
		}
		String timeUrl = "http://weibo.pp.cc/time/";
		fd.get(timeUrl);
	}

	

}
