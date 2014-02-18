package com.andy.weiboDriver.doMain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class InitApps {
	private static Logger logger = Logger.getLogger( InitApps.class);
	
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
					logger.info(sf1.format(new Date()));
					path += username + sf.format(new Date()) + "_init.txt";
					String fileMess = username + "\n";
					FileUtil.write2FileEnd(path, fileMess);
					new WeiboSina().login(fd, username, password);
					
					
					selectInterest(fd);
					
					Thread.sleep(0);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	public static void selectInterest(WebDriver fd) throws InterruptedException{
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
	}
	
	public static boolean addApp(WebDriver fd){
		fd.get("http://app.weibo.com/my");
		//死亡用户清除
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=1050823112&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Fkilldie");
		//取消关注管理
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=2801105842&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Fcancels");
		
		
		//互粉加加
		fd.get("https://api.weibo.com/oauth2/authorize?client_id=245891426&redirect_ur…ttp%3A%2F%2Fqiuzf.sinaapp.com%2Fcallback.php&response_type=code&state=sina");
		//推兔
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=4071554311&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Ftuituoo");
		//推米
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=2372363467&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Ftuimimi");
		//互推联盟		
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=2819702316&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Fwbhutui");
		//互粉赏金榜
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=4227115430&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Ffansreward%2F");
		
		//优推推互粉
		fd.get("https://api.weibo.com/oauth2/authorize?response_type=token&client_id=3904763163&redirect_uri=http%3A%2F%2Fapps.weibo.com%2Fzhuanqi");
		//互推达人
		fd.get("https://api.weibo.com/oauth2/authorize?client_id=1040961737&redirect_uri=http%3A%2F%2Fhutuidaren.sinaapp.com%2F&response_type=code&forcelogin=false");
		//推狗
		//推客啦
		//加加米
		
		//皮皮时光机
		
		//美丽传说可爱多
		
		
		
		
		
		return false;
		
		
	}

}
