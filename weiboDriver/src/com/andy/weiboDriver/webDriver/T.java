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
//		Request request = Request.Get("http://weibo.com/u/3944924952");
		// 核心应用类   
	       @SuppressWarnings({ "resource" })
		HttpClient httpClient = new DefaultHttpClient();   
	  
	        // HTTP请求   
	        HttpUriRequest request =   
	                new HttpGet("http://t.qq.com/zhichangyy");   
	  
	        // 打印请求信息   
	        System.out.println(request.getRequestLine()); 
	        
	        HttpResponse response = httpClient.execute(request);   
//	        StringEntity myEntity = new StringEntity("important message",
//	        		ContentType.create("text/plain", "UTF-8"));
//	        response.setEntity(myEntity);
            // 打印响应信息   
//            System.out.println(response.getStatusLine()); 
            HttpEntity he = response.getEntity();
            String aa = EntityUtils.toString(he);
            System.out.println("Response content:"  
                    + new String(aa.getBytes("UTF-8"),"UTF-8"));
            FileUtil.write2FileEnd(System.getProperty("user.dir")+"/aa.txt", new String(aa.getBytes("UTF-8"),"UTF-8"));
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		RequestConfig requestConfig = RequestConfig.custom()
//		.setSocketTimeout(1000)
//		.setConnectTimeout(1000)
//		.build();
//		HttpGet httpget1 = new HttpGet("http://weibo.com/u/3944924952");
//		httpget1.setConfig(requestConfig);
//		CloseableHttpResponse response1 = httpclient.execute(httpget1, context);
//		try {
//		HttpEntity entity1 = response1.getEntity();
//		} finally {
//		response1.close();
//		}
	}
}
