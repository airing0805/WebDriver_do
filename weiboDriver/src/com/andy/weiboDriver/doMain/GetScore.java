package com.andy.weiboDriver.doMain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.fansApp.HuFenBang;
import com.andy.weiboDriver.fansApp.HuTuiLianMeng;
import com.andy.weiboDriver.fansApp.Qiuzf;
import com.andy.weiboDriver.fansApp.Tuimi;
import com.andy.weiboDriver.fansApp.Tuitu;
import com.andy.weiboDriver.util.Threads;
import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class GetScore {

	private static Logger logger = Logger.getLogger(GetScore.class);

	public static void main(String[] args) throws ConfigurationException, InterruptedException {

		List<Object> weiboList = XMLConfig.getConfig().getList("weibo.weibo_username");
		String firefoxRun = XMLConfig.getConfig().getString("firefoxRun");
		int weiboNum = weiboList.size();
		WebDriver fd = null;
		if ("false".equals(firefoxRun)) {
			fd = new HtmlUnitDriver();
		} else {
			fd = new FirefoxDriver();
		}

		iterateGetScore(fd, weiboNum);

		fd.quit();
	}

	// TODO对于一键关注16个的应用要区分标注出来，半小时的限制会突破30
	public static void iterateGetScore(WebDriver fd, int weiboNum) {
		List<String> appList = new ArrayList<String>();
		// String nextStartAppName = "Tuitu";
		appList.add("Tuitu");
		appList.add("Qiuzf");
		appList.add("Tuimi");
		appList.add("HuTuiLianMeng");
		appList.add("HuFenBang");
		while (true) {
			int startDay = XMLConfig.getConfig().getInt("startDay");
			SimpleDateFormat sf = new SimpleDateFormat("dd");
			if (startDay == Integer.parseInt(sf.format(new Date()))) {
				break;
			} else {
				logger.info("等待5分钟。。。");
				Threads.sleep(1000 * 60 * 5);
			}
		}
		
		for (int i = 0; i < weiboNum; i++) {
			while (true) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				int num = 0;
				int numT = 0;
				String username = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_username");
				String password = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_password");
				String weiboUrl = XMLConfig.getConfig().getString("weibo(" + i + ").weibo_url");
				SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logger.info(sf1.format(new Date()));
				String fileMess = username + "\n";
				logger.info(fileMess);
				new WeiboSina().login(fd, username, password);
				map = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
				num = map.get("关注");

				// 如果关注的数量只比2000少70以内，那么删除相关的关注用户
				if (2000 - num < 70) {
					logger.info("关注人数接近2000了");
					Map<String, String> weiboMap = new HashMap<String, String>();
					weiboMap.put(username, password);
					DelAttentions.delDeadAttentions(fd);
					//删除死亡关注后，要等一段时间
					Threads.sleep(1000);
					DelAttentions.delEarliestAttentions(fd);
				}
				// 一键关注最多只到十页，有一页成功就退出
				// boolean flag = true;
				// int aa = appList.indexOf(nextStartAppName);
				boolean flag = false;

				try {
					// 推兔一键最多12个
					// http://apps.weibo.com/tuituoo
					logger.info("start:" + sf1.format(new Date()));
					flag = new Tuitu().getScoreFlow(fd);
					map = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					numT = map.get("关注");
					fileMess = sf1.format(new Date()) + "_总关注：" + numT + "_总粉丝：" + map.get("粉丝") + " _本次关注:" + (numT - num) + "\n";
					logger.info(fileMess);
					num = numT;
					// 关注过多，
					if (!flag) {
						logger.info("关注之后，测试删除");
						DelAttentions.delDeadAttentions(fd);
						break;
					}
				} catch (Exception e) {
					WebDriverUtil.takeScreenShot(fd);
					logger.info(e.getMessage(), e);
				}
				Threads.sleep(1000 * 60 * 16);

				try {
					// 互粉加加 一键一页最多7个
					logger.info("start:" + sf1.format(new Date()));
					flag = new Qiuzf(2).getScoreFlow(fd);
					map = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					numT = map.get("关注");
					fileMess = sf1.format(new Date()) + "_总关注：" + numT + "_总粉丝：" + map.get("粉丝") + " _本次关注:" + (numT - num) + "\n";
					logger.info(fileMess);
					num = numT;
					// 关注过多，
					if (!flag) {
						logger.info("关注之后，测试删除");
						DelAttentions.delDeadAttentions(fd);
						break;
					}
				} catch (Exception e) {
					WebDriverUtil.takeScreenShot(fd);
					logger.info(e.getMessage(), e);
				}
				Threads.sleep(1000 * 60 * 16);

				try {
					// 推米 一键一页最多7个
					logger.info("start:" + sf1.format(new Date()));
					flag = new Tuimi(2).getScoreFlow(fd);
					map = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					numT = map.get("关注");
					fileMess = sf1.format(new Date()) + "_总关注：" + numT + "_总粉丝：" + map.get("粉丝") + " _本次关注:" + (numT - num) + "\n";
					logger.info(fileMess);
					num = numT;
					// 关注过多，
					if (!flag) {
						logger.info("关注之后，测试删除");
						DelAttentions.delDeadAttentions(fd);
						break;
					}
				} catch (Exception e) {
					WebDriverUtil.takeScreenShot(fd);
					logger.info(e.getMessage(), e);
				}
				Threads.sleep(1000 * 60 * 16);

				try {
					// 互推联盟 一键一页最多12个
					logger.info("start:" + sf1.format(new Date()));
					flag = new HuTuiLianMeng().getScoreFlow(fd);
					map = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					numT = map.get("关注");
					fileMess = sf1.format(new Date()) + "_总关注：" + numT + "_总粉丝：" + map.get("粉丝") + " _本次关注:" + (numT - num) + "\n";
					logger.info(fileMess);
					num = numT;
					if (!flag) {
						logger.info("关注之后，测试删除");
						DelAttentions.delDeadAttentions(fd);
						break;
					}
				} catch (Exception e) {
					WebDriverUtil.takeScreenShot(fd);
					logger.info(e.getMessage(), e);
				}
				Threads.sleep(1000 * 60 * 16);

				try {
					// 互粉赏金榜一键一页最多16个
					logger.info("start:" + sf1.format(new Date()));
					flag = new HuFenBang().getScoreFlow(fd);
					map = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					numT = map.get("关注");
					fileMess = sf1.format(new Date()) + "_总关注：" + numT + "_总粉丝：" + map.get("粉丝") + " _本次关注:" + (numT - num) + "\n";
					logger.info(fileMess);
					num = numT;
					if (!flag) {
						logger.info("关注之后，测试删除");
						DelAttentions.delDeadAttentions(fd);
						break;
					}
				} catch (Exception e) {
					WebDriverUtil.takeScreenShot(fd);
					logger.info(e.getMessage(), e);
				}
				Threads.sleep(1000 * 60 * 16);
			}
		}
	}

}
