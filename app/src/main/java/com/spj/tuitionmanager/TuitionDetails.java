package com.spj.tuitionmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shamanland.fab.FloatingActionButton;

import java.util.ArrayList;


public class TuitionDetails extends Activity {

    private String vTuitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_details);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Tuition Name and Class
        final String tuitionName = getIntent().getExtras().getString("TuitionName");
        if (tuitionName.isEmpty()) {
            Toast.makeText(this, "Error..Invalid Record", Toast.LENGTH_LONG).show();
            finish();
        }
        vTuitionName = tuitionName;
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ContentValues tuitionDetails = dbHelper.getTuitionRecord(this, DatabaseHelper.COLUMN_fullname + "='" + tuitionName + "'");
        TextView txtTuitionName = (TextView) findViewById(R.id.lblTuitionName);
        String txtClass = tuitionDetails.getAsString(DatabaseHelper.COLUMN_class);
        txtTuitionName.setText(tuitionName + "\r\nClass : " + txtClass);
        LinearLayout content = (LinearLayout) findViewById(R.id.layoutTuitionDetailsSection);
        content.removeAllViews();
        //Address
        String address1 = tuitionDetails.getAsString(DatabaseHelper.COLUMN_address1);
        if (!address1.isEmpty()) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText(address1);
            content.addView(textview);
        }
        String address2 = tuitionDetails.getAsString(DatabaseHelper.COLUMN_address2);
        if (!address2.isEmpty()) {
            TextView textview = new TextView(this);
            textview.setText(address2);
            content.addView(textview);
        }
        //Phone
        String phone = tuitionDetails.getAsString(DatabaseHelper.COLUMN_phone);
        if (!phone.isEmpty()) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("Ph: " + phone);
            content.addView(textview);
        }
        //Mail ID
        String email = tuitionDetails.getAsString(DatabaseHelper.COLUMN_email);
        if (!email.isEmpty()) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("Email: " + email);
            content.addView(textview);
        }
        //No of Hours and Amount per Hour
        String no_of_hrs = tuitionDetails.getAsString(DatabaseHelper.COLUMN_no_of_hour);
        if (!no_of_hrs.isEmpty()) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("No of Hours : " + no_of_hrs);
            content.addView(textview);
        }
        String pay_per_hr = tuitionDetails.getAsString(DatabaseHelper.COLUMN_pay_per_hour);
        if (!pay_per_hr.isEmpty()) {
            TextView textview = new TextView(this);
            textview.setText("Pay per Hour: " + pay_per_hr);
            content.addView(textview);
        }
        //Payment Method
        String paymentMethod = tuitionDetails.getAsString(DatabaseHelper.COLUMN_payment_method);
        if (!paymentMethod.isEmpty()) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("Payment : " + paymentMethod);
            content.addView(textview);
        }
        //Status
        String status = tuitionDetails.getAsString(DatabaseHelper.COLUMN_status);
        if (!status.isEmpty()) {
            TextView textview = new TextView(this);
            textview.setText("Status : " + status);
            content.addView(textview);
        }
        //Weekday and subject with time
        String class_id = tuitionDetails.getAsString(DatabaseHelper.COLUMN_class_time_id);
        ArrayList<ArrayList<String>> classes_on = dbHelper.getTuitionClasses(class_id);
        ArrayList<ArrayList<String>> sun = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> mon = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> tue = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> wed = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> thu = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> fri = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> sat = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < classes_on.size(); i++) {
            ArrayList<String> temp_row = classes_on.get(i);
            String week_day = temp_row.get(0);
            if (week_day.equals("Sunday"))
                sun.add(temp_row);
            if (week_day.equals("Monday"))
                mon.add(temp_row);
            if (week_day.equals("Tuesday"))
                tue.add(temp_row);
            if (week_day.equals("Wednesday"))
                wed.add(temp_row);
            if (week_day.equals("Thursday"))
                thu.add(temp_row);
            if (week_day.equals("Friday"))
                fri.add(temp_row);
            if (week_day.equals("Saturday"))
                sat.add(temp_row);
        }
        //For Sunday
        if (sun.size() > 0) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("SUNDAY");
            textview.setTextColor(Color.BLUE);
            content.addView(textview);
        }
        for (int i = 0; i < sun.size(); i++) {
            ArrayList<String> temp_row = sun.get(i);
            String subject = temp_row.get(1);
            String time = temp_row.get(2);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
            linearLayout.setLayoutParams(layoutParams);
            TextView txtSubject = new TextView(this);
            txtSubject.setText(subject);
            TextView txtTime = new TextView(this);
            txtSubject.setLayoutParams(layoutParams);
            txtTime.setLayoutParams(layoutParams);
            txtTime.setText(time);
            linearLayout.addView(txtSubject);
            linearLayout.addView(txtTime);
            content.addView(linearLayout);
        }
        //For Monday
        if (mon.size() > 0) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("MONDAY");
            textview.setTextColor(Color.BLUE);
            content.addView(textview);
        }
        for (int i = 0; i < mon.size(); i++) {
            ArrayList<String> temp_row = mon.get(i);
            String subject = temp_row.get(1);
            String time = temp_row.get(2);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
            linearLayout.setLayoutParams(layoutParams);
            TextView txtSubject = new TextView(this);
            txtSubject.setText(subject);
            TextView txtTime = new TextView(this);
            txtSubject.setLayoutParams(layoutParams);
            txtTime.setLayoutParams(layoutParams);
            txtTime.setText(time);
            linearLayout.addView(txtSubject);
            linearLayout.addView(txtTime);
            content.addView(linearLayout);
        }
        //For Tuesday
        if (tue.size() > 0) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("TUESDAY");
            textview.setTextColor(Color.BLUE);
            content.addView(textview);
        }
        for (int i = 0; i < tue.size(); i++) {
            ArrayList<String> temp_row = tue.get(i);
            String subject = temp_row.get(1);
            String time = temp_row.get(2);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
            linearLayout.setLayoutParams(layoutParams);
            TextView txtSubject = new TextView(this);
            txtSubject.setText(subject);
            TextView txtTime = new TextView(this);
            txtSubject.setLayoutParams(layoutParams);
            txtTime.setLayoutParams(layoutParams);
            txtTime.setText(time);
            linearLayout.addView(txtSubject);
            linearLayout.addView(txtTime);
            content.addView(linearLayout);
        }
        //For Wednesday
        if (wed.size() > 0) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("WEDNESDAY");
            textview.setTextColor(Color.BLUE);
            content.addView(textview);
        }
        for (int i = 0; i < wed.size(); i++) {
            ArrayList<String> temp_row = wed.get(i);
            String subject = temp_row.get(1);
            String time = temp_row.get(2);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
            linearLayout.setLayoutParams(layoutParams);
            TextView txtSubject = new TextView(this);
            txtSubject.setText(subject);
            TextView txtTime = new TextView(this);
            txtSubject.setLayoutParams(layoutParams);
            txtTime.setLayoutParams(layoutParams);
            txtTime.setText(time);
            linearLayout.addView(txtSubject);
            linearLayout.addView(txtTime);
            content.addView(linearLayout);
        }
        //For Thursday
        if (thu.size() > 0) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("THURSDAY");
            textview.setTextColor(Color.BLUE);
            content.addView(textview);
        }
        for (int i = 0; i < thu.size(); i++) {
            ArrayList<String> temp_row = thu.get(i);
            String subject = temp_row.get(1);
            String time = temp_row.get(2);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
            linearLayout.setLayoutParams(layoutParams);
            TextView txtSubject = new TextView(this);
            txtSubject.setText(subject);
            TextView txtTime = new TextView(this);
            txtSubject.setLayoutParams(layoutParams);
            txtTime.setLayoutParams(layoutParams);
            txtTime.setText(time);
            linearLayout.addView(txtSubject);
            linearLayout.addView(txtTime);
            content.addView(linearLayout);
        }
        //For Friday
        if (fri.size() > 0) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("FRIDAY");
            textview.setTextColor(Color.BLUE);
            content.addView(textview);
        }
        for (int i = 0; i < fri.size(); i++) {
            ArrayList<String> temp_row = fri.get(i);
            String subject = temp_row.get(1);
            String time = temp_row.get(2);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
            linearLayout.setLayoutParams(layoutParams);
            TextView txtSubject = new TextView(this);
            txtSubject.setText(subject);
            TextView txtTime = new TextView(this);
            txtSubject.setLayoutParams(layoutParams);
            txtTime.setLayoutParams(layoutParams);
            txtTime.setText(time);
            linearLayout.addView(txtSubject);
            linearLayout.addView(txtTime);
            content.addView(linearLayout);
        }
        //For Saturday
        if (sat.size() > 0) {
            View hr = new View(this);
            hr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
            content.addView(hr);
            TextView textview = new TextView(this);
            textview.setText("SATURDAY");
            textview.setTextColor(Color.BLUE);
            content.addView(textview);
        }
        for (int i = 0; i < sat.size(); i++) {
            ArrayList<String> temp_row = sat.get(i);
            String subject = temp_row.get(1);
            String time = temp_row.get(2);
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, .5f);
            linearLayout.setLayoutParams(layoutParams);
            TextView txtSubject = new TextView(this);
            txtSubject.setText(subject);
            TextView txtTime = new TextView(this);
            txtSubject.setLayoutParams(layoutParams);
            txtTime.setLayoutParams(layoutParams);
            txtTime.setText(time);
            linearLayout.addView(txtSubject);
            linearLayout.addView(txtTime);
            content.addView(linearLayout);
        }
        FloatingActionButton btnEdit = (FloatingActionButton) findViewById(R.id.btnEditTuition);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editTuitionIntent = new Intent(TuitionDetails.this, NewTuitionActivity.class);
                editTuitionIntent.putExtra("tuitionName", tuitionName);
                editTuitionIntent.putExtra("onEdit", true);
                startActivity(editTuitionIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
