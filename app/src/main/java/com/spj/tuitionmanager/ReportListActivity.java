package com.spj.tuitionmanager;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;

public class ReportListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	@Override
	protected void onResume() {
		super.onResume();
		DatabaseHelper databaseHelper = new DatabaseHelper(this);
		ListView listView = (ListView) findViewById(R.id.listFullListView);
		ArrayList<String> full_list = databaseHelper.getReportList();
		final ListAdapter report_list_adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, full_list);
		listView.setAdapter(report_list_adapter);

		OnItemClickListener itemReport = new itemReportClickListener(this,
				report_list_adapter);

		listView.setOnItemClickListener(itemReport);
		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> av, View v, int pos,
					long id) {
				final String reportTitle = report_list_adapter.getItem(pos)
						.toString();

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ReportListActivity.this);

				// set title
				alertDialogBuilder.setTitle("Delete");

				// set dialog message
				alertDialogBuilder
						.setMessage("Do you want to delete Report '"+reportTitle+"'")
						.setCancelable(false)
						.setPositiveButton("DELETE",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										DatabaseHelper dbHelper = new DatabaseHelper(
												ReportListActivity.this);
										dbHelper.deleteReport(reportTitle);
										onResume();
									}
								})
						.setNegativeButton("CANCEL",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
				return true;
			}
		});
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
		getMenuInflater().inflate(R.menu.report, menu);
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
		case R.id.action_create_report:
			Intent intent = new Intent(this, CreateReport.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
