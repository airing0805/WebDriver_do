package com.andy.yunpanDriver.doMain;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.andy.yunpanDriver.util.FileUtil;

public class ChangeFileAttr {
	private static Logger logger = Logger.getLogger(ChangeFileAttr.class);
	public static void main(String[] args) {
		System.out.println("\\u"+Integer.toHexString('】'));
	}

	@Test
	public void test(){
		String path = "H:\\pan_together\\400gb_yuanbaiduvip_1\\1";
		File file= new File(path);
		List<String> pathList = new ArrayList<String>();
		int fileNum = 0;
		getFileIterCount( file,pathList,fileNum);
		logger.info(pathList.size());
		
	}

	public void getFileIterChange(File[] fileArr ,List<String> pathList,int fileNum ) {
		for (int i = 0; i < fileArr.length; i++) {
			File file = fileArr[i];
			if(file.isDirectory()){
				File[] fileArr2 = file.listFiles();
//				if(fileArr2.length==1){
//					pathList.add(file.getAbsolutePath());
//					logger.info(file.getAbsoluteFile());
//				}
				getFileIterChange(fileArr2,pathList,fileNum);
				
			}else{
				String name = file.getName();
				//最后通过长度，判断是不是在文件名中加了来源
				String containsStr = "_[_www.torrentday.com_]";
				if(name.contains(containsStr)){
					logger.info(name);
					String path = System.getProperty("user.dir")+file.separator+ containsStr+".txt";
//					int indexOf = name.indexOf("￡");
//					int last = name.lastIndexOf(".");
//					String replace = name.substring(indexOf,last);
					
//					name = name.replace(containsStr, "");
//					
					String newName = file.getParent()+ file.separator +name;
					if(new File(newName).exists()){
						file.deleteOnExit();
						logger.info("del");
					}else{
						Boolean flag = file.renameTo(new File(newName));
						if(!flag){
							logger.info(file.getAbsolutePath());
						}
					}
					FileUtil.write2FileEnd(path, file.getAbsolutePath());
					pathList.add(name);
				}
			}
		}
	}
	
	public void getFileIterCount(File folder ,List<String> pathList,int fileNum ) {
		File[] fileArr = folder.listFiles();
		for (int i = 0; i < fileArr.length; i++) {
			File file = fileArr[i];
			if(file.isDirectory()){
				File[] fileArr2 = file.listFiles();
//				if(fileArr2.length==1){
//					pathList.add(file.getAbsolutePath());
//					logger.info(file.getAbsoluteFile());
//				}
				getFileIterCount(file,pathList,fileNum);
				
			}else{
				fileNum ++;
				logger.info(fileNum);
				pathList.add("a");
			}
		}
	}
	
	
	public void getAllFileCome(String name){
		boolean flag = name.contains("⊙星星草⊙bbs.ylxcc.com⊙夜来香社区");
		flag = name.contains("bt种子");
		flag = name.contains("bt种子");
		flag = name.contains("百度网盘资源搜索");
		flag = name.contains("wangpanwu.com");
		flag = name.contains("百度网盘资源搜索[wangpanwu.com]");
		flag = name.contains("-bt种子-百度网盘资源搜索[wangpanwu.com]");
		flag = name.contains("www.wofei.net");
		flag = name.contains("【送打包好的X片种子（你懂的），加Q：1911499188】");
		flag = name.contains("\u3010送打包好的X片种子（你懂的），加Q：1911499188\u3011");
		flag = name.contains("飞跃彩虹(over_the_rainbow)@圣城家园@可恶阿帅");
		flag = name.contains("by 2cheng");
		flag = name.contains("￡圣城");
		flag = name.contains("￡圣城小罗");
		flag = name.contains("￡圣城九洲客");
		flag = name.contains("￡圣城loveliness");
		flag = name.contains("￡圣城打佛");
		flag = name.contains("@wawabear");
		flag = name.contains("￡圣城riyoo.归来");
		flag = name.contains("@圣城家园");
		flag = name.contains("@拒绝05");
		flag = name.contains("@空の轨迹");
		flag = name.contains("@rainman48");
		flag = name.contains("@可恶阿帅");
		flag = name.contains("@jamesrong");
		flag = name.contains("@chd联盟");
		flag = name.contains("@silu");
		flag = name.contains("..");
	}
	
	public void getAllFileType(String name){
//		boolean flag = !name.endsWith(".torrent") && !name.endsWith(".rar");
//		flag = flag && !name.endsWith(".jpg") && !name.endsWith(".txt");
//		flag = flag && !name.endsWith(".zip") && !name.endsWith(".xls");
//		flag = flag && !name.endsWith(".TORRENT") && !name.endsWith(".srt");
//		flag = flag && !name.endsWith(".html") && !name.endsWith(".torrent");
		boolean flag = !name.endsWith(".torrent") && !name.endsWith(".rar");
		flag = flag && !name.endsWith(".jpg") && !name.endsWith(".torrent");
		flag = flag && !name.endsWith(".zip") && !name.endsWith(".xls");
		flag = flag && !name.endsWith(".TORRENT") && !name.endsWith(".srt");
		flag = flag && !name.endsWith(".html") && !name.endsWith(".torrent");
				
		if(flag){
			logger.info(name);
		}
	}
}
