

package com.mobilelibrary.common;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.mobilelibrary.utils.ImageTools;
import com.mobilelibrary.utils.StringUtil;
import com.mobilelibrary.utils.SystemUtils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;



public enum ImageManager {

	INSTANCE;
	

	private final ExecutorService pool;
	private Map<ImageView, String> imageViews = Collections
			.synchronizedMap(new WeakHashMap<ImageView, String>());
	private Bitmap placeholder;
	private Bitmap placeholderMobile;

	private ThreadPoolExecutor executor;
	private BlockingQueue<Runnable> queue;
	public Object lock = new Object();

	public Boolean mLock = false;


	ImageManager() {
		pool = Executors.newFixedThreadPool(1);
		queue = new LinkedBlockingQueue<Runnable>();

		executor = new ThreadPoolExecutor(1, 10, 120, TimeUnit.SECONDS, queue);
	}

	public void setPlaceholder(Bitmap bmp) {
		placeholder = bmp;
	}
	
	public void setPlaceholderMobile(Bitmap bmp) {
		placeholderMobile = bmp;
	}

	public void queueJob(final String url, final ImageView imageView) {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {

					if (msg.obj != null) {
						
						String urlString = (String) imageView.getTag();
						if(!urlString.equals("")){
						if(urlString.equals(url)){
						imageView.setVisibility(View.VISIBLE);
						imageView.setImageBitmap((Bitmap) msg.obj);}
						}
					} else {
					}

			}
		};



			executor.execute(new Runnable() {


				@Override
				public void run() {
					// TODO Auto-generated method stub

					final Bitmap bmp = downloadBitmap(url);
					Message message = Message.obtain();
					message.obj = bmp;
					Log.d(null, "Item downloaded: " + url);

					handler.sendMessage(message);
					}



				}
			);

	}
	

	public void loadBitmap(final String url, final ImageView imageView) {
		

		
		resetPurgeTimer();
		
		Bitmap bitmap1 = null;
		if(!url.equals("")){
			bitmap1 = getBitmapFromCache(url);
		}

		if (bitmap1 != null) {
			imageView.setVisibility(View.VISIBLE);
			imageView.setImageBitmap(bitmap1);
		}else if(!url.equals("")){
			imageView.setImageBitmap(placeholder);
			queueJob(url, imageView);
	
		}else{
			imageView.setImageBitmap(placeholder);
		}

	}

	// 锟斤拷锟斤拷硬锟斤拷锟斤拷锟斤拷锟斤拷锟�
	private static final int HARD_CACHE_CAPACITY = 20;

	// 锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷时锟斤拷
	private static final int DELAY_BEFORE_PURGE = 30* 1000; // in milliseconds

	private final HashMap<String, Bitmap> sHardBitmapCache = new LinkedHashMap<String, Bitmap>(
			HARD_CACHE_CAPACITY / 2, 0.75f, true) {
		@Override
		protected boolean removeEldestEntry(
				LinkedHashMap.Entry<String, Bitmap> eldest) {
			if (size() > HARD_CACHE_CAPACITY) {
				cache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest
						.getValue()));
				return true;
			} else
				return false;
		}
	};

	private final static ConcurrentHashMap<String, SoftReference<Bitmap>> cache = new ConcurrentHashMap<String, SoftReference<Bitmap>>(
			HARD_CACHE_CAPACITY / 2);

	private final Handler purgeHandler = new Handler();

	private final Runnable purger = new Runnable() {
		public void run() {
			System.out.println("clear");
			clearCache();
		}
	};

	public void clearCache() {
		sHardBitmapCache.clear();
		cache.clear();
	}

	private void resetPurgeTimer() {
		// TODO Auto-generated method stub
		purgeHandler.removeCallbacks(purger);
		purgeHandler.postDelayed(purger, DELAY_BEFORE_PURGE);
	}
	
	public Bitmap getBitmapFromCacheForOpen(String url){
		return getBitmapFromCache(url);
	}
	
    private Bitmap getBitmapFromCache(String url) {
        synchronized (sHardBitmapCache) {
            final Bitmap bitmap = sHardBitmapCache.get(url);
            if (bitmap != null) {
                sHardBitmapCache.remove(url);
                sHardBitmapCache.put(url, bitmap);
                return bitmap;
            }
        }

        SoftReference<Bitmap> bitmapReference = cache.get(url);
        if (bitmapReference != null) {
            final Bitmap bitmap = bitmapReference.get();
            if (bitmap != null) {
                return bitmap;
            } else {
            	cache.remove(url);
            }
        }

        return null;
    }
    

	private Bitmap downloadBitmap(String url) {
		try {
			Bitmap bitmap = null;
			String fileName = "";
			File file = null;
			if (url != null && url.length() != 0) {
				fileName = StringUtil.namePicture(url);
			}
			

			file = new File(AppConstant.BOOK_RECOMMEND + "/" + fileName + ".jpg");

				
			


			if (file.exists()) {
					bitmap = SystemUtils.getPhotoFromSDCard(AppConstant.BOOK_RECOMMEND,
							fileName);

			} else {

				InputStream photoStream = null;
				try {
					photoStream = getImageStream(url);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				
				bitmap = ImageTools.getResizeBitmap(photoStream, 128, 128);
				if (bitmap != null) {
					
						SystemUtils.savePhotoToSDCard(bitmap, fileName,
								AppConstant.BOOK_RECOMMEND);
						
				}
		


			}
			addBitmapToCache(url,bitmap);

			return bitmap;
		} catch (Exception e) {

		}

		return null;
	}
	

	
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (sHardBitmapCache) {
                sHardBitmapCache.put(url, bitmap);
            }
        }
    }

	public static InputStream getImageStream(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("POST");
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			// conn.getContentLength()
			return conn.getInputStream();
		}
		return null;
	}


}

