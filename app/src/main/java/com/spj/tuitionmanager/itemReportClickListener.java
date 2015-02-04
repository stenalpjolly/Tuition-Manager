package com.spj.tuitionmanager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;

public class itemReportClickListener implements OnItemClickListener {
	
	private ListAdapter mListAdapter;
	private Context mContext;

	public itemReportClickListener(Context reportListActivity,
			ListAdapter report_list_adapter) {
		// TODO Auto-generated constructor stub
		
		mContext = reportListActivity;
		mListAdapter = report_list_adapter;
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long row) {
		// TODO Auto-generated method stub
		
		String reportTitle = mListAdapter.getItem(pos).toString();
		Intent reportGeneratorIntent = new Intent(mContext, ReportGenerator.class);
		reportGeneratorIntent.putExtra("reportTitle", reportTitle);
		mContext.startActivity(reportGeneratorIntent);
		

	}

}
