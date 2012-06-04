package com.mobilelibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilelibrary.R;
/**
 * other information
 * @author Ryan
 *
 */
public class OtherActivity extends BaseActivity implements OnItemClickListener{

	private static final int MODIFY_PASSWORD = 0;
	private static final int ABOUT = 1;
	private static final int FEEDBACK = 2;
	
	private ListView otherListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
		
		setTopTitle(this,R.string.other);
		
		otherListView = (ListView) this.findViewById(R.id.lv_other);
		otherListView.setOnItemClickListener(this);
		otherListView.setAdapter(new OtherAdapter(this));
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		switch (position) {
		case MODIFY_PASSWORD:
			Intent setPwdIntent=new Intent(OtherActivity.this, ModiPwdActivity.class);
			startActivity(setPwdIntent);
			break;
			
		case ABOUT:
			Intent aboutIntent=new Intent(OtherActivity.this, AboutActivity.class);
			startActivity(aboutIntent);
			break;
			
		case FEEDBACK:
//			UMFeedbackService.setGoBackButtonVisible();
//			UMFeedbackService.openUmengFeedbackSDK(OtherActivity.this);
			break;

		default:
			break;
		}
	}
	
	class OtherAdapter extends BaseAdapter{

		private Context context;
		
		public OtherAdapter(Context context){
			this.context = context;
		}
		
		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.other_item, null);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.item_img);
				holder.textView = (TextView) convertView.findViewById(R.id.item_content);
				
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			if (position == 0) {
				holder.imageView.setImageResource(R.drawable.modi_pwd);
				holder.textView.setText(getResources().getString(R.string.set_password));
				convertView.setBackgroundResource(R.drawable.listview_top_bg);
			}else if (position == 1) {
				holder.imageView.setImageResource(R.drawable.about);
				holder.textView.setText(getResources().getString(R.string.about));
				convertView.setBackgroundResource(R.drawable.listview_middle_bg);
			}else if (position == 2) {
				holder.imageView.setImageResource(R.drawable.feedback);
				holder.textView.setText(getResources().getString(R.string.feedback));
				convertView.setBackgroundResource(R.drawable.listview_bottom_bg);
			}
			
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		private ImageView imageView;
		private TextView textView;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
