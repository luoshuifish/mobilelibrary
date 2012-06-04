package com.mobilelibrary.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;

import com.mobilelibrary.R;

public class ExitDialog extends AlertDialog {

	public ExitDialog(Activity activity) {
		super(activity);
		
		DialogInterface.OnClickListener positiveListener = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO do some clear work
				
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		};
		
		DialogInterface.OnClickListener nagativeListener = new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dismiss();
			}
		};
		
		DialogInterface.OnKeyListener onKeyListener = new OnKeyListener() {
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH) {
					return true;
				}else {
					return false;
				}
			}
		};
		
		this.setTitle(R.string.promotion);
		this.setMessage(activity.getText(R.string.dialog_exit));
		this.setButton(activity.getText(R.string.confirm), positiveListener);
		this.setButton2(activity.getText(R.string.cancel), nagativeListener);
		this.setOnKeyListener(onKeyListener);
		
	}

	
}
