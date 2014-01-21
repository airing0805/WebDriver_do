package com.andy.weiboDriver.webDriver;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class WeiboSendAtPP {

	String[][] messArr = {
			{"晒幸福是件危险的事，可惜很多人不明白，往死里晒。",
			"http://t3.qpic.cn/mblogpic/c2ed59a7f8ef38189566/460"},
			{"多心的人注定活得辛苦，因为太容易被別人的情绪所左右。",
			"http://t3.qpic.cn/mblogpic/ea87d67452e2ebb6d666/460"},
			{"其实任何一个人离开我们的生活，生活始终都还在继续。没有人必须为我们停留。我们也不会为任何人停留。想清楚了。",
			"http://t3.qpic.cn/mblogpic/d23cab3a07df4d8e13d4/460"},
			{"#世界好歌曲# 唉，众口难调。",
			"http://t3.qpic.cn/mblogpic/a7f30c8956952e32b3dc/460"},
			{"黑鱼群~~~现在很少见了！！",
			"http://t3.qpic.cn/mblogpic/ddc6ec7bb0ccd3df1394/460"},
			{"朋友造了一把枪，说要去抢银行，我该怎么劝他…",
			"http://t3.qpic.cn/mblogpic/ccd87230f3816d52b8f2/460"},
			{"哈哈。。高级黑色，普京也中枪了！！",
			"http://t3.qpic.cn/mblogpic/bd998e5213fd912b83aa/460"},
			{"你最羡慕哪种特异体质的人：1、不管怎么吃都不胖；2、总能吸引异性的注意；3、买彩票总能中奖；4、皮肤又白又嫩；5、总能得到别人的帮助；6、特别有气场；7、天生就很有钱，特别有钱...(´｡･д人)(颜文字君)",
			"http://t3.qpic.cn/mblogpic/5b872f4b9b0df34cc320/460"},
			{"不知道不要紧，但知道了会很屌！",
			"http://t3.qpic.cn/mblogpic/460406374b74587e6512/460"},
			{"天气变冷，人也容易赖床。一同事赖床时，迷迷糊糊听到有个小姑娘的声音，“行行好吧，行行好嘛……”同事顺口说了一句，“给你一块钱，快走吧。”紧接着，便被老婆一脚踹醒，“让你起个床，哪那么多废话”。醒醒好嘛……",
			"http://t3.qpic.cn/mblogpic/f819d5ae40fd32821e8e/460"},
			{"真正相爱的人是不需要无时不刻在一起的，他们需要的是精神上的沟通和默契，是对彼此心灵的助长和赞许。他们尊重对方的生活方式，宽容以待；他们同时拥有自己的人格和世界，不容破坏。他们相容，并且相让。过分的依赖，特别是对他人身体、时间、空间、物质的无度占据，不是爱，是身心不独立的表现。",
			"http://t3.qpic.cn/mblogpic/4ada291155bdebe5d3a0/460"},
			{"你有过这样的经历吗？",
			"http://t3.qpic.cn/mblogpic/ddfa80f72b918e86be90/460"},
			{"尼玛，，这什么时代，这么高科技？？",
			"http://t3.qpic.cn/mblogpic/2a67150f9f34e63ae966/460"},
			{"王二小河边砍树，把铁斧掉进河里，急得哭了起来。忽然，水里传出一个苍老的声音：孩子你掉的是金斧子吗？王二小对着水里喊：不是的！水里的声音又道：那你掉的是银斧子吗？二小又说不是。这时，水里浮起一个额头顶着一把铁斧，满脸是血的老神仙，黑着脸吼道：这么说砍伤我的真的是你个混蛋咯！",
			"http://t3.qpic.cn/mblogpic/c111fb276f1b093b5590/460"},
			{"回龙观震惊一幕，360你这么碉堡，12306知道么？",
			"http://t3.qpic.cn/mblogpic/db8fc1e86929acc2b940/460"},
			{"一个女人有一晚没回家 隔天她跟老公说他睡在一个女性朋友那，她老公打电话给她最好的10个朋友，没有一个知道这件事！一个男人有一晚没回家睡，隔天他跟老婆说他睡在一个兄弟那边她老婆打电话给他最好的10个朋友，有八个好兄弟确定他老公睡在他们家……还有2个说“他老公还在他那儿",
			"http://t3.qpic.cn/mblogpic/bdbc2123b4b24df77800/460"},
			{"有一种遗忘的方式叫：眼不见为净，耳不听为静。",
			"http://t3.qpic.cn/mblogpic/e556ac97717453d52a06/460"},
			{"晚上出去遛狗，前面有个女的在路边买东西掏钱包掉了一块钱纸币，人家还没来得及捡，我家二货狗百米冲刺的速度奔过去叼起来那一块钱，扭头再冲刺回来到我面前，放下一块钱，张着嘴吐着舌头一脸求奖励的表情。 那女的都石化了……我想说我不认识它！！",
			"http://t3.qpic.cn/mblogpic/ca6d981876a41ca7ee1a/460"},
			{"昨晚去吃火锅，当场就给吓跑了。",
			"http://t3.qpic.cn/mblogpic/be26ab69253cead93634/460"}};

	 @Test
	public void faWeiFlowT() throws InterruptedException {
		WebDriver fd = new FirefoxDriver();
		long start = System.currentTimeMillis();
		login(fd);
		Thread.sleep(1000);
		loginPP4Oauth(fd);
		iterateMessage(fd);
		long end = System.currentTimeMillis();
		long total = end - start;
		System.out.println(total);

		// fd.quit();
	}
	
	public void iterateMessage(WebDriver fd) throws InterruptedException{
		fd.findElement(By.id("timer_diff_1")).sendKeys("25");;
		for(int i=0 ;i<messArr.length;i++){
			ppSendTime(fd, messArr[i][0], messArr[i][1]);
			Thread.sleep(500);
		}
	}
	
	public void ppSendTime(WebDriver fd,String message,String picUrl) throws InterruptedException{
		//显示图片提示方式
		((JavascriptExecutor) fd).executeScript("$('#show_pic_list_1').show();");
		Thread.sleep(100);
		//显示图片提示方式
		WebElement picMoveWe2 = fd.findElement(By.cssSelector("span[id=\"span_open_pic_1\"]"));
		if(!picMoveWe2.isDisplayed()){
			picMoveWe2.sendKeys(Keys.DOWN);
			new Actions(fd).moveToElement(picMoveWe2).build().perform();
		}
		Thread.sleep(50);
		WebElement picLink  = WebDriverUtil.findElement4Wait(fd, By.cssSelector("a[id=\"open_pic_url_1\"]"),1);
		if(null != picUrl && picUrl.length()>0){
			//显示图片提示窗口
			picLink.click();
			Thread.sleep(10);
			//输入图片地址
			WebElement picLinkInput = fd.findElement(By.cssSelector("input[id=\"pic_url_1\"]"));
			picLinkInput.sendKeys(picUrl);
			Thread.sleep(10);
			//提交图片
			WebElement picLinkSubmit = fd.findElement(By.cssSelector("input[id=\"pic_url_send_1\"]"));
			picLinkSubmit.click();
			Thread.sleep(300);
		}
		//填微博
		WebElement MessageWe = fd.findElement(By.cssSelector("textarea[id=\"content_1\"]"));
		MessageWe.sendKeys(message);
		MessageWe.click();
		new Actions(fd).moveToElement(MessageWe).build().perform();
		//提交
		WebElement messageTimeSendBut = fd.findElement(By.cssSelector("input[id=\"button_1\"]"));
		messageTimeSendBut.click();
		//要给图片提交留一点时间
		//等待重复内容的提示
		Thread.sleep(3000);
		WebElement closeHas = WebDriverUtil.findElement4Wait(fd,By.cssSelector("a[id=\"dialog_close\"]"),1);
		if(closeHas.isDisplayed() && null != closeHas){
			closeHas.click();
			WebElement picClose = fd.findElement(By.cssSelector("strong[id=\"insert_picture_preview_1\"] > a"));
			if(picClose.isDisplayed()){
				picClose.click();
			}
		}
		MessageWe.clear();
	}
	
	public void ppTime(WebDriver fd,String text){
		Calendar calendar = Calendar.getInstance();
		Select select = new Select(fd.findElement(By.cssSelector("select[id=\"hour_1\"]")));
		select.selectByVisibleText(text);
	}
	
	public void loginPP4Oauth(WebDriver fd) throws InterruptedException{
		String ppUrl = "http://weibo.pp.cc/";
		fd.get(ppUrl);
		Thread.sleep(1000);
		String currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		WebElement sinaLoginWe = fd.findElement(By.cssSelector("a[id=\"account_login\"]"));
		sinaLoginWe.click();
		Thread.sleep(1000);
		currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		String ppOauthUrl = "https://api.weibo.com/oauth2/authorize?client_id=1967296247&redirect_uri=http%3A%2F%2Fweibo.pp.cc%2Fmember.php%3Fmod%3Dbind%26action%3Daccess%26type%3Dsina%26app%3Dtime%26sinav%3D3&response_type=code";
		fd.get(ppOauthUrl);
		WebElement userIdWe = fd.findElement(By.cssSelector("input[id=\"userId\"]"));
		userIdWe.sendKeys("yitest0805@sina.com");
		WebElement passwdWe = fd.findElement(By.cssSelector("input[id=\"passwd\"]"));
		passwdWe.sendKeys("andy0805");
		WebElement submitWe = fd.findElement(By.cssSelector("a[node-type=\"submit\"]"));
		submitWe.click();
	}

	//点击新浪登录
	public void loginPP4click(WebDriver fd) throws InterruptedException {
		String ppUrl = "http://weibo.pp.cc/";
		fd.get(ppUrl);
		String currentUrl = fd.getCurrentUrl();
		if (currentUrl.contains("time")) {
			return;
		}
		Thread.sleep(1000);
		WebElement sinaLoginWe = fd.findElement(By.cssSelector("a[id=\"account_login\"]"));
		sinaLoginWe.sendKeys(Keys.DOWN);
		sinaLoginWe.click();
		Thread.sleep(1000);
		if (currentUrl.contains("time")) {
			System.out.println("contains time");
			return;
		}
		String timeUrl = "http://weibo.pp.cc/time/";
		fd.get(timeUrl);
	}

	// 登录到新浪微博
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
