package com.kxingyi.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 属性文件操作工具类。
 * 
 * @author lixu
 */
public final class PropertiesUtil {
	
	private PropertiesUtil() {}

	/**
	 * 加载指定路径(资源文件下的)的property文件
	 * @param path
	 * @return
	 */
	public static Properties readProperties(String path){
		InputStream inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(path);
		Properties props = new Properties();
		try {
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return props;
	}
	
	/**
	 * 加载指定路径下的property文件
	 * @param path
	 * @return
	 */
	public static Properties readPropertiesByPath(String path) {
		InputStream inputStream = null;
		Properties props = null;
		try {
			inputStream = new FileInputStream(path);
			props = new Properties();
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return props;
	}
	
	/**
	 * 修改properties属性
	 * @param newProperty
	 * @param file
	 */
	public static void updateProperties(Properties newProperty, String file) {
		if (newProperty == null) {
			return;
		}
		FileOutputStream oFile = null;
		try { 
			oFile = new FileOutputStream(file, false);
			newProperty.store(oFile, "modify properties file");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oFile != null) {
					oFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
