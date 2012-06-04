package com.mobilelibrary.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * String tools
 * @author Ryan
 *
 */
public final class StringTools {

	public static String converStreamToString(InputStream is) {
		if(is==null) return null;
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

	public static String namePicture(String imageUrl,String img_extension) {
		String url = imageUrl;
		String suffixes;
		if(img_extension == ""){
			suffixes = ".jpg";
		}else{
			suffixes = "."+img_extension;
		}
		url = url.replace("/", "");
		url = url.replace(".jpg", "");
		url = url.replace(".jpeg", "");
		url = url.replace(".gif", "");
		url = url.replace(".png", "");
		url = url.replace(".", "");
		return url.substring(url.length() - 10) +suffixes;
	}

}
