package com.mobilelibrary.activity;


import android.app.Activity;
import com.mobilelibrary.R;
import android.os.Bundle;

public class BookShelfActivity extends BaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookshelf);
		setTopTitle(this,R.string.my_storebook);

    }
}