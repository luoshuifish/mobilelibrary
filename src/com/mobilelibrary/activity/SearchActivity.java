package com.mobilelibrary.activity;

import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.mobilelibrary.R;
import com.mobilelibrary.adapter.BookRecommendAdapter;
import com.mobilelibrary.adapter.BookSearchAdapter;
import com.mobilelibrary.common.LoadingDialog;
import com.mobilelibrary.common.MobilelibraryResourceFromJSONRequest;
import com.mobilelibrary.entity.BookRecommendEntity;
import com.mobilelibrary.entity.BookSearchEntity;
import com.mobilelibrary.handlerexception.WSError;
import com.mobilelibrary.view.FailureBar;
import com.mobilelibrary.view.ProgressBar;
/**
 * search books activity
 * @author Ryan
 *
 */
public class SearchActivity extends BaseActivity{
	
	
	private ViewFlipper mViewFlipper;
	private Gallery mGallery;
	private ProgressBar mProgressBar;
	private FailureBar mFailureBar;
	private ViewFlipper mSearchFlipper;
	private ListView mSearchListView;
	private Button mSearchButton;
	private EditText mSearchEditText;
	private BaseAdapter mAdapter;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite);
//		setContentView(R.layout.book_recommend_item);

		
		setTopTitle(this,R.string.book_search);
		
		mGallery = (Gallery)findViewById(R.id.Gallery);
		mProgressBar = (ProgressBar)findViewById(R.id.ProgressBar);
		mFailureBar = (FailureBar)findViewById(R.id.FailureBar);
		mViewFlipper = (ViewFlipper)findViewById(R.id.ViewFlipper);
		
		mSearchEditText = (EditText)findViewById(R.id.et_search_key);
		//搜索按钮
		mSearchButton = (Button)findViewById(R.id.btn_do_search);
		//设置搜索按钮的监听器
		mSearchButton.setOnClickListener(mSearchButtonListener);
		
		//执行得到推荐新书的线程
		new NewsTaskGetBookRecommend().execute((Void)null);
		
		
		mSearchFlipper =(ViewFlipper)findViewById(R.id.SearchViewFlipper);
		
		//显示书目的listView
		mSearchListView = (ListView)findViewById(R.id.SearchListView);
		
		
		//如果当前listview中为空，则显示提示输入
		if(mSearchListView.getCount()==0){
			mSearchFlipper.setDisplayedChild(2);
		}
	
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
	
	
	//Gallery的异步线程
	private class NewsTaskGetBookRecommend extends AsyncTask<Void, WSError, BookRecommendEntity[]> {

		@Override
		public void onPreExecute() {
			mViewFlipper.setDisplayedChild(0);
			mProgressBar.setText(R.string.loading_book_recommend);
			super.onPreExecute();
		}

		@Override
		public BookRecommendEntity[] doInBackground(Void... params) {
			MobilelibraryResourceFromJSONRequest  server = new MobilelibraryResourceFromJSONRequest();
			BookRecommendEntity[] books = null;
			try {
				books = server.getBooksRecommend();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (WSError e){
				publishProgress(e);
			}
			return books;
		}

		@Override
		public void onPostExecute(BookRecommendEntity[] books) {

			if(books != null && books.length > 0){
				mViewFlipper.setDisplayedChild(1);
				BookRecommendAdapter booksAdapter = new BookRecommendAdapter(SearchActivity.this);
				booksAdapter.setList(books);
				mGallery.setAdapter(booksAdapter);
				mGallery.setOnItemClickListener(mGalleryListener);
				mGallery.setSelection(books.length/2, true); 
				
				

			} else {
				mViewFlipper.setDisplayedChild(2);
				mFailureBar.setOnRetryListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						new NewsTaskGetBookRecommend().execute((Void)null);
					}

				});
				mFailureBar.setText(R.string.connection_fail);
			}
			super.onPostExecute(books);
		}

		@Override
		protected void onProgressUpdate(WSError... values) {
			Toast.makeText(SearchActivity.this, values[0].getMessage(), Toast.LENGTH_LONG).show();
			super.onProgressUpdate(values);
		}
		
		

	}
	
	//Gallery的监听器
	private OnItemClickListener mGalleryListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long id) {
			BookRecommendEntity book = (BookRecommendEntity) adapterView.getItemAtPosition(position);
			//下面跳传到该图书的界面，需要加载
		}
		
	};
	
	
	private class NewTaskSearchingBook extends LoadingDialog<Void, Integer>{

		private Integer mSearchMode;
		private BaseAdapter mAdapter;

		public NewTaskSearchingBook(Activity activity, int loadingMsg, int failMsg) {
			super(activity, loadingMsg, failMsg);
		}

		@Override
		public Integer doInBackground(Void... params) {
			
			//后台查找书
			bookSearch();
			return 1;
		}

		@Override
		public void doStuffWithResult(Integer result) {

			mSearchListView.setAdapter(mAdapter);
			
			if(mSearchListView.getCount() > 0){
				mSearchFlipper.setDisplayedChild(0); 
			} else {
				mSearchFlipper.setDisplayedChild(1); 
			}
		    mSearchListView.setOnItemClickListener(mBookClickListener);
		
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
	
	
	
	//搜索按钮的监听器
	private OnClickListener mSearchButtonListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			new NewTaskSearchingBook(SearchActivity.this,
					R.string.wait_msg,
					R.string.wait_msg).execute((Void)null);	
		}

	};
	
	//书目listView的每一条目的监听器
	private OnItemClickListener mBookClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position,
				long time) {
			//跳传到该书的信息页
		}

	};

	
}
