package com.andy.weiboDriver.doMain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.util.Threads;
import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class InitApps {
	private static Logger logger = Logger.getLogger(InitApps.class);

	public static void main(String[] args) {
		List<Object> weiboXmlList = XMLConfig.getConfig().getList("weibo.weibo_username");
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		int weiboNum = weiboXmlList.size();
		WebDriver fd = null;
		if ("false".equals(firefoxRun)) {
			fd = new HtmlUnitDriver();
		} else {
			fd = new FirefoxDriver();
		}
		fd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		 Map<String, String> weiboMap = getWeibo( );
		iterateInitApp(fd, weiboMap);
		
		fd.quit();

	}
	
	@Test
	public static void test() {
		List<Object> weiboXmlList = XMLConfig.getConfig().getList("weibo.weibo_username");
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		int weiboNum = weiboXmlList.size();
		WebDriver fd = null;
		if ("false".equals(firefoxRun)) {
			fd = new HtmlUnitDriver();
		} else {
			fd = new FirefoxDriver();
		}
		fd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		// Map<String, String> weiboMap = getWeibo( );
		Map<String, String> weiboMap = new HashMap<String, String>();
		weiboMap.put("yitest08051@sina.com", "andy0805");
		iterateInitApp(fd, weiboMap);
		
		fd.quit();
		
	}

	private static Map<String, String> getWeibo() {
		List<Object> weiboXmlList = XMLConfig.getConfig().getList("initapps.weibo_username");
		int weiboNum = weiboXmlList.size();
		Map<String, String> weiboMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < weiboNum; i++) {
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_username");
			String password = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_password");
			weiboMap.put(username, password);
		}
		return weiboMap;
	}

	private static void iterateInitApp(WebDriver fd, Map<String, String> weiboMap) {

		for (Entry<String, String> weiboMapEntry : weiboMap.entrySet()) {
				String username = weiboMapEntry.getKey();
				String password = weiboMapEntry.getValue();
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logger.info(sf1.format(new Date()));
				new WeiboSina().login(fd, username, password);

				selectInterest(fd);

				addApp(fd,username, password);

				Threads.sleep(0);
		}
	}

	public static void selectInterest(WebDriver fd)  {
		if ("http://weibo.com/nguide/interests".equals(fd.getCurrentUrl().split("\\u003F")[0])) {
			fd.findElements(By.className("fav_tag_sel")).get(3).findElement(By.tagName("a")).click();
			for (int j = 0; j < 10; j++) {
				int size = fd.findElements(By.cssSelector("div[node-type=\"interest_list\"]")).size();
				if (2 <= size) {
					break;
				} else {
					Threads.sleep(500);
				}
			}
			for (int j = 0; j < 10; j++) {
				int size = fd.findElements(By.cssSelector("div[node-type=\"interest_list\"]")).size();
				if (2 <= size) {
					break;
				} else {
					Threads.sleep(500);
				}
			}
			fd.findElement(By.cssSelector("a[action-type=\"W_btn_big\"]")).click();
		}
	}

	public static boolean addApp(WebDriver fd, String username, String password) {
		fd.get("http://app.weibo.com/my");
		// 死亡用户清除
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=1050823112&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Fkilldie");
		submitOauth(fd);
		// 取消关注管理
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=2801105842&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Fcancels");
		submitOauth(fd);

		// 互粉加加
		fd.get("https://api.weibo.com/oauth2/authorize?client_id=245891426&redirect_uri=http%3A%2F%2Fqiuzf.sinaapp.com%2Fcallback.php&response_type=code&state=sina");
		submitOauth(fd);
		// 推兔
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=4071554311&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Ftuituoo");
		submitOauth(fd);
		// 推米
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=2372363467&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Ftuimimi");
		submitOauth(fd);
		// 互推联盟
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=2819702316&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Fwbhutui");
		submitOauth(fd);
		// 互粉赏金榜
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=4227115430&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Ffansreward%2F");
		submitOauth(fd);

		// 优推推互粉
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=3904763163&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Fzhuanqi");
		submitOauth(fd);
		// 互推达人
		fd.get("https://api.weibo.com/oauth2/authorize?client_id=1040961737&redirect_uri=http%3A%2F%2Fhutuidaren.sinaapp.com%2F&response_type=code&forcelogin=false");
		submitOauth(fd);
		// 推狗
		fd.get("https://api.weibo.com/oauth2/authorize?client_id=1371273153&redirect_uri=http%3A%2F%2Fwww.tlianmeng.com%2Fwelcome%2Fsinaband%2F&response_type=code");
		submitOauth(fd);
		// 推客啦
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=3551150405&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Footeach%2F");
		submitOauth(fd);
		// 加加米
		fd.get("https://api.weibo.com/oauth2/authorize?client_id=245891426&redirect_uri=http%3A%2F%2Fqiuzf.sinaapp.com%2Fcallback.php&response_type=code&state=sina");
		submitOauth(fd);

		// 美丽传说可爱多
		fd.get("http://api.t.sina.com.cn/oauth/authorize?oauth_token=d95acb84a559e77bb0026de80cbf986b&oauth_callback=http%3A%2F%2Fweibo0805.sinaapp.com%2Fcallback.php");
		submitOauth(fd);

		// 皮皮时光机
		fd.get("http://weibo.pp.cc/member.php?mod=login&action=register&app=weitu&type=sina");
		if(WebDriverUtil.hasElement(fd, By.id("username_register"))){
			regPP(fd,username, password);
		}
		fd.get("https://api.weibo.com/oauth2/authorize?client_id=1967296247&redirect_uri=http%3A%2F%2Fweibo.pp.cc%2Fmember.php%3Fmod%3Dbind%26action%3Daccess%26type%3Dsina%26app%3Dtime%26sinav%3D3&response_type=code");
		submitOauth(fd);
		return false;

	}
	
	//有验证码通过不了
	private static void regPP(WebDriver fd, String username, String password) {
		fd.findElement(By.id("username_register")).sendKeys(username.substring(0, username.indexOf("@")));
		fd.findElement(By.id("password_register")).sendKeys(password);
		fd.findElement(By.id("password2_register")).sendKeys(password);
		fd.findElement(By.id("email_register")).sendKeys(username);
		
	}

	private static void submitOauth(WebDriver fd) {
		By by = By.cssSelector("a[action-type=\"submit\"]");
		if (WebDriverUtil.hasElement(fd, by)) {
			fd.findElement(by).click();
		}
		 by = By.id("sub");
		if (WebDriverUtil.hasElement(fd, by)) {
			fd.findElement(by).click();
		}
	}
}
