package com.mobilelibrary.adapter;

import com.mobilelibrary.R;
import com.mobilelibrary.common.ImageManager;
import com.mobilelibrary.entity.BookRecommendEntity;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * 新书推荐显示的适配器
 */

public class BookRecommendAdapter extends ArrayListAdapter<BookRecommendEntity>{

	public BookRecommendAdapter(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		View row=convertView;

		ViewHolder holder;

		if (row==null) {
			LayoutInflater inflater = mContext.getLayoutInflater();
			row=inflater.inflate(R.layout.book_recommend_item, null);

			holder = new ViewHolder();
			holder.bookImage = (ImageView)row.findViewById(R.id.book_recommend_imageview);
			holder.bookText = (TextView)row.findViewById(R.id.book_recommend_textview);

			row.setTag(holder);
		}
		else{
			holder = (ViewHolder) row.getTag();
		}
		
		holder.bookText.setText(mList.get(position).getBookText());
		
		System.out.println("text"+mList.get(position).getBookText());
		final String imageUrl = mList.get(position).getBookImageUrl();
		
		holder.bookImage.setTag(imageUrl);
		
		Resources res = mContext.getResources();
		Bitmap bmp=BitmapFactory.decodeResource(res, R.drawable.book_recommend_default);
		
		//默认图片
		ImageManager.INSTANCE.setPlaceholder(bmp);
		ImageManager.INSTANCE.loadBitmap(imageUrl, holder.bookImage);  

		return row;
		
	}
	
	static class ViewHolder {
		ImageView bookImage;
		TextView bookText;
	}

}
