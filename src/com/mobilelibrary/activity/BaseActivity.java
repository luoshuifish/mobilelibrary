package com.mobilelibrary.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mobilelibrary.R;
import com.mobilelibrary.common.AppConstant;
import com.mobilelibrary.view.ExitDialog;
/**
 * Base activity that every new activity will extends this
 * @author Ryan
 *
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	}

	/**
	 * set title for activity
	 * @param activity
	 * @param resTitle
	 */
	protected void setTopTitle(Activity activity,int resTitle){
		((TextView)activity.findViewById(R.id.top_title)).setText(resTitle);
	}
	
	/**
	 * set title for activity
	 * @param activity
	 * @param resTitle
	 */
	protected void setTopTitle(Activity activity,String strTitle){
		((TextView)activity.findViewById(R.id.top_title)).setText(strTitle);
	}
	
	/**
	 * set button of back for activity
	 * @param activity
	 * @param isVisible
	 * @param onClickListener
	 */
	protected void setBackButton(Activity activity,android.view.View.OnClickListener onClickListener){
		Button backButton = (Button)activity.findViewById(R.id.btn_back);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(onClickListener);
	}
	
	/**
	 * set button of right on top
	 * @param activity
	 * @param strTitle
	 * @param onClickListener
	 */
	protected void setRightButton(Activity activity,String strTitle,android.view.View.OnClickListener onClickListener){
		Button backButton = (Button)activity.findViewById(R.id.btn_top_right);
		backButton.setVisibility(View.VISIBLE);
		backButton.setText(strTitle);
		backButton.setOnClickListener(onClickListener);
	}
	
	/**
	 * set button of right on top
	 * @param activity
	 * @param strTitle
	 * @param onClickListener
	 */
	protected void setRightButton(Activity activity,int resTitle,android.view.View.OnClickListener onClickListener){
		Button backButton = (Button)activity.findViewById(R.id.btn_top_right);
		backButton.setText(resTitle);
		backButton.setOnClickListener(onClickListener);
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case AppConstant.EXIT:
			new ExitDialog(this).show();
			break;

		default:
			break;
		}
		return super.onCreateDialog(id);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (this instanceof MainPageActivity || this instanceof SearchActivity || this instanceof OtherActivity) {
                showDialog(AppConstant.EXIT);
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
	
	
}
