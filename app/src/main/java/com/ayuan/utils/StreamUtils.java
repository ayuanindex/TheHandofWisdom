package com.ayuan.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将流转换成字符串
 */
public class StreamUtils {
	/**
	 * 将流转换成字符串
	 *
	 * @param inputStream 传入的流
	 * @return 返回字符串
	 */
	public static String StreamToString(InputStream inputStream) {
		if (inputStream != null) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int len = -1;
			try {
				while ((len = inputStream.read(bytes)) != -1) {
					byteArrayOutputStream.write(bytes, 0, len);
				}
				return byteArrayOutputStream.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (byteArrayOutputStream != null) {
					try {
						byteArrayOutputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}
}
