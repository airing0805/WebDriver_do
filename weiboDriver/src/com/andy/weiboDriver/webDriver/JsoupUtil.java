package com.andy.weiboDriver.webDriver;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JsoupUtil {

	public static boolean hasElement(Element el, String  select) {
			Elements els =	el.select(select);
			if(null == els || els.size() > 0){
				return true;
			}else {
				return false;
			}
	}
}
