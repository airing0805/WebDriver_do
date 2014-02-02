package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.XMLConfig;

public class DriverWeiboQQ {

	StringBuffer messageBuffer = new StringBuffer();

	public DriverWeiboQQ() {
		super();
	}

	public void getMessageFlow(WebDriver fd, int weiboNum) throws ConfigurationException, InterruptedException {
		for (int i = 0; i < weiboNum; i++) {
			long start = System.currentTimeMillis();
			
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").pp_username");
			System.out.println(username);
			String path = System.getProperty("user.dir") + File.separator + username + ".txt";
			List<Object> addressList = XMLConfig.getConfig().getList("weibo(" + i + ").pp_address.QQAddress");
			new File(path).delete();
			for (int j = 0; j < addressList.size(); j++) {
				String url = XMLConfig.getConfig().getString("weibo(" + i + ").pp_address.QQAddress(" + j + ")");
				fd.get(url);
				String message = getMessage(fd, 1);
				System.out.println(message);
				FileUtil.write2FileEnd(path, message);
				// Thread.sleep(3000);
			}
			
			long end = System.currentTimeMillis();
			long total = end - start;
			System.out.println(total);
		}
	}

	// 从qq获取微博内容，没有水印
	private String getMessage(WebDriver fd, int startPage) throws InterruptedException, ConfigurationException {
		By closeLoginBy = By.cssSelector("div[class=\"DWrap\"] > a.DClose.close");
		WebElement closeLoginElement = WebDriverUtil.findElement4Wait(fd, closeLoginBy, 2);
		if (null != closeLoginElement) {
			closeLoginElement.click();
		}
		// 点击原创等待加载
		By originalBy = By.xpath("//*[@id=\"userAppTab\"]/ul/li[2]/a");
		WebElement originalWe = WebDriverUtil.findElement4Wait(fd, originalBy, 1);
		if (null != closeLoginElement) {
			String text = originalWe.getText();
			if (text.contains("原创")) {
				originalWe.click();
			}
		}
		Thread.sleep(500);
		List<WebElement> messageLiList = fd.findElements(By.cssSelector("ul[id=\"talkList\"] > li"));
		for (int i = 0; i < messageLiList.size(); i++) {
			WebElement messageLi = messageLiList.get(i);
			if (hasAdvert(messageLi)) {
				continue;
			}
			WebElement messageDiv = messageLi.findElement(By.cssSelector("div[class=\"msgCnt\"]"));
			String message = messageDiv.getText().replace("\"", "“");
			// 有链接的都不要。。。
			if (message.toLowerCase().contains("url.cn")) {
				continue;
			}
			messageBuffer.append(message + "~laiqian~");
			WebElement picDiv = WebDriverUtil.findElement4Wait(messageLi, By.cssSelector("div[class=\"mediaWrap\"] > div > a.pic"), 1);
			String picHref = "";
			if (null != picDiv) {
				picHref = picDiv.getAttribute("href");
			}
			messageBuffer.append("" + picHref + "~mashang~");
		}
		doNextPage(fd, startPage);
		return messageBuffer.toString();
	}

	private void doNextPage(WebDriver fd, int startPage) throws ConfigurationException, InterruptedException {
		int pageConf = Integer.parseInt(XMLConfig.getConfig().getString("QQSpiderPage"));
		if (pageConf != startPage) {
			WebElement pageNavWe = fd.findElement(By.xpath("//*[@id=\"pageNav\"]"));
			List<WebElement> pageLinkWeList = pageNavWe.findElements(By.tagName("a"));
			int nextPageNum = startPage + 1;
			for (WebElement pageLinkWe : pageLinkWeList) {
				String text = pageLinkWe.getText();
				if (text.contains("上一页") || text.contains("下一页")) {
					continue;
				}
				if (nextPageNum == Integer.parseInt(text)) {
					String nextLink = pageLinkWe.getAttribute("href");
					fd.get(nextLink);
					break;
				}
			}
			getMessage(fd, nextPageNum);
		}

	}

	private boolean hasAdvert(WebElement messageLi) throws ConfigurationException {
		By replyBy = By.cssSelector("div[class=\"replyBox\"]");
		if (WebDriverUtil.hasElement(messageLi, replyBy))
			return true;
		String messageLiStr = messageLi.toString();
		List<Object> advertList = XMLConfig.getConfig().getList("advert.value");
		for (Object advertValue : advertList) {
			if (messageLiStr.toLowerCase().contains(advertValue.toString().toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}
