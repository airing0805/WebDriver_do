package com.andy.yunpanDriver.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileUtil {

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
	public static StringBuffer readFileByLines(String fileName) {
		StringBuffer sb = new StringBuffer();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return sb;
	}


}
