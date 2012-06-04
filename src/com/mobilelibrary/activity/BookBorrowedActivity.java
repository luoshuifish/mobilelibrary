package com.mobilelibrary.activity;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

import com.mobilelibrary.R;
import com.mobilelibrary.adapter.BookSearchAdapter;
import com.mobilelibrary.common.LoadingDialog;
import com.mobilelibrary.common.MobilelibraryResourceFromJSONRequest;
import com.mobilelibrary.entity.BookSearchEntity;
import com.mobilelibrary.handlerexception.WSError;

public class BookBorrowedActivity extends BaseActivity{
	
    private ViewFlipper mViewFlipper;
	private ListView mBorrowedListView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.borrowed_book);
		setTopTitle(this,R.string.my_borrowedbook);
		
		mViewFlipper = (ViewFlipper)findViewById(R.id.ViewFlipper);
		
		mBorrowedListView = (ListView)findViewById(R.id.borrowedListView);
		
		
		//if  borrowed book is null
		if(mBorrowedListView.getCount()==0){
			mViewFlipper.setDisplayedChild(1);
		}


    }
	
	// get borrowed book from db
	private class NewTaskGetBorrowedBookFromDB extends LoadingDialog<Void, Integer>{

		private Integer mSearchMode;
		private BaseAdapter mAdapter;

		public NewTaskGetBorrowedBookFromDB(Activity activity, int loadingMsg, int failMsg) {
			super(activity, loadingMsg, failMsg);
		}

		@Override
		public Integer doInBackground(Void... params) {
			
			//get the  books from borrowed DB
			bookSearch();
			return 1;
		}

		@Override
		public void doStuffWithResult(Integer result) {

			mBorrowedListView.setAdapter(mAdapter);
			
			if(mBorrowedListView.getCount() > 0){
				mViewFlipper.setDisplayedChild(0); 
			} else {
				mViewFlipper.setDisplayedChild(1); 
			}
			mBorrowedListView.setOnItemClickListener(mBookClickListener);
		
		}

		private void bookSearch(){
			
			//再次建立JSON的服力
			MobilelibraryResourceFromJSONRequest  server = new MobilelibraryResourceFromJSONRequest();
			
			//得到搜索
			String query = mSearchEditText.getText().toString();
			BookSearchEntity[] books = null;
			try {
				
				books = server.getBooksSearch(query);
				BookSearchAdapter bookAdapter = new BookSearchAdapter(SearchActivity.this); 
				bookAdapter.setList(books);
				bookAdapter.setListView(mSearchListView);
				mAdapter = bookAdapter;

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (WSError e) {
				publishProgress(e);
				this.cancel(true);
			}
		}



		
	}
	
	//borrowed book Listener
	private OnItemClickListener mBookClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long time) {
			//跳传到该书的信息页
		}

	};

}
