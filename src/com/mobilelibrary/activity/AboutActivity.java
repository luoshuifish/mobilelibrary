package com.mobilelibrary.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.mobilelibrary.R;

public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		setTopTitle(this,R.string.about);
		setBackButton(this, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AboutActivity.this.finish();
			}
		});
		
	}
}
