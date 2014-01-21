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
	 * �ļ����������е��ļ�
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
	 * ���ַ������ӵ��ļ���ĩβ
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
	 * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�
	 */
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((tempString = reader.readLine()) != null) {
				// ��ʾ�к�
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
