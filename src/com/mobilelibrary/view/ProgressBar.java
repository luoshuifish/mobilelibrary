
package com.mobilelibrary.view;



import com.mobilelibrary.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ProgressBar extends LinearLayout {
	
	protected TextView mTextView;
	
	public ProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ProgressBar(Context context) {
		super(context);
		init();
	}
	

	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.progress_bar, this);
		
		mTextView = (TextView)findViewById(R.id.ProgressTextView);
	}
	
	public void setText(int resid){
		mTextView.setText(resid);
	}

}
