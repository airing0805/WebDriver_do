package com.andy.weiboDriver.webDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GetUserFromHuaTi {


	public String getUrl(WebDriver fd,String url) throws InterruptedException {
		fd.get(url);
		
//		<li action-data="url=/32368?from=home_content_topic&amp;tid=32368&amp;num=495760&amp;ctg1=99&amp;topic=中国好歌曲" action-type="show-topic" class="list_1 clearfix                           hover">
//        <div class="num"><span></span></div>
//    <div class="main">
//<div class="hd">
//<a href="/32368?from=home_content_topic" class="name" target="_blank" title="中国好歌曲">#中国好歌曲#</a>
//                                                                                <span class="text W_textc">《中国好歌曲》今晚19:30央…</span>
//                                                            <a class="place" href="javascript:void(0);" action-type="main-list" action-data="ctg1=102&amp;ctg2=0&amp;prov=0&amp;sort=time&amp;p=1">电视节目</a>
//                </div>
//            <div class="info clearfix">
//<div class="process">
//    <span class="solid" style="width:100%"></span>
//    <span class="empty" style="width:0%"></span>
//</div>
//<div class="state"><span class="num_info">495760</span>
//                    <span class="icon_hotrank" action-type="show-hot"></span>
//                    </div>
//</div>
//        </div>
//
//<div class="presenter_name"><a action-data="uid=3577435414" action-type="usercard" target="_blank" href="http://weibo.com/thesongofchina">中国好歌曲<i class="approve_co" title="新浪机构认证"></i></a></div>
//</li>
		Thread.sleep(5000);
		WebElement  we = fd.findElement(By.cssSelector("div[node-type=\"list-small\"]")).findElements(By.tagName("li")).get(0).findElement(By.cssSelector("a[class=\"name\"]"));
		
		we.click();
		return null;
	}

}
