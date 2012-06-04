package com.mobilelibrary.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {

	public static String converStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String namePicture(String imageUrl) {
		

		String url = imageUrl;
		url = url.replace("/", "");
		url = url.replace(".jpg", "");
		
		if(url.length()>10){
			return url.substring(url.length() - 10) ;
		}
		else if(url.length()>0){
			return url.substring(url.length());
		}else{
			return null;
		}
		
		
	}

}
