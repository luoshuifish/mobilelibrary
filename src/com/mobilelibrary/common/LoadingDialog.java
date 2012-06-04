package com.mobilelibrary.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchManager.OnCancelListener;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mobilelibrary.handlerexception.WSError;
/*
 * 此类的设计主要是为了异步线程复用进度条
 */

public abstract class LoadingDialog<Input, Result> extends AsyncTask<Input, WSError, Result>{

	private ProgressDialog mProgressDialog;
	protected Activity mActivity;
	private int mLoadingMsg;
	private int mFailMsg;

	public LoadingDialog(Activity activity, int loadingMsg, int failMsg){
		this.mActivity = activity;
		this.mLoadingMsg = loadingMsg;
		this.mFailMsg = failMsg;
	}

	@Override
	public void onCancelled() {		
		failMsg();
		super.onCancelled();
	}

	@Override
	public void onPreExecute() {
		String title = "";
		String message = mActivity.getString(mLoadingMsg);
		mProgressDialog = ProgressDialog.show(mActivity, title, message, true, true, new DialogInterface.OnCancelListener(){

			@Override
			public void onCancel(DialogInterface dialogInterface) {
				LoadingDialog.this.cancel(true);
			}

		});
		super.onPreExecute();
	}

	@Override
	public abstract Result doInBackground(Input... params);

	@Override
	public void onPostExecute(Result result) {
		super.onPostExecute(result);

		mProgressDialog.dismiss();

		if(result != null){
			doStuffWithResult(result);
		} else {
			
			failMsg();

		}
	}
	
	protected void failMsg(){
		Toast.makeText(mActivity, mFailMsg, 2000).show();
	}
	

	public abstract void doStuffWithResult(Result result);
	
	@Override
	protected void onProgressUpdate(WSError... values) {
		Toast.makeText(mActivity, values[0].getMessage(), Toast.LENGTH_LONG).show();
		this.cancel(true);
		mProgressDialog.dismiss();
		super.onProgressUpdate(values);
	}
	
	public void doCancel()
	{
		mProgressDialog.dismiss();
	}

}
