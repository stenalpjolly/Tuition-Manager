package com.spj.tuitionmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteAssetHelper {

    // Table Name
    public static final String TABLE_classes_on = "classes_on";
    public static final String TABLE_daily_update_list = "daily_update_list";
    public static final String TABLE_events_list = "events_list";
    public static final String TABLE_events_on = "events_on";
    public static final String TABLE_tuition_list = "tuition_list";
    public static final String TABLE_reports_details = "report_details";
    // Column Name
    public static final String COLUMN_fullname = "fullname";
    public static final String COLUMN_class = "class";
    public static final String COLUMN_address1 = "address1";
    public static final String COLUMN_address2 = "address2";
    public static final String COLUMN_email = "email";
    public static final String COLUMN_phone = "phone";
    public static final String COLUMN_class_time_id = "class_time_id";
    public static final String COLUMN_event_id = "event_time_id";
    public static final String COLUMN_no_of_hour = "no_of_hour";
    public static final String COLUMN_pay_per_hour = "pay_per_hour";
    public static final String COLUMN_enable_commission = "enable_commission";
    public static final String COLUMN_payment_method = "payment_method";
    public static final String COLUMN_status = "status";
    public static final String COLUMN_week_day = "week_day";
    public static final String COLUMN_subject = "subject";
    public static final String COLUMN_time = "time";
    public static final String COLUMN_date = "date";
    public static final String COLUMN_time_from = "time_from";
    public static final String COLUMN_time_to = "time_to";
    public static final String COLUMN_payment_received = "payment_received";
    public static final String COLUMN_no_of_paid_session = "no_of_paid_session";
    public static final String COLUMN_no_of_session = "no_of_session";
    public static final String COLUMN_amount = "amount";
    public static final String COLUMN_year = "year";
    public static final String COLUMN_no_of_commission_session = "no_of_commission_session";
    public static final String COLUMN_report_title = "report_title";
    public static final String COLUMN_based_on = "based_on";
    public static final String COLUMN_date_from = "date_from";
    public static final String COLUMN_date_to = "date_to";
    public static final String COLUMN_tuition_name = "tuition_name";
    public static final String COLUMN_custom_time_check = "custom_time_check";
    public static final String COLUMN_specify_tuition_check = "specify_tuition_check";
    public static final String COLUMN_set_target_amount_check = "set_target_amount_check";
    public static final String COLUMN_target_amount = "target_amount";
    public static final String COLUMN_commission_amount = "commission_amount";
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "tuition_manager.sqlite";
    private static Context mContext = null;

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;

    }

    private static int getMinBetweenTime(String timeFrom, String timeTo) {
        int min = 0;
        String[] temp_Ftime1 = timeFrom.split(":");
        String[] temp_Ftime2 = temp_Ftime1[1].split(" ");
        int fHr = Integer.parseInt(temp_Ftime1[0]);
        if (temp_Ftime2[1].equals("pm"))
            fHr = fHr + 12;
        int fMin = Integer.parseInt(temp_Ftime2[0]);

        String[] temp_Ttime1 = timeTo.split(":");
        String[] temp_Ttime2 = temp_Ttime1[1].split(" ");
        int tHr = Integer.parseInt(temp_Ttime1[0]);
        if (temp_Ttime2[1].equals("pm"))
            tHr = tHr + 12;
        int tMin = Integer.parseInt(temp_Ttime2[0]);

        min = ((tHr - fHr) * 60) + tMin - fMin;
        return min;
    }

    public Boolean insertIntoTuitionList(Context context, String fullname,
                                         String class_name, String address1, String address2, String email,
                                         String phone, String class_id, String no_of_hours,
                                         String pay_per_hour, Boolean enable_commission,
                                         String payment_method, String status, ArrayList<String> week_day,
                                         ArrayList<String> subject, ArrayList<String> time) {

        db = getWritableDatabase();
        ContentValues row = new ContentValues();
        // ContentValues classes_time_row = new ContentValues(week_day.size());

        if (week_day.size() <= 0) {
            Toast.makeText(context, "No Weekday Selected", Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        row.put(COLUMN_fullname, fullname);
        row.put(COLUMN_class, class_name);
        row.put(COLUMN_address1, address1);
        row.put(COLUMN_address2, address2);
        row.put(COLUMN_email, email);
        row.put(COLUMN_phone, phone);
        row.put(COLUMN_class_time_id, class_id);
        row.put(COLUMN_no_of_hour, no_of_hours);
        row.put(COLUMN_pay_per_hour, pay_per_hour);
        row.put(COLUMN_enable_commission, enable_commission);
        row.put(COLUMN_payment_method, payment_method);
        row.put(COLUMN_status, status);

        Calendar calendar = Calendar.getInstance();
        row.put(COLUMN_year, calendar.get(Calendar.YEAR));

        db.insert(TABLE_tuition_list, null, row);

        for (int i = 0; i < week_day.size(); i++) {
            ContentValues classes_on = new ContentValues();
            classes_on.clear();

            classes_on.put(COLUMN_class_time_id, class_id);
            classes_on.put(COLUMN_week_day, week_day.get(i));
            classes_on.put(COLUMN_subject, subject.get(i));
            classes_on.put(COLUMN_time, time.get(i));

            db.insert(TABLE_classes_on, null, classes_on);
        }

        db.close();
        return true;
    }

    public Boolean insertIntoDailyEvents(Context context, String fullname,
                                         String date, String time_from, String time_to,
                                         String PayCommission, Double CommissionSessionNo,
                                         String PaymentRecevied, Double PaymentSessionNo, Double SessionNo,
                                         Double Amount, Double CommissionAmount) {

        db = getWritableDatabase();
        ContentValues row = new ContentValues();

        // ContentValues classes_time_row = new ContentValues(week_day.size());

        row.put(COLUMN_fullname, fullname);
        row.put(COLUMN_date, date);
        row.put(COLUMN_time_from, time_from);
        row.put(COLUMN_time_to, time_to);
        row.put(COLUMN_enable_commission, PayCommission);
        row.put(COLUMN_no_of_commission_session, CommissionSessionNo);
        row.put(COLUMN_payment_received, PaymentRecevied);
        row.put(COLUMN_no_of_paid_session, PaymentSessionNo);
        row.put(COLUMN_no_of_session, SessionNo);
        row.put(COLUMN_amount, Amount);
        row.put(COLUMN_commission_amount, CommissionAmount);

        db.insert(TABLE_daily_update_list, null, row);

        db.close();
        return true;
    }

    public Boolean insertIntoEventList(String fullname, String address1,
                                       String address2, String email, String phone, String event_id,
                                       String no_of_hours, String pay_per_hour, String payment_method,
                                       String status, ArrayList<String> week_day, ArrayList<String> time) {

        db = getWritableDatabase();
        ContentValues row = new ContentValues();

        if (week_day.size() <= 0) {
            Toast.makeText(mContext, "No Weekday Selected", Toast.LENGTH_LONG)
                    .show();
            return false;
        }

        row.put(COLUMN_fullname, fullname);
        row.put(COLUMN_address1, address1);
        row.put(COLUMN_address2, address1);
        row.put(COLUMN_email, email);
        row.put(COLUMN_phone, phone);
        row.put(COLUMN_class_time_id, event_id);
        row.put(COLUMN_no_of_hour, no_of_hours);
        row.put(COLUMN_pay_per_hour, pay_per_hour);
        row.put(COLUMN_payment_method, payment_method);
        row.put(COLUMN_status, status);

        db.insert(TABLE_events_list, null, row);

        for (int i = 0; i < week_day.size(); i++) {
            ContentValues classes_on = new ContentValues();
            classes_on.clear();

            classes_on.put(COLUMN_event_id, event_id);
            classes_on.put(COLUMN_week_day, week_day.get(i));
            classes_on.put(COLUMN_time, time.get(i));

            db.insert(TABLE_events_on, null, classes_on);
        }

        db.close();
        return true;
    }

    public void insertIntoReports(Context context,
                                  ContentValues fullDataRowToStore) {
        // TODO Auto-generated method stub
        db = getWritableDatabase();
        db.insert(TABLE_reports_details, null, fullDataRowToStore);
        db.close();
    }

    public ContentValues getTuitionRecord(Context context, String where) {
        ContentValues row = new ContentValues();
        db = this.getReadableDatabase();
        String sql_query = "Select * from " + TABLE_tuition_list + " where "
                + where;
        Cursor row_cursor = db.rawQuery(sql_query, null);
        if (row_cursor.getCount() <= 0)
            Toast.makeText(context, "Error..No Records Found",
                    Toast.LENGTH_LONG).show();
        else {
            if (row_cursor != null)
                row_cursor.moveToFirst();
            row.put(COLUMN_fullname, row_cursor.getString(0));
            row.put(COLUMN_class, row_cursor.getString(1));
            row.put(COLUMN_address1, row_cursor.getString(2));
            row.put(COLUMN_address2, row_cursor.getString(3));
            row.put(COLUMN_email, row_cursor.getString(4));
            row.put(COLUMN_phone, row_cursor.getString(5));
            row.put(COLUMN_class_time_id, row_cursor.getString(6));
            row.put(COLUMN_no_of_hour, row_cursor.getString(7));
            row.put(COLUMN_pay_per_hour, row_cursor.getString(8));
            row.put(COLUMN_enable_commission, row_cursor.getString(9));
            row.put(COLUMN_payment_method, row_cursor.getString(10));
            row.put(COLUMN_status, row_cursor.getString(11));
        }
        row_cursor.close();
        return row;
    }

    public ArrayList<ArrayList<String>> getTuitionClasses(String class_time_id) {
        db = getReadableDatabase();
        ArrayList<ArrayList<String>> classesOn = new ArrayList<ArrayList<String>>();
        String query = "Select * from " + TABLE_classes_on + " where " + COLUMN_class_time_id + " ='" + class_time_id + "'";
        Cursor class_cursor = db.rawQuery(query, null);
        if (class_cursor != null)
            class_cursor.moveToFirst();
        for (int i = 0; i < class_cursor.getCount(); i++, class_cursor.moveToNext()) {
            ArrayList<String> temp_row = new ArrayList<String>();
            temp_row.clear();
            temp_row.add(class_cursor.getString(class_cursor.getColumnIndex(COLUMN_week_day)));
            temp_row.add(class_cursor.getString(class_cursor.getColumnIndex(COLUMN_subject)));
            temp_row.add(class_cursor.getString(class_cursor.getColumnIndex(COLUMN_time)));
            classesOn.add(temp_row);
        }
        class_cursor.close();
        db.close();
        return classesOn;
    }

    public String getNearTuitionTime(Context context, String class_time_id) {
        String near_time = new String("Time");
        db = getReadableDatabase();
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday"};
        String[] columns = {COLUMN_week_day, COLUMN_time};
        String where = COLUMN_class_time_id + " = '" + class_time_id + "'";
        Cursor classes_on_Cursor = db.query(TABLE_classes_on, columns, where,
                null, null, null, null);
        // Cursor classes_on_Cursor = db.rawQuery("Select * from " +
        // TABLE_classes_on, null);
        if (classes_on_Cursor.getCount() <= 0) {
            Toast.makeText(context, "No Classes Selected", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        Integer n = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Boolean flag = true;
        for (int i = 0; i < 7 && flag; i++, n--) {
            if (n < 0)
                n = 6;
            String day_of_the_week = days[n];
            classes_on_Cursor.moveToFirst();
            do {
                if (classes_on_Cursor.getString(0).equals(day_of_the_week)) {
                    near_time = classes_on_Cursor.getString(1);
                    flag = false;
                    break;
                }

            } while (classes_on_Cursor.moveToNext());
            // Toast.makeText(context,day_of_the_week,
            // Toast.LENGTH_SHORT).show();
        }
        classes_on_Cursor.close();
        return near_time;
    }

    public ArrayList<String> getTuitionList() {
        ArrayList<String> list = new ArrayList<String>();
        db = getReadableDatabase();
        String[] columns = {"fullname"};
        Cursor table_data = db.query(TABLE_tuition_list, columns, null, null,
                null, null, "fullname");
        if (table_data != null)
            table_data.moveToFirst();
        for (int i = 0; i < table_data.getCount(); i++) {
            list.add(table_data.getString(0));
            table_data.moveToNext();
        }
        table_data.close();
        return list;
    }

    // private ArrayList<ContentValues> getList(String table_name) {
    // ArrayList<ContentValues> list = new ArrayList<ContentValues>();
    // ContentValues singleRow = new ContentValues();
    // db = getReadableDatabase();
    //
    // String sql_statment = "Select * from " + table_name;
    // Cursor table_data = db.rawQuery(sql_statment, null);
    //
    // Integer columnCount = table_data.getColumnCount();
    // Integer rowCount = table_data.getCount();
    //
    // return list;
    // }

    public ArrayList<String> getActiveTuitionList() {
        ArrayList<String> list = new ArrayList<String>();
        db = getReadableDatabase();
        // String sql_statment = "Select fullname from " + TABLE_tuition_list +
        // " where status='ACTIVE'";
        // Cursor table_data = db.rawQuery(sql_statment, null);
        String where = " status='ACTIVE'";
        String[] columns = {"fullname"};
        Cursor table_data = db.query(TABLE_tuition_list, columns, where, null,
                null, null, "fullname");
        if (table_data != null)
            table_data.moveToFirst();
        for (int i = 0; i < table_data.getCount(); i++) {
            list.add(table_data.getString(0));
            table_data.moveToNext();
        }
        table_data.close();
        return list;
    }

    public String getReportType(String reportTitle) {

        db = getReadableDatabase();
        String[] columns = {COLUMN_based_on};
        String where = COLUMN_report_title + " = '" + reportTitle + "'";
        Cursor table_data = db.query(TABLE_reports_details, columns, where,
                null, null, null, null);
        if (table_data != null)
            table_data.moveToFirst();
        String reportType = table_data.getString(0);
        table_data.close();
        return reportType;
    }

    public ContentValues getReportRecord(String reportTitle) {
        ContentValues row = new ContentValues();
        db = this.getReadableDatabase();
        String sql_query = "Select * from " + TABLE_reports_details + " where "
                + COLUMN_report_title + " = '" + reportTitle + "'";
        Cursor row_cursor = db.rawQuery(sql_query, null);
        if (row_cursor.getCount() <= 0)
            Toast.makeText(mContext, "Error..No Records Found",
                    Toast.LENGTH_LONG).show();
        else {
            if (row_cursor != null)
                row_cursor.moveToFirst();
            row.put(COLUMN_report_title, row_cursor.getString(0));
            row.put(COLUMN_based_on, row_cursor.getString(1));
            row.put(COLUMN_date_from, row_cursor.getString(2));
            row.put(COLUMN_date_to, row_cursor.getString(3));
            row.put(COLUMN_tuition_name, row_cursor.getString(4));
            row.put(COLUMN_custom_time_check, row_cursor.getString(5));
            row.put(COLUMN_specify_tuition_check, row_cursor.getString(6));
            row.put(COLUMN_set_target_amount_check, row_cursor.getString(7));
            row.put(COLUMN_target_amount, row_cursor.getString(8));
        }
        row_cursor.close();
        return row;
    }

    public ArrayList<String> getReportList() {
        // TODO Auto-generated method stub
        ArrayList<String> list = new ArrayList<String>();
        db = getReadableDatabase();
        String[] columns = {COLUMN_report_title};
        Cursor table_data = db.query(TABLE_reports_details, columns, null,
                null, null, null, COLUMN_report_title);
        if (table_data != null)
            table_data.moveToFirst();
        for (int i = 0; i < table_data.getCount(); i++) {
            list.add(table_data.getString(0));
            table_data.moveToNext();
        }
        table_data.close();
        return list;
    }

    public ArrayList<ArrayList<String>> getReportDetails_TimeType(
            String DateFrom, String DateTo) {

        db = getReadableDatabase();
        String rawQuery = "Select * from " + TABLE_daily_update_list;
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<ArrayList<String>> tableReportList = new ArrayList<ArrayList<String>>();
        ArrayList<String> row = new ArrayList<String>();
        row.add("Date");
        row.add("Time");
        row.add("Student Name");
        row.add("No.of Commission\r\nSession");
        row.add("No.of\r\nPaid Session");
        row.add("No.of\r\nSession");
        row.add("Amount");

        tableReportList.add(row);
        Double total_no_of_commission_session = 0.0;
        Double total_no_of_paid_session = 0.0;
        Double total_no_of_session = 0.0;
        Double total_amount = 0.0;

        for (int i = 0; i < count; i++, cursor.moveToNext()) {
            ArrayList<String> temp = new ArrayList<String>();
            temp.clear();
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_date));

            // Date Comparison
            if (!isDateBetween(date, DateFrom, DateTo)) {
                continue;
            }
            temp.add(date);
            String time = cursor.getString(cursor
                    .getColumnIndex(COLUMN_time_from))
                    + " - "
                    + cursor.getString(cursor.getColumnIndex(COLUMN_time_to));
            temp.add(time);
            temp.add(cursor.getString(cursor.getColumnIndex(COLUMN_fullname)));

            String no_of_commission_session = cursor.getString(cursor
                    .getColumnIndex(COLUMN_no_of_commission_session));
            total_no_of_commission_session += Double
                    .valueOf(no_of_commission_session);
            temp.add(no_of_commission_session);

            String no_of_paid_session = cursor.getString(cursor
                    .getColumnIndex(COLUMN_no_of_paid_session));
            total_no_of_paid_session += Double.valueOf(no_of_paid_session);
            temp.add(no_of_paid_session);

            String no_of_session = cursor.getString(cursor
                    .getColumnIndex(COLUMN_no_of_session));
            total_no_of_session += Double.valueOf(no_of_session);
            temp.add(no_of_session);

            String amount = cursor.getString(cursor
                    .getColumnIndex(COLUMN_amount));
            total_amount += Double.valueOf(amount);
            temp.add(amount);

            tableReportList.add(temp);
        }
        ArrayList<String> rowTotal = new ArrayList<String>();
        rowTotal.add("TOTAL");
        rowTotal.add(""); // Blank Space for Time
        rowTotal.add(""); // Blank Space for Name
        rowTotal.add(total_no_of_commission_session.toString());
        rowTotal.add(total_no_of_paid_session.toString());
        rowTotal.add(total_no_of_session.toString());
        rowTotal.add(total_amount.toString());
        tableReportList.add(rowTotal);

        cursor.close();
        return tableReportList;
    }

    public ArrayList<TuitionRecord> getReportDetails_FeeDueType(
            Boolean isTuitionNameSet, String TuitionName,
            Boolean isCustomTimeSet, String CustomTimeFrom, String CustomTimeTo) {

        ArrayList<TuitionRecord> listOfTuitionRecord = new ArrayList<TuitionRecord>();

        db = getReadableDatabase();
        String rawDailyUpdateQuery = new String();
        if (isTuitionNameSet) {
            rawDailyUpdateQuery = "Select distinct " + COLUMN_fullname + " from " + TABLE_daily_update_list
                    + " where " + COLUMN_fullname + " ='" + TuitionName + "'";
        } else {
            rawDailyUpdateQuery = "Select distinct " + COLUMN_fullname + " from " + TABLE_daily_update_list;
        }
        Cursor dailyUpdateCursor = db.rawQuery(rawDailyUpdateQuery, null);
        if (dailyUpdateCursor != null)
            dailyUpdateCursor.moveToFirst();
        int count = dailyUpdateCursor.getCount();

        for (int j = 0; j < count; j++, dailyUpdateCursor.moveToNext()) {
            if (isCustomTimeSet) {
                TuitionRecord temp = new TuitionRecord(mContext, dailyUpdateCursor.getString(0), CustomTimeFrom, CustomTimeTo);
                listOfTuitionRecord.add(temp);
            } else {
                TuitionRecord temp = new TuitionRecord(mContext, dailyUpdateCursor.getString(0), null, null);
                listOfTuitionRecord.add(temp);
            }

        }

        dailyUpdateCursor.close();
        db.close();
        return listOfTuitionRecord;
    }

    public ArrayList<ReportBalanceSheet> getReportDetails_BalanceSheetType(
            Boolean isTuitionNameSet, String TuitionName,
            Boolean isTargeAmountSet, Boolean isCustomTimeSet,
            String CustomTimeFrom, String CustomTimeTo) {

        ArrayList<ReportBalanceSheet> listOfBalanceSheet = new ArrayList<ReportBalanceSheet>();

        db = getReadableDatabase();
        String rawDailyUpdateQuery = new String();
        if (isTuitionNameSet) {
            rawDailyUpdateQuery = "Select * from " + TABLE_daily_update_list
                    + " where " + COLUMN_fullname + " ='" + TuitionName + "'";
        } else {
            rawDailyUpdateQuery = "Select * from " + TABLE_daily_update_list;
        }
        Cursor dailyUpdateCursor = db.rawQuery(rawDailyUpdateQuery, null);
        if (dailyUpdateCursor != null)
            dailyUpdateCursor.moveToFirst();
        int count = dailyUpdateCursor.getCount();

        for (int j = 0; j < count; j++, dailyUpdateCursor.moveToNext()) {
            String date = dailyUpdateCursor.getString(dailyUpdateCursor
                    .getColumnIndex(COLUMN_date));
            if (isCustomTimeSet)
                // Date Comparison
                if (!isDateBetween(date, CustomTimeFrom, CustomTimeTo)) {
                    continue;
                }
            String[] dateParts = date.split("/");
            Integer month = Integer.parseInt(dateParts[1]);
            String year = dateParts[2];
            Boolean flagReportPresent = false;
            Integer reportPosition = -1;
            ReportBalanceSheet newYearReport = new ReportBalanceSheet();
            // Finding and retrieving report for particular year, if there
            // exists
            for (int i = 0; i < listOfBalanceSheet.size(); i++) {
                ReportBalanceSheet tempReport = listOfBalanceSheet.get(i);
                if (tempReport.getYear().equals(year)) {
                    newYearReport = tempReport;
                    listOfBalanceSheet.remove(i);
                    reportPosition = i;
                    flagReportPresent = true;
                    break;
                }
            }

            // Adding each record from SQL to Report
            String timeFrom = dailyUpdateCursor.getString(dailyUpdateCursor
                    .getColumnIndex(COLUMN_time_from));
            String timeTo = dailyUpdateCursor.getString(dailyUpdateCursor
                    .getColumnIndex(COLUMN_time_to));
            Integer min = getMinBetweenTime(timeFrom, timeTo);
            Double no_of_hours = min / 60.0;
            Double no_of_session = dailyUpdateCursor
                    .getDouble(dailyUpdateCursor
                            .getColumnIndex(COLUMN_no_of_session));
            Double amount = dailyUpdateCursor.getDouble(dailyUpdateCursor
                    .getColumnIndex(COLUMN_amount));
            Double commission_amount = dailyUpdateCursor
                    .getDouble(dailyUpdateCursor
                            .getColumnIndex(COLUMN_commission_amount));
            String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
            newYearReport.addRow(months[month - 1], no_of_session, no_of_hours,
                    commission_amount, amount);

            // Adding Year Report ArrayList of ReportBalanceSheet
            if (flagReportPresent) {
                newYearReport.setYear(year);
                listOfBalanceSheet.add(reportPosition, newYearReport);
            } else {
                newYearReport.setYear(year);
                Boolean flagInserted = false;
                int iYear = Integer.parseInt(year);
                ReportBalanceSheet tempReport = new ReportBalanceSheet();
                int listCount = listOfBalanceSheet.size();
                for (int k = 0; k < listCount; k++) {
                    tempReport = listOfBalanceSheet.get(k);
                    int iTempYear = Integer.parseInt(tempReport.getYear());
                    if (iYear < iTempYear) {
                        listOfBalanceSheet.add(k, newYearReport);
                        flagInserted = true;
                        break;
                    }
                }
                // Toast.makeText(mContext, String.valueOf(listCount),
                // Toast.LENGTH_SHORT).show();
                if (!flagInserted) {
                    listOfBalanceSheet.add(newYearReport);
                }
            }
        }

        dailyUpdateCursor.close();
        db.close();
        return listOfBalanceSheet;
    }

    public ArrayList<ArrayList<String>> getReportDetails_TuitionType(
            String TuitionName, String DateFrom, String DateTo) {

        db = getReadableDatabase();
        String rawQuery = "Select * from " + TABLE_daily_update_list
                + " where " + COLUMN_fullname + " ='" + TuitionName + "'";
        Log.v("Query", rawQuery);
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        int count = cursor.getCount();
        ArrayList<ArrayList<String>> tableReportList = new ArrayList<ArrayList<String>>();
        ArrayList<String> row = new ArrayList<String>();
        row.add("Date");
        row.add("Time");
        row.add("No.of Commission\r\nSession");
        row.add("No.of\r\nPaid Session");
        row.add("No.of\r\nSession");
        row.add("Amount");

        tableReportList.add(row);
        Double total_no_of_commission_session = 0.0;
        Double total_no_of_paid_session = 0.0;
        Double total_no_of_session = 0.0;
        Double total_amount = 0.0;

        for (int i = 0; i < count; i++, cursor.moveToNext()) {
            ArrayList<String> temp = new ArrayList<String>();
            temp.clear();
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_date));

            // Date Comparison if there exists a From and To Dates
            if (DateFrom != null && DateTo != null)
                if (!isDateBetween(date, DateFrom, DateTo)) {
                    continue;
                }
            temp.add(date);
            String time = cursor.getString(cursor
                    .getColumnIndex(COLUMN_time_from))
                    + " - "
                    + cursor.getString(cursor.getColumnIndex(COLUMN_time_to));
            temp.add(time);

            String no_of_commission_session = cursor.getString(cursor
                    .getColumnIndex(COLUMN_no_of_commission_session));
            total_no_of_commission_session += Double
                    .valueOf(no_of_commission_session);
            temp.add(no_of_commission_session);

            String no_of_paid_session = cursor.getString(cursor
                    .getColumnIndex(COLUMN_no_of_paid_session));
            total_no_of_paid_session += Double.valueOf(no_of_paid_session);
            temp.add(no_of_paid_session);

            String no_of_session = cursor.getString(cursor
                    .getColumnIndex(COLUMN_no_of_session));
            total_no_of_session += Double.valueOf(no_of_session);
            temp.add(no_of_session);

            String amount = cursor.getString(cursor
                    .getColumnIndex(COLUMN_amount));
            total_amount += Double.valueOf(amount);
            temp.add(amount);

            tableReportList.add(temp);
        }
        ArrayList<String> rowTotal = new ArrayList<String>();
        rowTotal.add("TOTAL");
        rowTotal.add(""); // Blank Space for Time
        rowTotal.add(total_no_of_commission_session.toString());
        rowTotal.add(total_no_of_paid_session.toString());
        rowTotal.add(total_no_of_session.toString());
        rowTotal.add(total_amount.toString());
        tableReportList.add(rowTotal);

        cursor.close();
        db.close();
        return tableReportList;
    }

    private boolean isDateBetween(String date, String dateFrom, String dateTo) {
        // TODO Auto-generated method stub
        Boolean return_value = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            Date currentDate = dateFormat.parse(date);
            Date fromDate = dateFormat.parse(dateFrom);
            Date toDate = dateFormat.parse(dateTo);
            if (fromDate.compareTo(currentDate) <= 0
                    && toDate.compareTo(currentDate) >= 0)
                return_value = true;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return return_value;
    }

    public ContentValues getLatestDailyUpdateDetails(String vTuitionName, String DateFrom, String DateTo) {
        ContentValues latestDetail = new ContentValues();
        db = getReadableDatabase();

        String rawQuery = "Select * from " + TABLE_daily_update_list
                + " where " + COLUMN_fullname + " ='" + vTuitionName + "'";
        Log.v("Query", rawQuery);
        Cursor cursor = db.rawQuery(rawQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        int count = cursor.getCount();

        String lastPaymentOn = new String("1/1/1999");
        String lastPaidAmount = new String();
        String nextPaymentOn = new String();
        String DueAmount = new String();
        for (int i = 0; i < count; i++, cursor.moveToNext()) {
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_date));
            Boolean isPaymentReceived = cursor.getString(cursor.getColumnIndex(COLUMN_payment_received)).equals("1");
            //Only Compare date if Payment Received
            if (!isPaymentReceived) {
                continue;
            }
            // Date Comparison if there exists a From and To Dates
            if (DateFrom != null && DateTo != null)
                if (!isDateBetween(date, DateFrom, DateTo)) {
                    continue;
                }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            Date fromDate;
            Integer compareVal = new Integer(-1);
            String[] date1 = lastPaymentOn.split("/");
            String[] date2 = date.split("/");
            Integer date1_day = Integer.valueOf(date1[0]),
                    date1_month = Integer.valueOf(date1[1]),
                    date1_year = Integer.valueOf(date1[2]);
            Integer date2_day = Integer.valueOf(date2[0]),
                    date2_month = Integer.valueOf(date2[1]),
                    date2_year = Integer.valueOf(date2[2]);
            Log.v("Date1 :", date1_day.toString() + "/" + date1_month.toString() + "/" + date1_year.toString() + "/");
            Log.v("Date2 :", date2_day.toString() + "/" + date2_month.toString() + "/" + date2_year.toString() + "/");
            if (date1_year.equals(date2_year) && date1_month.equals(date2_month) && date1_day.equals(date2_day))
                compareVal = 0;
            if (date1_year > date2_year)
                compareVal = 1;
            if (date1_year.equals(date2_year) && date1_month > date2_month)
                compareVal = 1;
            if (date1_year.equals(date2_year) && date1_month.equals(date2_month) && date1_day > date2_day)
                compareVal = 1;

            Log.v("Last Payment On", lastPaymentOn);
            Log.v("Date", date);
            Log.v("Compared Value", String.valueOf(compareVal));

            if (compareVal < 0) {
                lastPaymentOn = date;
                String amount = cursor.getString(cursor
                        .getColumnIndex(COLUMN_amount));
                lastPaidAmount = amount;
            }
        }
        latestDetail.put("LastPaymentOn", lastPaymentOn);
        latestDetail.put("LastPaidAmount", lastPaidAmount);
        latestDetail.put("NextPaymentOn", "NextPaymentOn");
        latestDetail.put("DueAmount", "DueAmount");
        return latestDetail;
    }

    public int getTupleCountOfReportList() {
        return getCount(TABLE_reports_details);
    }

    public int getTupleCountOfTuitionList() {
        return getCount(TABLE_tuition_list);
    }

    public int getTupleCountOfEventList() {
        return getCount(TABLE_events_list);
    }

    public int getTupleCountOfDailyUpdate() {
        return getCount(TABLE_daily_update_list);
    }

    private int getCount(String table_name) {

        String countQuery = "SELECT  * FROM " + table_name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<String> getTodayTuitionList() {
        // TODO Auto-generated method stub
        ArrayList<String> list = new ArrayList<String>();
        db = getReadableDatabase();
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday"};
        Calendar calendar = Calendar.getInstance();
        String week_day = days[calendar.get(Calendar.DAY_OF_WEEK) - 1];

        // String sql_statment = "Select fullname,class_time_id from " +
        // TABLE_tuition_list;
        String sql_statment = "select distinct fullname,week_day from tuition_list,classes_on where tuition_list.class_time_id = classes_on.class_time_id and status='ACTIVE' and week_day='"
                + week_day + "' order by fullname";
        Cursor table_data = db.rawQuery(sql_statment, null);

        if (table_data != null)
            table_data.moveToFirst();
        for (int i = 0; i < table_data.getCount(); i++) {
            list.add(table_data.getString(0));
            table_data.moveToNext();
        }
        table_data.close();
        db.close();
        return list;
    }

    public ArrayList<String> getInactiveTuitionList() {
        // TODO Auto-generated method stub
        ArrayList<String> list = new ArrayList<String>();
        db = getReadableDatabase();
        String sql_statment = "Select fullname from " + TABLE_tuition_list
                + " where status='NOT ACTIVE'";
        Cursor table_data = db.rawQuery(sql_statment, null);
        if (table_data != null)
            table_data.moveToFirst();
        for (int i = 0; i < table_data.getCount(); i++) {
            list.add(table_data.getString(0));
            table_data.moveToNext();
        }
        table_data.close();
        db.close();
        return list;
    }

    public Double getUnpaidSessionNo(String Fullname) {
        // TODO Auto-generated method stub
        String total_session_count_query = "Select sum(no_of_session) from daily_update_list where fullname = '"
                + Fullname + "'";
        String paid_session_count_query = "Select sum(no_of_paid_session) from daily_update_list where fullname = '"
                + Fullname + "'";
        db = getReadableDatabase();
        Cursor total_Cursor = db.rawQuery(total_session_count_query, null);
        Cursor paid_Cursor = db.rawQuery(paid_session_count_query, null);
        Double count = -1.0;
        if (total_Cursor != null && paid_Cursor != null) {
            total_Cursor.moveToFirst();
            paid_Cursor.moveToFirst();
            Double total_count = total_Cursor.getDouble(0);
            Double paid_count = paid_Cursor.getDouble(0);
            count = total_count - paid_count;
        }

        total_Cursor.close();
        paid_Cursor.close();
        db.close();
        return count;
    }

    public void deleteReport(String reportTitle) {
        db = getWritableDatabase();
        db.delete(TABLE_reports_details, COLUMN_report_title + " = ?",
                new String[]{reportTitle});
        db.close();
    }

    public void deleteTuition(String class_time_id) {
        db = getWritableDatabase();
        db.delete(TABLE_tuition_list, COLUMN_class_time_id + " = ?",
                new String[]{class_time_id});
        db.delete(TABLE_classes_on, COLUMN_class_time_id + " = ?",
                new String[]{class_time_id});
        db.close();
    }

}
