package com.spj.tuitionmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ReportGenerator extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = new View(this);
        String reportTitle;
        String reportType;

        reportTitle = getIntent().getExtras().getString("reportTitle");
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        reportType = dbHelper.getReportType(reportTitle);

        if (reportType.equals("Time Period"))
            contentView = timePeriodReport(reportTitle);
        if (reportType.equals("Tuition"))
            contentView = tuitionPeriodReport(reportTitle);
        if (reportType.equals("Fee Due"))
            contentView = feeDueReport(reportTitle);
        if (reportType.equals("Balance Sheet"))
            contentView = balanceSheetPeriodReport(reportTitle);
        // setContentView(R.layout.activity_report_generator);
        setContentView(contentView);
        // Show the Up button in the action bar.
        setupActionBar();
    }

    private View feeDueReport(String reportTitle) {

        View reportView = LayoutInflater.from(this).inflate(
                R.layout.activity_report_generator, null);

        LinearLayout layoutReportHeader = (LinearLayout) reportView
                .findViewById(R.id.layout_header_section);
        TableLayout reportTable = (TableLayout) reportView
                .findViewById(R.id.table_report);

        LinearLayout.LayoutParams fullWidthParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // fullWidthParams.setMargins(5, 5, 5, 5);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ContentValues reportDetails = dbHelper.getReportRecord(reportTitle);


        //Checking for Custom Period
        String customDateAsString = reportDetails
                .getAsString(DatabaseHelper.COLUMN_custom_time_check);
        Boolean customDate = (!customDateAsString.equals("0"));
        Log.v("Query", customDate.toString());

        String dateFrom = null, dateTo = null;
        if (customDate) {
            dateFrom = reportDetails
                    .getAsString(DatabaseHelper.COLUMN_date_from);
            dateTo = reportDetails.getAsString(DatabaseHelper.COLUMN_date_to);
        }

//        ArrayList<String> TuitionList = new ArrayList<String>();
//        /*
//        * Check for Specific Tuition Name
//        * If One Tuition is Mentioned then Add only that name to TuitionList
//        * el    se add all Tuition Names to sort Sue Amount wise in Descending order
//        * */

        ArrayList<TuitionRecord> tuitionList =  dbHelper.getReportDetails_FeeDueType(false, null, false, null, null);
        TuitionRecord tuitionRecord;

        for (int j = 0; j < tuitionList.size(); j++) {
           tuitionRecord = tuitionList.get(j);
            //For Each Tuition There exist a header
            ArrayList<String> headerList = tuitionRecord.getHeaderRow();
            String heading = new String();
            TableRow tableRow = new TableRow(this);
            for(int i=0;i<headerList.size();i++){
                heading = headerList.get(i);
                TextView lblCell = new TextView(this);
                lblCell.setText(heading);
                lblCell.setPadding(10, 7, 7, 7);
                tableRow.addView(lblCell);
            }
            reportTable.addView(tableRow);
        }

        return reportView;
    }


    private View timePeriodReport(String reportTitle) {
        // TODO Auto-generated method stub
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View reportView = LayoutInflater.from(this).inflate(
                R.layout.activity_report_generator, null);

        LinearLayout layoutReportHeader = (LinearLayout) reportView
                .findViewById(R.id.layout_header_section);
        TableLayout reportTable = (TableLayout) reportView
                .findViewById(R.id.table_report);

        LinearLayout.LayoutParams fullWidthParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // fullWidthParams.setMargins(5, 5, 5, 5);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        ContentValues reportDetails = dbHelper.getReportRecord(reportTitle);
        String dateFrom = reportDetails
                .getAsString(DatabaseHelper.COLUMN_date_from);
        String dateTo = reportDetails
                .getAsString(DatabaseHelper.COLUMN_date_to);
        TextView txtPeriodHeader = new TextView(this);
        txtPeriodHeader.setText("Period: " + dateFrom + " to " + dateTo);
        txtPeriodHeader.setTextColor(Color.WHITE);
        txtPeriodHeader.setTextSize(16);
        txtPeriodHeader.setPadding(5, 5, 5, 5);
        txtPeriodHeader.setBackgroundColor(Color.rgb(20, 58, 58));
        txtPeriodHeader.setLayoutParams(fullWidthParams);

        ArrayList<ArrayList<String>> tableReportList = dbHelper
                .getReportDetails_TimeType(dateFrom, dateTo);
        ArrayList<String> row = new ArrayList<String>();

        // For Header
        row = (ArrayList<String>) tableReportList.get(0);
        TableRow tableHeaderRow = new TableRow(this);
        TableRow.LayoutParams headerParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < row.size(); i++) {
            TextView lblHeaderCell = new TextView(this);
            lblHeaderCell.setText(row.get(i));
            lblHeaderCell.setGravity(Gravity.CENTER);
            // lblHeaderCell.setBackgroundColor(Color.rgb(20, 58, 58));
            lblHeaderCell.setTextColor(Color.WHITE);
            lblHeaderCell.setTypeface(null, Typeface.BOLD);
            lblHeaderCell.setPadding(7, 7, 7, 7);
            lblHeaderCell.setEllipsize(TruncateAt.START);
            lblHeaderCell.setMaxLines(2);
            lblHeaderCell.setBackgroundResource(R.drawable.header);
            lblHeaderCell.setLayoutParams(headerParams);
            tableHeaderRow.addView(lblHeaderCell);
        }
        reportTable.addView(tableHeaderRow);

        // For Content
        for (int j = 1; j < tableReportList.size() - 1; j++) {
            row = (ArrayList<String>) tableReportList.get(j);
            TableRow tableRow = new TableRow(this);
            for (int i = 0; i < row.size(); i++) {
                TextView lblCell = new TextView(this);
                lblCell.setText(row.get(i));
                lblCell.setPadding(7, 7, 7, 7);
                lblCell.setBackgroundResource(R.drawable.cell);
                tableRow.addView(lblCell);
            }
            reportTable.addView(tableRow);
        }

        // For Footer Total
        row = (ArrayList<String>) tableReportList
                .get(tableReportList.size() - 1);
        TableRow tableFooterRow = new TableRow(this);
        for (int i = 0; i < row.size(); i++) {
            TextView lblFooterCell = new TextView(this);
            lblFooterCell.setText(row.get(i));
            lblFooterCell.setTextColor(Color.WHITE);
            lblFooterCell.setTypeface(null, Typeface.BOLD);
            lblFooterCell.setPadding(7, 7, 7, 7);
            lblFooterCell.setBackgroundResource(R.drawable.header);
            tableFooterRow.addView(lblFooterCell);
        }
        reportTable.addView(tableFooterRow);

        // Toast.makeText(this, String.valueOf(tableReport.length),
        // Toast.LENGTH_SHORT).show();

        layoutReportHeader.addView(txtPeriodHeader);
        return reportView;
    }

    private View balanceSheetPeriodReport(String reportTitle) {
        // TODO Auto-generated method stub
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View reportView = LayoutInflater.from(this).inflate(
                R.layout.activity_report_generator, null);

        LinearLayout layoutReportHeader = (LinearLayout) reportView
                .findViewById(R.id.layout_header_section);
        TableLayout reportTable = (TableLayout) reportView
                .findViewById(R.id.table_report);

        LinearLayout.LayoutParams fullWidthParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // fullWidthParams.setMargins(5, 5, 5, 5);

        TableRow.LayoutParams headerParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        ContentValues reportDetails = dbHelper.getReportRecord(reportTitle);

        String customTargetAsString = reportDetails
                .getAsString(DatabaseHelper.COLUMN_set_target_amount_check);
        Boolean isCustomTarget = (!customTargetAsString.equals("0"));
        String customTuitionAsString = reportDetails
                .getAsString(DatabaseHelper.COLUMN_specify_tuition_check);
        Boolean isCustomTuition = (!customTuitionAsString.equals("0"));
        String customDateAsString = reportDetails
                .getAsString(DatabaseHelper.COLUMN_custom_time_check);
        Boolean isCustomDate = (!customDateAsString.equals("0"));

        String dateFrom = reportDetails
                .getAsString(DatabaseHelper.COLUMN_date_from);
        String dateTo = reportDetails
                .getAsString(DatabaseHelper.COLUMN_date_to);
        String tuitionName = reportDetails
                .getAsString(DatabaseHelper.COLUMN_tuition_name);
        Double targetAmount = reportDetails
                .getAsDouble(DatabaseHelper.COLUMN_target_amount);
        if (isCustomDate) {
            TextView txtPeriodHeader = new TextView(this);
            txtPeriodHeader.setText("Period: " + dateFrom + " to " + dateTo);
            txtPeriodHeader.setTextColor(Color.WHITE);
            txtPeriodHeader.setTextSize(16);
            txtPeriodHeader.setPadding(5, 5, 5, 5);
            txtPeriodHeader.setBackgroundColor(Color.rgb(20, 58, 58));
            txtPeriodHeader.setLayoutParams(fullWidthParams);
            layoutReportHeader.addView(txtPeriodHeader);
        }
        if (isCustomTuition) {
            TextView txtPeriodHeader = new TextView(this);
            txtPeriodHeader.setText("Tuition : " + tuitionName);
            txtPeriodHeader.setTextColor(Color.WHITE);
            txtPeriodHeader.setTextSize(16);
            txtPeriodHeader.setPadding(5, 5, 5, 5);
            txtPeriodHeader.setBackgroundColor(Color.rgb(20, 58, 58));
            txtPeriodHeader.setLayoutParams(fullWidthParams);
            layoutReportHeader.addView(txtPeriodHeader);
        }
        if (isCustomTarget) {
            TextView txtPeriodHeader = new TextView(this);
            txtPeriodHeader
                    .setText("Target Amount :" + targetAmount.toString());
            txtPeriodHeader.setTextColor(Color.WHITE);
            txtPeriodHeader.setTextSize(16);
            txtPeriodHeader.setPadding(5, 5, 5, 5);
            txtPeriodHeader.setBackgroundColor(Color.rgb(20, 58, 58));
            txtPeriodHeader.setLayoutParams(fullWidthParams);
            layoutReportHeader.addView(txtPeriodHeader);
        }
        ArrayList<ReportBalanceSheet> tableReportList = dbHelper
                .getReportDetails_BalanceSheetType(isCustomTuition, tuitionName, isCustomTarget, isCustomDate, dateFrom, dateTo);
        ReportBalanceSheet singleBalanceSheet = new ReportBalanceSheet();

        // For Each Year Balance Sheet
        for (int j = 0; j < tableReportList.size(); j++) {
            singleBalanceSheet = (ReportBalanceSheet) tableReportList.get(j);
            TextView lblYearCell = new TextView(this);
            lblYearCell.setText(singleBalanceSheet.getYear());
            lblYearCell.setTextColor(Color.WHITE);
            lblYearCell.setTypeface(null, Typeface.BOLD);
            lblYearCell.setPadding(7, 7, 7, 7);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    LayoutParams.MATCH_PARENT, 5);
            View hr = new View(this);
            hr.setLayoutParams(layoutParams);
            reportTable.addView(hr);

            lblYearCell.setBackgroundResource(R.drawable.header);
            reportTable.addView(lblYearCell);

            ArrayList<String> row = ReportBalanceSheet.getHeader();
            TableRow tableRow = new TableRow(this);
            for (int i = 0; i < row.size(); i++) {
                TextView lblHeaderCell = new TextView(this);
                lblHeaderCell.setText(row.get(i));
                lblHeaderCell.setGravity(Gravity.CENTER);
                lblHeaderCell.setTextColor(Color.WHITE);
                lblHeaderCell.setTypeface(null, Typeface.BOLD);
                lblHeaderCell.setPadding(7, 7, 7, 7);
                lblHeaderCell.setBackgroundResource(R.drawable.header);
                lblHeaderCell.setLayoutParams(headerParams);
                tableRow.addView(lblHeaderCell);
            }
            reportTable.addView(tableRow);
            // For Content Addition
            ArrayList<ArrayList<String>> singleReportTable = new ArrayList<ArrayList<String>>();
            singleReportTable = singleBalanceSheet.getTable();
            for (int jRow = 0; jRow < singleReportTable.size() - 1; jRow++) {
                row = (ArrayList<String>) singleReportTable.get(jRow);
                TableRow singleRow = new TableRow(this);
                for (int i = 0; i < row.size(); i++) {
                    TextView lblCell = new TextView(this);
                    lblCell.setText(row.get(i));
                    lblCell.setPadding(7, 7, 7, 7);
                    lblCell.setBackgroundResource(R.drawable.cell);
                    singleRow.addView(lblCell);
                }
                reportTable.addView(singleRow);
            }
            row = (ArrayList<String>) singleReportTable.get(singleReportTable
                    .size() - 1);
            TableRow singleRow = new TableRow(this);
            for (int i = 0; i < row.size(); i++) {
                TextView lblFooterCell = new TextView(this);
                lblFooterCell.setText(row.get(i));
                lblFooterCell.setTextColor(Color.WHITE);
                lblFooterCell.setTypeface(null, Typeface.BOLD);
                lblFooterCell.setPadding(7, 7, 7, 7);
                lblFooterCell.setBackgroundResource(R.drawable.header);
                singleRow.addView(lblFooterCell);
            }
            reportTable.addView(singleRow);
            // //////////////
        }

        // Toast.makeText(this, String.valueOf(tableReportList.size()),
        // Toast.LENGTH_SHORT).show();

        return reportView;
    }

    private View tuitionPeriodReport(String reportTitle) {
        // TODO Auto-generated method stub
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View reportView = LayoutInflater.from(this).inflate(
                R.layout.activity_report_generator, null);

        LinearLayout layoutReportHeader = (LinearLayout) reportView
                .findViewById(R.id.layout_header_section);
        TableLayout reportTable = (TableLayout) reportView
                .findViewById(R.id.table_report);

        LinearLayout.LayoutParams fullWidthParams = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // fullWidthParams.setMargins(5, 5, 5, 5);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        ContentValues reportDetails = dbHelper.getReportRecord(reportTitle);

        String customDateAsString = reportDetails
                .getAsString(DatabaseHelper.COLUMN_custom_time_check);
        Boolean customDate = (!customDateAsString.equals("0"));
        Log.v("Query", customDate.toString());

        String dateFrom = null, dateTo = null;
        if (customDate) {
            dateFrom = reportDetails
                    .getAsString(DatabaseHelper.COLUMN_date_from);
            dateTo = reportDetails.getAsString(DatabaseHelper.COLUMN_date_to);
        }

        String tuitionName = reportDetails
                .getAsString(DatabaseHelper.COLUMN_tuition_name);
        TextView txtTutionNameHeader = new TextView(this);
        txtTutionNameHeader.setText("Tuition : " + tuitionName);
        txtTutionNameHeader.setTextColor(Color.WHITE);
        txtTutionNameHeader.setTextSize(16);
        txtTutionNameHeader.setPadding(5, 5, 5, 5);
        txtTutionNameHeader.setBackgroundColor(Color.rgb(20, 58, 58));
        txtTutionNameHeader.setLayoutParams(fullWidthParams);
        layoutReportHeader.addView(txtTutionNameHeader);
        if (dateFrom != null && dateTo != null) {
            TextView txtPeriodHeader = new TextView(this);
            txtPeriodHeader.setText("Period: " + dateFrom + " to " + dateTo);
            txtPeriodHeader.setTextColor(Color.WHITE);
            txtPeriodHeader.setTextSize(16);
            txtPeriodHeader.setPadding(5, 5, 5, 5);
            txtPeriodHeader.setBackgroundColor(Color.rgb(20, 58, 58));
            txtPeriodHeader.setLayoutParams(fullWidthParams);
            layoutReportHeader.addView(txtPeriodHeader);
        }

        ArrayList<ArrayList<String>> tableReportList = dbHelper
                .getReportDetails_TuitionType(tuitionName, dateFrom, dateTo);
        ArrayList<String> row = new ArrayList<String>();

        // For Header
        row = (ArrayList<String>) tableReportList.get(0);
        TableRow tableHeaderRow = new TableRow(this);
        TableRow.LayoutParams headerParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < row.size(); i++) {
            TextView lblHeaderCell = new TextView(this);
            lblHeaderCell.setText(row.get(i));
            lblHeaderCell.setGravity(Gravity.CENTER);
            // lblHeaderCell.setBackgroundColor(Color.rgb(20, 58, 58));
            lblHeaderCell.setTextColor(Color.WHITE);
            lblHeaderCell.setTypeface(null, Typeface.BOLD);
            lblHeaderCell.setPadding(7, 7, 7, 7);
            lblHeaderCell.setEllipsize(TruncateAt.START);
            lblHeaderCell.setMaxLines(2);
            lblHeaderCell.setBackgroundResource(R.drawable.header);
            lblHeaderCell.setLayoutParams(headerParams);
            tableHeaderRow.addView(lblHeaderCell);
        }
        reportTable.addView(tableHeaderRow);

        // For Content
        for (int j = 1; j < tableReportList.size() - 1; j++) {
            row = (ArrayList<String>) tableReportList.get(j);
            TableRow tableRow = new TableRow(this);
            for (int i = 0; i < row.size(); i++) {
                TextView lblCell = new TextView(this);
                lblCell.setText(row.get(i));
                lblCell.setPadding(7, 7, 7, 7);
                lblCell.setBackgroundResource(R.drawable.cell);
                tableRow.addView(lblCell);
            }
            reportTable.addView(tableRow);
        }

        // For Footer Total
        row = (ArrayList<String>) tableReportList
                .get(tableReportList.size() - 1);
        TableRow tableFooterRow = new TableRow(this);
        for (int i = 0; i < row.size(); i++) {
            TextView lblFooterCell = new TextView(this);
            lblFooterCell.setText(row.get(i));
            lblFooterCell.setTextColor(Color.WHITE);
            lblFooterCell.setTypeface(null, Typeface.BOLD);
            lblFooterCell.setPadding(7, 7, 7, 7);
            lblFooterCell.setBackgroundResource(R.drawable.header);
            tableFooterRow.addView(lblFooterCell);
        }
        reportTable.addView(tableFooterRow);

        // Toast.makeText(this, String.valueOf(tableReport.length),
        // Toast.LENGTH_SHORT).show();

        return reportView;
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
//        getMenuInflater().inflate(R.menu.report_generator, menu);
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
