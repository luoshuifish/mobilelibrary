package com.mobilelibrary.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * HTTP request 锛圥OST锛� * 
 * @author Ryan
 */
public class HttpRequest {
	private final static int CONNECT_TIMEOUT = 5000;
	private final static int REPEATS = 3;
	private final static int READ_TIMEOUT = 10000;
	
	/**
	 * 鍙戦�XML鏁版嵁缁欐寚瀹氱殑URL path:璇锋眰鍦板潃 xml:xml鏁版嵁
	 * @param path
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static InputStream requestByXML(String path, String xml){
		int requestCounts = 0;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		byte[] data = xml.getBytes();
		
		while (requestCounts < REPEATS) {
			try {
				URL url = new URL(path);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setConnectTimeout(CONNECT_TIMEOUT);
				conn.setReadTimeout(READ_TIMEOUT);
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
				conn.setRequestProperty("Content-Length", String.valueOf(data.length));
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(data);
				outputStream.flush();
				outputStream.close();
				
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					throw new RuntimeException();
				}else {
					inputStream = conn.getInputStream();
				}
				
				if (null == inputStream) {
					throw new RuntimeException();
				}else {
					requestCounts = REPEATS;
				}
			} catch (IOException e) {
				requestCounts ++;
			} catch (RuntimeException e) {
				requestCounts ++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return inputStream;
	}
	

	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static InputStream requestByURL(String path)throws Exception{
		HttpPost httpPost = new HttpPost(path);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return response.getEntity().getContent();
		}else {
			return null;
		}
	}

	/**
	 * 鍙戦�鎼哄甫鍙傛暟鐨凱OST璇锋眰
	 * 杩斿洖绫诲瀷涓篒nputStream锛屼紶鍏mlpull瑙ｆ瀽鍣ㄨ繘琛岃В鏋�	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static InputStream requestByParams(String path, Map<String, String> params)
			throws Exception {
		List<NameValuePair> paramsPairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				paramsPairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		// 鑾峰彇缁忚繃缂栫爜鍚庣殑瀹炰綋瀵硅薄
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsPairs,
				"UTF-8");
		HttpPost httpPost = new HttpPost(path);
		httpPost.setEntity(entity);
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpPost);
		// 鍙栧緱璇锋眰鍝嶅簲缁撴灉xml锛岃В鏋愬苟鍒ゆ柇鏄惁鎴愬姛
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//			String results = EntityUtils.toString(response.getEntity());
			return response.getEntity().getContent();
		}else {
			return null;
		}
	}

	//鐢盪RL鑾峰緱鍥剧墖鐨勮緭鍏ユ祦
    public static InputStream getImageStream(String path){  
    	int requestCounts = 0;
        HttpURLConnection conn = null;
        InputStream resultStream = null;
        
        while (requestCounts < REPEATS) {
        	URL url;
			try {
				url = new URL(path);
				conn = (HttpURLConnection) url.openConnection();     
	            conn.setConnectTimeout(CONNECT_TIMEOUT);     
	            conn.setRequestMethod("POST");  
	            
	            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){     
	                throw new RuntimeException();        
	            }else {
	            	resultStream = conn.getInputStream();
				}
	            
	            if (resultStream == null) {
					throw new RuntimeException();
				}else {
					requestCounts = REPEATS;
				}
			} catch (IOException e) {
				requestCounts ++;
				e.printStackTrace();
			} catch (RuntimeException e) {
				requestCounts ++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}     
        	
		}
        
        return resultStream;   
    }  
    
    //鐢辫緭鍏ユ祦鑾峰緱瀛楄妭鏁扮粍
    public static byte[] readStream(InputStream inStream) throws Exception{     
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();     
        byte[] buffer = new byte[1024];     
        int len = 0;     
        while( (len=inStream.read(buffer)) != -1){     
            outStream.write(buffer, 0, len);     
        }     
        outStream.close();     
        inStream.close();     
        return outStream.toByteArray();     
    }   
}
