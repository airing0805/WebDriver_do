package com.andy.weiboDriver.webDriver;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.andy.weiboDriver.util.FileUtil;


@SuppressWarnings("deprecation")
public class T {

	public static void main(String[] args) throws ClientProtocolException, IOException{
		String aa = "aa?bb";
		String bb = "aabb";
		System.out.println(aa.substring(0,1));
		System.out.println(bb.split("\\u003F")[0]);

	}
}
