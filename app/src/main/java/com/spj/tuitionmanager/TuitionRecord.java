package com.spj.tuitionmanager;

import android.content.ContentValues;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Stenal P Jolly on 01-Feb-15.
 */
public class TuitionRecord {
    private final String vTuitionName;
    private final Context mContext;
    private String vDateFrom;
    private String vDateTo;
    private ArrayList<String> headerRow = new ArrayList<String>();
    private ArrayList<ArrayList<String>> tuitionTable = new ArrayList<ArrayList<String>>();

    TuitionRecord(Context context, String vTuitionName,String dateFrom, String dateTo) {
        this.mContext = context;
        this.vTuitionName = vTuitionName;
        this.vDateFrom = dateFrom;
        this.vDateTo = dateTo;
    }

    public ArrayList<String> getHeaderRow() {
        headerRow = getTitleRow();
        return headerRow;
    }

    public String getTuitionName() {
        return vTuitionName;
    }

    private ArrayList<String> getTitleRow() {
        ArrayList<String> header = new ArrayList<String>();
        if (vTuitionName != null) {
            header.add(vTuitionName);
            DatabaseHelper dbHelper = new DatabaseHelper(mContext);
            ContentValues latestRecord = dbHelper.getLatestDailyUpdateDetails(vTuitionName,vDateFrom,vDateTo);
            header.add("Last Payment On : ");
            header.add(latestRecord.get("LastPaymentOn").toString());
            header.add("Last Paid Amount : ");
            header.add(latestRecord.get("LastPaidAmount").toString());
            header.add("Next Payment On : ");
            header.add(latestRecord.get("NextPaymentOn").toString());
            header.add("Due Amount : ");
            header.add(latestRecord.get("DueAmount").toString());
            return header;
        } else {
            Toast.makeText(mContext, "Error during Title Parse", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void addDetailsTable(String DateFrom, String DateTo) {
        DatabaseHelper dbHelper = new DatabaseHelper(mContext);
        tuitionTable = dbHelper.getReportDetails_TuitionType(vTuitionName, DateFrom, DateTo);
    }

    public ArrayList<ArrayList<String>> getDetailedTuitionTable() {
        addDetailsTable(vDateFrom, vDateTo);
        return tuitionTable;
    }
}
