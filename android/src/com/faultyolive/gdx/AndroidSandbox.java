package com.faultyolive.gdx;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AndroidSandbox extends ListActivity {

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Sample.all()));
	}

	protected void onListItemClick (ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);

		Object o = this.getListAdapter().getItem(position);
		String sample = o.toString();

		Bundle bundle = new Bundle();
		bundle.putString("sample", sample);
		Intent intent = new Intent(this, SampleActivity.class);
		intent.putExtras(bundle);

		startActivity(intent);
	}
}
