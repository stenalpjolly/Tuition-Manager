package com.spj.tuitionmanager;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.widget.Toast;


public class TuitionList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		ListView listView = (ListView)findViewById(R.id.listFullListView);
		ArrayList<String> full_list = databaseHelper.getTuitionList();
		final ListAdapter tuition_list_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,full_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(TuitionList.this, tuition_list_adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                Intent tuitionDetails = new Intent(TuitionList.this,TuitionDetails.class);
                tuitionDetails.putExtra("TuitionName",tuition_list_adapter.getItem(position).toString());
                startActivity(tuitionDetails);
            }
        });
//		Toast.makeText(this, String.valueOf(full_list.size()), Toast.LENGTH_SHORT).show();
		
		listView.setAdapter(tuition_list_adapter);
		
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
