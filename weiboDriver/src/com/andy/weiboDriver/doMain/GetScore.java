package com.andy.weiboDriver.doMain;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.andy.weiboDriver.fansApp.HuFenBang;
import com.andy.weiboDriver.fansApp.HuTuiLianMeng;
import com.andy.weiboDriver.fansApp.Qiuzf;
import com.andy.weiboDriver.fansApp.Tuimi;
import com.andy.weiboDriver.fansApp.Tuitu;
import com.andy.weiboDriver.util.FileUtil;
import com.andy.weiboDriver.util.XMLConfig;
import com.andy.weiboDriver.webDriver.WebDriverUtil;
import com.andy.weiboDriver.webDriver.WeiboSina;

public class GetScore {
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

	//TODO对于一键关注16个的应用要区分标注出来，半小时的限制会突破30
	public static void iterateGetScore(WebDriver fd, int weiboNum) {
		List<String> appList = new ArrayList<String>();
		String nextStartAppName = "Tuitu";
		appList.add("Tuitu");
		appList.add("Qiuzf");
		appList.add("Tuimi");
		appList.add("HuTuiLianMeng");
		appList.add("HuFenBang");
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
					path += username + sf.format(new Date()) + ".txt";
					String fileMess = username + "\n";
					FileUtil.write2FileEnd(path, fileMess);
					new WeiboSina().login(fd, username, password);
					num = WebDriverUtil.getNumInfoAtUrl(fd,weiboUrl);
					// 一键关注最多只到十页，有一页成功就退出
					// boolean flag = true;
					int aa = appList.indexOf(nextStartAppName);
					boolean flag = false;
					
					//推兔一键最多12个
					System.out.println("start:" + sf1.format(new Date()));
					flag = new Tuitu().getScoreFlow(fd);
					numT = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					fileMess = sf1.format(new Date()) + "_总共关注：" + numT + " _本次关注:" + (numT - num) + "\n";
					System.out.println(fileMess);
					FileUtil.write2FileEnd(path, fileMess);
					num = numT;
					// 关注过多，
					if (!flag)
						return;
					Thread.sleep(1000 * 60 * 15);
					
					//互粉加加 一键一页最多7个
					System.out.println("start:" + sf1.format(new Date()));
					flag = new Qiuzf(2).getScoreFlow(fd);
					numT = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					fileMess = sf1.format(new Date()) + "_总共关注：" + numT + " _本次关注:" + (numT - num) + "\n";
					System.out.println(fileMess);
					FileUtil.write2FileEnd(path, fileMess);
					num = numT;
					Thread.sleep(1000 * 60 * 15);

					//推米 一键一页最多7个
					System.out.println("start:" + sf1.format(new Date()));
					flag = new Tuimi(2).getScoreFlow(fd);
					numT = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					fileMess = sf1.format(new Date()) + "_总共关注：" + numT + " _本次关注:" + (numT - num) + "\n";
					System.out.println(fileMess);
					FileUtil.write2FileEnd(path, fileMess);
					num = numT;
					Thread.sleep(1000 * 60 * 15);

					//互推联盟 一键一页最多12个
					System.out.println("start:" + sf1.format(new Date()));
					flag = new HuTuiLianMeng().getScoreFlow(fd);
					numT = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					fileMess = sf1.format(new Date()) + "_总共关注：" + numT + " _本次关注:" + (numT - num) + "\n";
					System.out.println(fileMess);
					FileUtil.write2FileEnd(path, fileMess);
					num = numT;
					if (!flag)
						return;
					Thread.sleep(1000 * 60 * 15);

					//互粉赏金榜一键一页最多16个
					System.out.println("start:" + sf1.format(new Date()));
					flag = new HuFenBang().getScoreFlow(fd);
					numT = WebDriverUtil.getNumInfoAtUrl(fd, weiboUrl);
					fileMess = sf1.format(new Date()) + "_总共关注：" + numT + " _本次关注:" + (numT - num) + "\n";
					System.out.println(fileMess);
					FileUtil.write2FileEnd(path, fileMess);
					num = numT;
					if (!flag)
						return;
					Thread.sleep(1000 * 60 * 15);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
