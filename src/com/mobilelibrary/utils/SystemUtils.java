package com.mobilelibrary.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class SystemUtils {

	/**
	 * check the network
	 * @param context
	 * @return
	 */
	public static boolean checkNetwork(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @param photoName
	 * @return
	 */
	public static Bitmap getPhotoFromSDCard(String path,String photoName){
		Bitmap photoBitmap = BitmapFactory.decodeFile(path + "/" +photoName +".jpg");
		if (photoBitmap == null) {
			return null;
		}else {
			return photoBitmap;
		}
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public static boolean findPhotoFromSDCard(String path,String photoName){
		boolean flag = false;
		
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			File dir = new File(path);
			if (dir.exists()) {
				File folders = new File(path);
				File photoFile[] = folders.listFiles();
				for (int i = 0; i < photoFile.length; i++) {
					String fileName = photoFile[i].getName().split("\\.")[0];
					if (fileName.equals(photoName)) {
						flag = true;
					}
				}
			}else {
				flag = false;
			}
//			File file = new File(path + "/" + photoName  + ".jpg" );
//			if (file.exists()) {
//				flag = true;
//			}else {
//				flag = false;
//			}
			
		}else {
			flag = false;
		}
		return flag;
	}
	
	public static void savePhotoToSDCard(Bitmap photoBitmap,String photoName,String path){
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			
			File dir = new File(path);
			if (!dir.exists()){
				dir.mkdirs();
			}
			
			File photoFile = new File(path , photoName + ".jpg");
			try {
				
				FileOutputStream fileOutputStream = new FileOutputStream(photoFile);
				
				if (photoBitmap != null) {
					if (photoBitmap.compress(CompressFormat.JPEG, 100, fileOutputStream)) {
						fileOutputStream.flush();
						fileOutputStream.close();
					}
				}
				
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			}
			
		} 
	}
	
	/**
	 * @param context
	 * @param path
	 */
	public static void deleteAllPhoto(String path){
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}
}
