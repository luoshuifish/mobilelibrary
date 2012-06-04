

package com.mobilelibrary.view;




import com.mobilelibrary.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class FailureBar extends LinearLayout{
	
	protected TextView mTextView;
	protected Button mRetryButton;
	
	public FailureBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FailureBar(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		LayoutInflater.from(getContext()).inflate(R.layout.failure_bar, this);
		
		mTextView = (TextView)findViewById(R.id.ProgressTextView);
		mRetryButton = (Button)findViewById(R.id.RetryButton);
	}
	

	public void setText(int resid){
		mTextView.setText(resid);
	}
	
	public void setOnRetryListener(OnClickListener l){
		mRetryButton.setOnClickListener(l);
	}

}
