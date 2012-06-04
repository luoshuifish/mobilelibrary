package com.mobilelibrary.activity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mobilelibrary.R;
import com.mobilelibrary.adapter.PersonInformationAdapter;

public class PersonInformation  extends BaseActivity{
	
    private ListView personInformationListView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_imfomation);
        personInformationListView = (ListView) this.findViewById(R.id.lv_other);
		personInformationListView.setAdapter(new PersonInformationAdapter(this));
    }

}
