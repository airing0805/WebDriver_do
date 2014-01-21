package com.andy.weiboDriver.test.webDriver;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.andy.weiboDriver.webDriver.GetUserFromFans;
import com.andy.weiboDriver.webDriver.GetUserFromHuaTi;
import com.andy.weiboDriver.webDriver.SendMessageFlow;

public class SinaWebDriverT {

//	 @Test
	public void faWeiFlowT() throws InterruptedException {
		WebDriver fd = new FirefoxDriver();
		login(fd);
		String message = "今天天气不错";
		long start = System.currentTimeMillis();
		new SendMessageFlow().SendMessage(fd, message);
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println(total);

		// fd.quit();
	}

	@Test
	public void sendMessT() throws InterruptedException {
		WebDriver fd = new FirefoxDriver();
		login(fd);
		Thread.sleep(5000);
		String url = "http://weibo.com/u/3944924952";
		String message = "亲，粉一个";
		String[] urlArr = {  "http://weibo.com/u/1828769150", "http://weibo.com/u/3454273352", "http://weibo.com/u/2611616975", "http://weibo.com/u/3919462411", "http://weibo.com/u/3888969482", "http://weibo.com/u/3192196065", "http://weibo.com/u/3921086526", "http://weibo.com/u/3921176893", "http://weibo.com/u/3904667553", "http://weibo.com/u/3221660905", "http://weibo.com/u/3855333813", "http://weibo.com/u/2174026615",
				"http://weibo.com/u/2850693120", "http://weibo.com/u/3882756718", "http://weibo.com/u/3894177877", "http://weibo.com/u/3862042747", "http://weibo.com/u/3885017170", "http://weibo.com/u/3902892670", "http://weibo.com/u/3881618626", "http://weibo.com/u/2736336555", "http://weibo.com/u/3314886781", "http://weibo.com/u/3880118546", "http://weibo.com/u/2661034073", "http://weibo.com/u/3863593153", "http://weibo.com/u/3887762277", "http://weibo.com/u/2524380085",
				"http://weibo.com/u/3824506432", "http://weibo.com/u/3317417751", "http://weibo.com/u/3879388460", "http://weibo.com/u/3849013654", "http://weibo.com/u/3508946502", "http://weibo.com/715768789", "http://weibo.com/u/3143208524", "http://weibo.com/u/1873709667", "http://weibo.com/dwkjmstt", "http://weibo.com/u/2134470773", "http://weibo.com/linshichongzhi", "http://weibo.com/u/3557988282", "http://weibo.com/u/2018473351", "http://weibo.com/u/3822848507",
				"http://weibo.com/u/2500399207", "http://weibo.com/u/3834862434", "http://weibo.com/732862456", "http://weibo.com/u/2475810193", "http://weibo.com/u/3054021787", "http://weibo.com/u/3807469453", "http://weibo.com/u/1358984851", "http://weibo.com/u/3801359101", "http://weibo.com/u/3760280047", "http://weibo.com/u/3291232251", "http://weibo.com/u/3789460642", "http://weibo.com/u/3413903440", "http://weibo.com/u/3763838147", "http://weibo.com/u/3759202605",
				"http://weibo.com/u/3613270244", "http://weibo.com/u/3266311920", "http://weibo.com/u/3759827517", "http://weibo.com/u/3031073560", "http://weibo.com/u/2255928255", "http://weibo.com/u/3778126437", "http://weibo.com/u/3761070064", "http://weibo.com/u/3113075425", "http://weibo.com/u/2603068265", "http://weibo.com/zhouyanjun1119", "http://weibo.com/u/2119372834", "http://weibo.com/u/1910982541", "http://weibo.com/u/2786920113", "http://weibo.com/u/3692703631",
				"http://weibo.com/u/3192883860", "http://weibo.com/u/1028347593", "http://weibo.com/u/2387023620", "http://weibo.com/u/3660886771", "http://weibo.com/u/3509218912", "http://weibo.com/u/3154775524", "http://weibo.com/u/3648927964", "http://weibo.com/shuishoudao", "http://weibo.com/u/2495258347", "http://weibo.com/yangli11134", "http://weibo.com/u/2171373593", "http://weibo.com/u/3616624077", "http://weibo.com/u/3625912331", "http://weibo.com/u/2762102160",
				"http://weibo.com/u/1853008973", "http://weibo.com/u/2830954097", "http://weibo.com/u/1566950694", "http://weibo.com/u/1659303201", "http://weibo.com/531012550", "http://weibo.com/u/3602908463", "http://weibo.com/u/2726391617", "http://weibo.com/u/3490939485", "http://weibo.com/u/2580348367", "http://weibo.com/u/3580201684", "http://weibo.com/u/3619685354", "http://weibo.com/u/3494840575", "http://weibo.com/u/3493562650", "http://weibo.com/u/3001996615",
				"http://weibo.com/u/2549546877", "http://weibo.com/u/3186590344", "http://weibo.com/u/2940547391", "http://weibo.com/u/3362883234", "http://weibo.com/u/1163319030", "http://weibo.com/u/2664511961", "http://weibo.com/tingtingxiaozhu", "http://weibo.com/u/2085363063", "http://weibo.com/u/3501950230", "http://weibo.com/u/3538428963", "http://weibo.com/u/3253486430", "http://weibo.com/u/3320846817", "http://weibo.com/u/3181562567", "http://weibo.com/u/3248344023",
				"http://weibo.com/u/2667150551", "http://weibo.com/u/3319653915", "http://weibo.com/u/3480536550", "http://weibo.com/u/1308802493", "http://weibo.com/u/1458461131", "http://weibo.com/u/3246327640", "http://weibo.com/u/2962665697", "http://weibo.com/u/3296925560", "http://weibo.com/u/1671284485", "http://weibo.com/u/2216652977", "http://weibo.com/u/1013361937", "http://weibo.com/u/2539154453", "http://weibo.com/u/2751655957", "http://weibo.com/u/2540216574",
				"http://weibo.com/u/3319300823", "http://weibo.com/u/1840473514", "http://weibo.com/u/3275968515", "http://weibo.com/u/1800548631", "http://weibo.com/u/3256831360", "http://weibo.com/u/2646600972", "http://weibo.com/u/3319981071", "http://weibo.com/u/2737250461", "http://weibo.com/u/2907410855", "http://weibo.com/u/3299288587", "http://weibo.com/u/3296902811", "http://weibo.com/u/3097664357", "http://weibo.com/jeremicah", "http://weibo.com/u/2609932800",
				"http://weibo.com/730010345", "http://weibo.com/u/1905467920", "http://weibo.com/u/3323158370", "http://weibo.com/u/1433642132", "http://weibo.com/u/3225073240", "http://weibo.com/u/3310184232", "http://weibo.com/u/1762136811", "http://weibo.com/u/2106033093", "http://weibo.com/u/2343966395", "http://weibo.com/u/2963502803", "http://weibo.com/u/3243187782", "http://weibo.com/u/2664891671", "http://weibo.com/u/3229983644", "http://weibo.com/u/1661768914",
				"http://weibo.com/u/3199169244", "http://weibo.com/u/2383950431", "http://weibo.com/u/2492108254", "http://weibo.com/u/2611252843", "http://weibo.com/u/2940116827", "http://weibo.com/u/3263877600", "http://weibo.com/u/3041798811", "http://weibo.com/u/3263009030", "http://weibo.com/u/3172101352", "http://weibo.com/516357456" };
		for (String urlA : urlArr) {
			Thread.sleep(30000);
			long start = System.currentTimeMillis();
			new SendMessageFlow().SendPrivateLetterAtUserIndex(fd, urlA, message);
			long end = System.currentTimeMillis();
			long total = end - start;
			System.out.println(total);
		}
		// fd.quit();
	}


	// @Test
	public void getUserFromFans() throws InterruptedException {
		WebDriver fd = new FirefoxDriver();
		login(fd);
		Thread.sleep(5000);
		String url = "http://weibo.com/u/2789760420";
		String message = "你好";
		long start = System.currentTimeMillis();
		new GetUserFromFans(fd, url);
		// new SendPrivateLetterAtSelfIndex(fd,url,message);
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println(total);
		// fd.quit();
	}

	// @Test
	// TODO 话题居然加载不出来
	public void GetUserFromHuaTiT() throws InterruptedException {
		WebDriver fd = new FirefoxDriver();
		login(fd);
		Thread.sleep(5000);
		String url = "http://huati.weibo.com/";
		String message = "你好";
		long start = System.currentTimeMillis();
		String userUrl = new GetUserFromHuaTi().getUrl(fd, url);
		// new SendPrivateLetterAtSelfIndex(fd,userUrl,message);
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println(total);
		// fd.quit();
	}

	public void login(WebDriver fd) {
		fd.get("http://www.weibo.com");
		List<WebElement> webElementList = fd.findElements(By.tagName("input"));
		for (WebElement we : webElementList) {
			boolean flag1 = "username".equals(we.getAttribute("name"));
			boolean flag2 = "username".equals(we.getAttribute("node-type"));
			if (flag1 && flag2) {
				we.sendKeys("yitest0805@sina.com");
			}
			boolean flag3 = "password".equals(we.getAttribute("name"));
			boolean flag4 = "password".equals(we.getAttribute("node-type"));
			if (flag3 && flag4) {
				we.sendKeys("andy0805");
				break;
			}
		}
		List<WebElement> aList = fd.findElements(By.tagName("a"));
		for (WebElement we : aList) {
			boolean flag2 = "submitBtn".equals(we.getAttribute("node-type"));
			if (flag2) {
				we.click();
				break;
			}
		}
	}

}
