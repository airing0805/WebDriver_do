package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.Threads;
import com.andy.weiboDriver.util.XMLConfig;

public class DriverWeiboQQ {
	
	private static Logger logger = Logger.getLogger(  DriverWeiboQQ.class);
	
	public DriverWeiboQQ() {
		super();
	}

	public void getMessageFlow(WebDriver fd, int weiboNum) throws ConfigurationException, InterruptedException {
		for (int i = 0; i < weiboNum; i++) {
			long start = System.currentTimeMillis();
			
			String username = XMLConfig.getConfig().getString("weibo(" + i + ").pp_username");
			logger.info(username);
			String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String path = System.getProperty("user.dir") + File.separator + username + "-"+day+".txt";
			List<Object> addressList = XMLConfig.getConfig().getList("weibo(" + i + ").pp_address.QQAddress");
			new File(path).delete();
			for (int j = 0; j < addressList.size(); j++) {
				String url = XMLConfig.getConfig().getString("weibo(" + i + ").pp_address.QQAddress(" + j + ")");
				logger.info(url);
				fd.get(url);
				int pageConf = Integer.parseInt(XMLConfig.getConfig().getString("QQSpiderPage"));
				for(int k =0;k<pageConf;k++){
					String message = getMessage(fd);
					logger.info(message);
					doNextPage(fd, k+1);
					FileUtil.write2FileEnd(path, message);
				}
			}
			
			long end = System.currentTimeMillis();
			long total = end - start;
			logger.info(total);
		}
	}

	// 从qq获取微博内容，没有水印
	private String getMessage(WebDriver fd) throws ConfigurationException {
		StringBuffer messageBuffer = new StringBuffer();
		By closeLoginBy = By.cssSelector("div[class=\"DWrap\"] > a.DClose.close");
		WebElement closeLoginElement = WebDriverUtil.getElementOrNot(fd, closeLoginBy);
		if (null != closeLoginElement && closeLoginElement.isDisplayed()) {
			closeLoginElement.click();
		}
		// 点击原创等待加载
		By originalBy = By.xpath("//*[@id=\"userAppTab\"]/ul/li[2]/a");
		WebElement originalWe = WebDriverUtil.findElement4Wait(fd, originalBy, 1);
		if (null != originalWe && originalWe.isDisplayed() ) {
			String text = originalWe.getText();
			if (text.contains("原创")) {
				originalWe.click();
			}
		}
		Threads.sleep(500);
		List<WebElement> messageLiList = fd.findElements(By.cssSelector("ul[id=\"talkList\"] > li"));
		for (int i = 0; i < messageLiList.size(); i++) {
			WebElement messageLi = messageLiList.get(i);
			//有转发内容的不要
			By replyBy = By.cssSelector("div[class=\"replyBox\"]");
			if (WebDriverUtil.hasElement(messageLi, replyBy))
				continue;
			//跨年的内容说明，跳过
			By messCnt = By.cssSelector("div[class=\"msgCnt\"]");
			boolean flag = WebDriverUtil.hasElement(messageLi, messCnt);
			if(!flag){
				continue;
			}
			WebElement messageDiv = messageLi.findElement(By.cssSelector("div[class=\"msgCnt\"]"));
			String message = messageDiv.getText().replace("\"", "“");
			// 有链接的不要。。。
			if (message.toLowerCase().contains("url.cn")) {
				continue;
			}
			//有广告内容的不要
			if (hasAdvert(messageLi)) {
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
		return messageBuffer.toString();
	}

	private void doNextPage(WebDriver fd, int startPage) throws ConfigurationException, InterruptedException {
		int pageConf = Integer.parseInt(XMLConfig.getConfig().getString("QQSpiderPage"));
		if (pageConf != startPage) {
			WebElement pageNavWe = fd.findElement(By.id("pageNav"));
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
		}
	}

	private boolean hasAdvert(WebElement messageLi) throws ConfigurationException {
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
