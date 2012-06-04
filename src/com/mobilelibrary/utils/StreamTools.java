package com.mobilelibrary.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class StreamTools {
	
	/**
	 * convert string to input stream
	 * @param content
	 * @return
	 */
	public static InputStream stringToInputStream(String content){
		InputStream inputStream = null;
		inputStream = new ByteArrayInputStream(content.getBytes());
		return inputStream;
	}

	/**
	 * convert input stream to string
	 * @param inputStream
	 * @return
	 */
	public static String inputStreamToString(InputStream inputStream){
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * inputstream to byte[] 
	 */
	public static byte[] inputStreamToBytes(InputStream inputStream)
			throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		inputStream.close();
		return outputStream.toByteArray();
	}
}
