package com.andy.weiboDriver.webDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.andy.weiboDriver.util.FileUtil;

public class JsoupWeiboQQFile {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String url = "http://t.qq.com/zhichangyy";
		List<String> aa = new ArrayList<String>();
		 geturl(aa,url ) ;
		// System.out.println(doc.toString());
	}
	
	public static void  geturl(List<String> aa,String url ) throws IOException{
		Document doc = Jsoup.connect(url).get();
		Elements page = doc.select("[id=pageNav]");
		Elements a = page.get(0).select("a");
		aa.add(url);
		getMessage(doc,url);
		for (int i = 0; i < a.size(); i++) {
			String  text  = a.get(i).text();
			if(text.contains("下一页"))continue;
			String  href  = a.get(i).attr("href");
			String hrefAA = "http://t.qq.com/zhichangyy" + href ;
			if(!aa.contains(hrefAA)){
				geturl(aa,hrefAA );
			}
		}
	}

	public static void getMessage(Document doc,String url) throws IOException {
//		Document doc = Jsoup.connect(url).get();
		
		Elements els = doc.select("ul[id=talkList] > li");
		String aa = "";
		for (int i = 0; i < els.size(); i++) {
			
			Elements q1 = els.get(i).select("div[class=msgCnt]");
			if(null != q1 && q1.size() > 0){
				Element el = q1.get(0);
				aa += el.text() + "\n";
			}
			Elements q2 = els.get(i).select("div[class=mediaWrap] > div >a.pic");
			if(null != q2 && q2.size()  > 0 ){
				Element el2 =		q2.get(0);
				aa += el2.attr("href") + "\n";
				
			}
		}
		String fileName = "http://t.qq.com/zhichangyy".replace(":",	"_").replace("/", "_").replace("&", "_").replace("?", "_").replace("=", "_");
		String path = System.getProperty("user.dir") + File.separator + fileName + ".txt";
		FileUtil.write2FileEnd(path, aa);
	}

	public void demo() throws IOException {
		Document doc = Jsoup.connect("http://t.qq.com/zhichangyy").get();
		Elements els = doc.select("ul[id=talkList] > li");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i).select("div[class=msgCnt]").get(0);
			Element el2 = els.get(i).select("div[class=mediaWrap] > div >a.pic").get(0);
			System.out.println(el.text() + "\n\n");
			System.out.println(el2.attr("href") + "\n\n");
		}
	}
}
