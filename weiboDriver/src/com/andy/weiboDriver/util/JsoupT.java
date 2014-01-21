package com.andy.weiboDriver.util;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class JsoupT {

	// @Test
	public void getBaiDuDoc() {
		String url = "http://www.baidu.com/link?url=OLaP4gTB_-IHGemUj1qxYGGGC6kd8er-C7K9Vz0XXFq1Agti0IR7OShc1dF0l9Wq5dz-bQxihdZWzAByd_NOPVVpa8rT-x3DhGEbqHAv39q";
		Document doc = getDoc(url);
		System.out.println(doc.html());
	}

	// @Test
	public void getBaiDuNew() {
		String url = "http://www.baidu.com/s?ie=utf-8&bs=互联网金融&f=8&rsv_bp=1&rsv_spt=3&wd=互联网金融&inputT=0";
		Document doc = getDoc(url);
		Element body = doc.getElementById("2");// .body();
		Elements newE = body.getElementsByTag("a");
		for (Iterator<Element> it = newE.iterator(); it.hasNext();) {
			Element aTag = it.next();
			System.out.println(aTag.text());
			System.out.println(aTag.attr("href") + "\n");
		}
		// System.out.println(newE.html());
	}

	@Test
	public void getSinaArti() {
		// artical-player-wrap
		String url = "http://news.sina.com.cn/c/2014-01-16/073529257251.shtml";
		Document doc = getDoc(url);
		Element el = getSinaArti( doc);

//		System.out.println(el.html());
	}

	public Document getDoc(String url) {
		Document doc = null;
		try {
			// url = URLEncoder.encode(value, "utf-8"); //编码
			// url = URLDecoder.decode(value,"utf-8");//解码
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public Element getSinaArti(Document doc){
		Element el = doc.getElementById("artibody");//.body();
		Element play = el.select("div.artical-player-wrap").first();
		play.empty();
		play.unwrap();
//		Element play2 = el.select("div.a-p-hd").first();
//		play2.unwrap();
		Elements els = doc.getElementsByTag("p");
		int num = els.size();
		els.remove(num-1);
		els.remove(num-2);
		els.remove(num-3);
		System.out.println(els.toString());
		return el;

	}
}
