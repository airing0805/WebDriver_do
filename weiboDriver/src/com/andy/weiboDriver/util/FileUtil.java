package com.andy.weiboDriver.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtil {

	// String readPath =
	// "G:/heritrix-1.14.4/jobs/codeAll_0912_1-20110912003812515/mirror/www.java2s.com";
	String readPath = "G:/heritrix-1.14.4/jobs/code_CN-20111009230631343/mirror/www.java2s.com";
	String write2Path = "";

	/**
	 * 文件夹下面所有的文件
	 * 
	 */
	public void getAllfiles(File file) {
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			File fileNew = fileList[i];
			if (fileNew.isDirectory()) {
				getAllfiles(fileNew);
			} else {
				StringBuffer partStr = new StringBuffer("");
				// String repStr =
				// "G:\\heritrix-1.14.4\\jobs\\codeAll_0912_1-20110912003812515\\mirror\\www.java2s.com";
				partStr.append(fileNew.getAbsolutePath().trim());
				if (partStr != null
						&& (partStr.toString().endsWith(".htm") || partStr
								.toString().endsWith(".html"))) {
					write2FileEnd(write2Path, partStr.toString() + "\n");
				}
			}
		}
	}

	/**
	 * 将字符串增加到文件的末尾
	 * 
	 * @param partStr
	 */
	public static void write2FileEnd(String write2Path, String partStr) {
		File file = new File(write2Path);
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file, true));
			String counter = partStr;
			writer.write(counter);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}


}
