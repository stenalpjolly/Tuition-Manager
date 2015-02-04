package com.spj.tuitionmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btnExit = (Button)findViewById(R.id.btnExit);
		Button btnNewTuition = (Button)findViewById(R.id.btnNewTuition);
		Button btnNewEvent = (Button)findViewById(R.id.btnNewEvent);
		Button btnDailyUpdate = (Button)findViewById(R.id.btnDailyUpdate);
		Button btnList = (Button)findViewById(R.id.btnList);
		Button btnReports = (Button)findViewById(R.id.btnReport);
		
		
		btnReports.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent Reports = new Intent(MainActivity.this, ReportListActivity.class);
				startActivity(Reports);
			}
		});
		btnList.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
//				Toast.makeText(getApplicationContext(), String.valueOf(dbHelper.getTupleCountOfDailyUpdate()), Toast.LENGTH_LONG).show();
//				Toast.makeText(getApplicationContext(), String.valueOf(dbHelper.getReadableDatabase().getPath()), Toast.LENGTH_LONG).show();
				Intent TuitionList = new Intent(MainActivity.this, com.spj.tuitionmanager.TuitionList.class);
				startActivity(TuitionList);
			}
		});
		
		btnExit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Exiting..", Toast.LENGTH_LONG).show();
				finish();
			}
		});
		btnNewTuition.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent NewTuition = new Intent(getApplicationContext(), NewTuitionActivity.class);
				startActivity(NewTuition);
			}
		});
		btnNewEvent.setOnClickListener(new View.OnClickListener() {

			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent NewEvent = new Intent(getApplicationContext(), NewEventActivity.class);
				startActivity(NewEvent);
				
			}
		});
		btnDailyUpdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent NewTuition = new Intent(getApplicationContext(), DailyUpdate.class);
				startActivity(NewTuition);
			}
		});
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
