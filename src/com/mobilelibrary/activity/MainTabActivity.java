package com.mobilelibrary.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

import com.mobilelibrary.R;

/**
 * tabhost activity
 * @author Ryan
 *
 */
public class MainTabActivity extends TabActivity implements
		OnCheckedChangeListener {

	private static final String TAB_MAIN_PAGE = "tab_main_page";
	private static final String TAB_COLLECT = "tab_collect";
	private static final String TAB_OTHER = "tab_other";

	private TabHost mTabHost;  
	private Intent mMainPageIntent;  
	private Intent mCollectIntent;  
	private Intent mOtherIntent;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.maintabs);
		
		this.mMainPageIntent = new Intent(this,MainPageActivity.class);
		this.mCollectIntent = new Intent(this,SearchActivity.class);
		this.mOtherIntent = new Intent(this,OtherActivity.class);
		

		((RadioButton) findViewById(R.id.tab_main_page)).setOnCheckedChangeListener(this);  
		((RadioButton) findViewById(R.id.tab_collect)).setOnCheckedChangeListener(this);  
		((RadioButton) findViewById(R.id.tab_other)).setOnCheckedChangeListener(this);
		
		setupTabHost();

	}
	
	private void setupTabHost(){
		this.mTabHost = getTabHost();
		TabHost tabHost = this.mTabHost;
		
		tabHost.addTab(buildTabSpec(TAB_MAIN_PAGE, R.string.main_page, R.drawable.main_page, this.mMainPageIntent));
		tabHost.addTab(buildTabSpec(TAB_COLLECT, R.string.search, R.drawable.collect, this.mCollectIntent));
		tabHost.addTab(buildTabSpec(TAB_OTHER, R.string.other, R.drawable.other, this.mOtherIntent));
		
		tabHost.setCurrentTab(0);
	}

	private TabHost.TabSpec buildTabSpec(String tag,int label,int icon,final Intent content){
		return this.mTabHost.newTabSpec(tag).setIndicator(getResources().getString(label), getResources().getDrawable(icon)).setContent(content);
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			int btn_id = buttonView.getId();
			switch (btn_id) {
			case R.id.tab_main_page:
				this.mTabHost.setCurrentTabByTag(TAB_MAIN_PAGE);
				break;
				
			case R.id.tab_collect:
				this.mTabHost.setCurrentTabByTag(TAB_COLLECT);
				break;
				
			case R.id.tab_other:
				this.mTabHost.setCurrentTabByTag(TAB_OTHER);
				break;
				
			default:
				break;
			}
		}
	}

}
