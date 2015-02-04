package com.spj.tuitionmanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.renderscript.Double4;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class NewTuitionActivity extends Activity {

	public ArrayList<String> selected_day = new ArrayList<String>();
	public ArrayList<String> selected_subject = new ArrayList<String>();
	public ArrayList<String> selected_time = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_tuition);

		Spinner payment_methods = (Spinner) findViewById(R.id.txtPayment_Method);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.payment_methods,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		payment_methods.setAdapter(adapter);

		final Button btnSunday = (Button) findViewById(R.id.btnSun);
		final Button btnMonday = (Button) findViewById(R.id.btnMon);
		final Button btnTuesday = (Button) findViewById(R.id.btnTue);
		final Button btnWednesday = (Button) findViewById(R.id.btnWed);
		final Button btnThursday = (Button) findViewById(R.id.btnThu);
		final Button btnFriday = (Button) findViewById(R.id.btnFri);
		final Button btnSaturday = (Button) findViewById(R.id.btnSat);

		btnSunday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				show_sub_time_dialog("Sunday");

			}

		});

		btnMonday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				show_sub_time_dialog("Monday");
			}
		});

		btnTuesday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				show_sub_time_dialog("Tuesday");
			}
		});

		btnWednesday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				show_sub_time_dialog("Wednesday");
			}
		});

		btnThursday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				show_sub_time_dialog("Thursday");

			}
		});

		btnFriday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				show_sub_time_dialog("Friday");

			}
		});

		btnSaturday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				show_sub_time_dialog("Saturday");

			}
		});

        Boolean flagOnEdit = false;
        try {
            flagOnEdit = getIntent().getExtras().getBoolean("onEdit");
        } catch (NullPointerException e) {
            Log.v("Error", "Null Pointer in Flag");
        } catch (Exception e) {
            Log.v("Error", "Unknown Error");
        }

        if (flagOnEdit) {
            String tuitionName = getIntent().getExtras().getString("tuitionName");
            loadValues(tuitionName);
        }
    }

    private void loadValues(String tuitionName) {
        Toast.makeText(this,tuitionName,Toast.LENGTH_LONG).show();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ContentValues tuitionDetails = dbHelper.getTuitionRecord(this, DatabaseHelper.COLUMN_fullname + "='" + tuitionName + "'");
        String class_tuition = tuitionDetails.getAsString(DatabaseHelper.COLUMN_class);
        String address1 = tuitionDetails.getAsString(DatabaseHelper.COLUMN_address1);
        String address2 = tuitionDetails.getAsString(DatabaseHelper.COLUMN_address2);
        String phone = tuitionDetails.getAsString(DatabaseHelper.COLUMN_phone);
        String email = tuitionDetails.getAsString(DatabaseHelper.COLUMN_email);
        String no_of_hrs = tuitionDetails.getAsString(DatabaseHelper.COLUMN_no_of_hour);
        String pay_per_hr = tuitionDetails.getAsString(DatabaseHelper.COLUMN_pay_per_hour);
        String paymentMethod = tuitionDetails.getAsString(DatabaseHelper.COLUMN_payment_method);
        String status = tuitionDetails.getAsString(DatabaseHelper.COLUMN_status);

        final EditText txtFullName = (EditText) findViewById(R.id.txtFullName);
        txtFullName.setText(tuitionName);
        txtFullName.setEnabled(false);
        final EditText txtClass = (EditText) findViewById(R.id.txtClass);
        txtClass.setText(class_tuition);
        final EditText txtAddress1 = (EditText) findViewById(R.id.txtAddress1);
        txtAddress1.setText(address1);
        final EditText txtAddress2 = (EditText) findViewById(R.id.txtAddress2);
        txtAddress2.setText(address2);
        final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtEmail.setText(email);
        final EditText txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtPhone.setText(phone);
        final EditText txtNo_of_Hours = (EditText) findViewById(R.id.txtNo_of_Hrs);
        txtNo_of_Hours.setText(no_of_hrs);
        final EditText txtPay_per_Hour = (EditText) findViewById(R.id.txtPay_per_Hour);
        txtPay_per_Hour.setText(pay_per_hr);
        final CheckBox txtEnableCommission = (CheckBox) findViewById(R.id.txtEnableCommission);
        Boolean enableCommission = tuitionDetails.getAsString(DatabaseHelper.COLUMN_enable_commission).equals("1");
        txtEnableCommission.setChecked(enableCommission);
        final Spinner txtPayment_Method = (Spinner) findViewById(R.id.txtPayment_Method);
        for (int i = 0; i < txtPayment_Method.getCount(); i++) {
            if (txtPayment_Method.getItemAtPosition(i).toString().equals(paymentMethod)) {
                txtPayment_Method.setSelection(i);
                break;
            }
        }
        final Button txtStatus = (Button) findViewById(R.id.btnStatus);
        if (!status.equals("ACTIVE"))
            txtStatus.performClick();

        String class_id = tuitionDetails.getAsString(DatabaseHelper.COLUMN_class_time_id);
        ArrayList<ArrayList<String>> classes_on = dbHelper.getTuitionClasses(class_id);

        for (int i = 0; i < classes_on.size(); i++) {
            ArrayList<String> temp_row = classes_on.get(i);
            selected_day.add(temp_row.get(0));
            selected_subject.add(temp_row.get(1));
            selected_time.add(temp_row.get(2));
        }
        markDay();

    }

    public void show_sub_time_dialog(final String week_day) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(NewTuitionActivity.this);
		dialog.setTitle("Select Subject and Time");

		final ScrollView dialog_scroll = new ScrollView(getApplicationContext());
		final LinearLayout dialog_scroll_linear = new LinearLayout(
				getApplicationContext());
		dialog_scroll_linear.setOrientation(LinearLayout.VERTICAL);

		final LinearLayout dialog_scroll_linear_button = new LinearLayout(
				getApplicationContext());
		dialog_scroll_linear_button.setOrientation(LinearLayout.HORIZONTAL);

		final LinearLayout dialog_scroll_linear_linear = new LinearLayout(
				getApplicationContext());
		dialog_scroll_linear_linear.setOrientation(LinearLayout.VERTICAL);

		final LinearLayout.LayoutParams layoutparam = new LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		
		final LinearLayout.LayoutParams params = new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(5, 5, 5, 5);
		
		final Button btnDone = new Button(getApplicationContext());
		btnDone.setText("DONE");
		btnDone.setBackgroundResource(R.drawable.button);
		btnDone.setTextColor(Color.rgb(20, 58, 58));
		btnDone.setTypeface(null, Typeface.BOLD);
		btnDone.setTextSize(19);
		btnDone.setGravity(Gravity.CENTER);
		btnDone.setLayoutParams(params);

		final Button btnCancel = new Button(getApplicationContext());
		btnCancel.setText("CANCEL");
		btnCancel.setBackgroundResource(R.drawable.button);
		btnCancel.setTextColor(Color.rgb(20, 58, 58));
		btnCancel.setTypeface(null, Typeface.BOLD);
		btnCancel.setTextSize(19);
		btnCancel.setGravity(Gravity.CENTER);
		btnCancel.setLayoutParams(params);

		final LinearLayout layoutBtnDoneCancelSet = new LinearLayout(
				getApplicationContext());
		layoutBtnDoneCancelSet.setOrientation(LinearLayout.HORIZONTAL);
		layoutBtnDoneCancelSet.setLayoutParams(layoutparam);
		layoutBtnDoneCancelSet.setGravity(Gravity.RIGHT);
		layoutBtnDoneCancelSet.setPadding(5, 5, 5, 5);
		layoutBtnDoneCancelSet.addView(btnCancel);
		layoutBtnDoneCancelSet.addView(btnDone);

		final TextView addnew = new TextView(getApplicationContext());

		final LinearLayout.LayoutParams textviewparam = new LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		textviewparam.setMargins(10, 10, 10, 10);

		addnew.setPadding(10, 10, 10, 10);
		addnew.setTextSize(18);
		addnew.setTextColor(Color.BLACK);
		addnew.setLayoutParams(textviewparam);
		addnew.setText("Add New");

		addnew.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < dialog_scroll_linear_linear.getChildCount(); i++) {
					LinearLayout new_row_linear = (LinearLayout) dialog_scroll_linear_linear
							.getChildAt(i);

					EditText temp_subject = (EditText) new_row_linear
							.getChildAt(0);

					if (temp_subject.getText().toString().isEmpty()) {

						Toast.makeText(getApplicationContext(),
								"Slot Available", Toast.LENGTH_SHORT).show();
						return;
					}

				}

				LinearLayout.LayoutParams lin_param = new LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				lin_param.setMargins(10, 10, 10, 10);

				LinearLayout lin_sub_time = new LinearLayout(
						getApplicationContext());
				lin_sub_time.setOrientation(LinearLayout.HORIZONTAL);
				lin_sub_time.setLayoutParams(lin_param);

				final EditText txtSubject = new EditText(
						NewTuitionActivity.this);
				txtSubject.setTextColor(Color.BLACK);
				txtSubject.setHint("Subject");
				txtSubject.setHintTextColor(Color.GRAY);
				txtSubject.setLayoutParams(layoutparam);
				txtSubject.requestFocus();

				final Button txtTime = new Button(NewTuitionActivity.this);
				txtTime.setText("Select Time");
				txtTime.setTextColor(Color.BLACK);
				txtTime.setBackgroundResource(R.drawable.button);
				txtTime.setLayoutParams(layoutparam);

				lin_sub_time.addView(txtSubject);
				lin_sub_time.addView(txtTime);
				dialog_scroll_linear_linear.addView(lin_sub_time);
				dialog.setContentView(dialog_scroll);

				txtTime.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						int hourOfDay = 16, minute = 0;

						if (!txtTime.getText().toString().equals("Select Time")) {
							String[] temp_time1 = txtTime.getText().toString()
									.split(":");
							String[] temp_time2 = temp_time1[1].split(" ");
							hourOfDay = Integer.parseInt(temp_time1[0]);
							// Toast.makeText(getApplicationContext(),temp_time1[1],
							// Toast.LENGTH_SHORT).show();
							if (temp_time2[1].equals("pm"))
								hourOfDay = hourOfDay + 12;
							minute = Integer.parseInt(temp_time2[0]);
						}

						TimePickerDialog tdialog = new TimePickerDialog(
								NewTuitionActivity.this,
								new OnTimeSetListener() {

									@Override
									public void onTimeSet(TimePicker arg0,
											int arg1, int arg2) {
										// TODO Auto-generated method stub
										Integer hr = arg1, min = arg2;

										String am_pm = "am";
										if (hr >= 12) {
											hr = hr - 12;
											am_pm = "pm";
										} else {
											am_pm = "am";
										}
										txtTime.setText(String.format("%02d",
												hr)
												+ ":"
												+ String.format("%02d", min)
												+ " " + am_pm);
									}
								}, hourOfDay, minute, false);
						tdialog.show();
					}
				});

			}
		});

		dialog_scroll_linear.addView(dialog_scroll_linear_linear);
		dialog_scroll_linear.addView(addnew);

		dialog_scroll_linear_button.addView(layoutBtnDoneCancelSet);
//		dialog_scroll_linear_button.addView(btnDone);

		dialog_scroll_linear.addView(dialog_scroll_linear_button);
		dialog_scroll.addView(dialog_scroll_linear);

		dialog.setContentView(dialog_scroll);

		dialog.show();
		Boolean day_selected = false;
		for (int i = 0; i < selected_day.size(); i++) {
            Log.v("Selected_day",selected_day.get(i) + selected_subject.get(i) +selected_time.get(i));
            if (selected_day.get(i).equals(week_day)) {
                day_selected = true;
                addnew.performClick();
                int count = dialog_scroll_linear_linear.getChildCount() - 1;
                LinearLayout new_row_linear = (LinearLayout) dialog_scroll_linear_linear
                        .getChildAt(count);

                EditText temp_subject = (EditText) new_row_linear.getChildAt(0);
                Button temp_time_button = (Button) new_row_linear.getChildAt(1);
                temp_subject.setText(selected_subject.get(i));
                temp_time_button.setText(selected_time.get(i));

            }
        }
        if (!day_selected)
			addnew.performClick();

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				// TODO Auto-generated method stub
                markDay();
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});

		btnDone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				while (selected_day.contains(week_day))
					for (int j = 0; j < selected_day.size(); j++) {
						if (selected_day.get(j).equals(week_day)) {
							selected_day.remove(j);
							selected_subject.remove(j);
							selected_time.remove(j);
						}
					}
				Boolean exit_flag = true;
				for (int i = 0; i < dialog_scroll_linear_linear.getChildCount(); i++) {
					LinearLayout new_row_linear = (LinearLayout) dialog_scroll_linear_linear
							.getChildAt(i);

					EditText temp_subject = (EditText) new_row_linear
							.getChildAt(0);
					Button temp_time_button = (Button) new_row_linear
							.getChildAt(1);
					Boolean flag = true;

					if (!temp_subject.getText().toString().isEmpty() && flag) {

						if (temp_time_button.getText().toString() == "Select Time") {
							Toast.makeText(getApplicationContext(),
									"Select Time", Toast.LENGTH_SHORT).show();
							flag = false;
							exit_flag = false;
							break;
						}
						selected_day.add(week_day);
						selected_subject.add(temp_subject.getText().toString());
						selected_time
								.add(temp_time_button.getText().toString());
					}

				}
				// for (int j = 0; j < selected_day.size(); j++) {
				// String temp;
				// temp = selected_day.get(j) + ":" + selected_subject.get(j)
				// + ":" + selected_time.get(j);
				// Toast.makeText(getApplicationContext(), temp,
				// Toast.LENGTH_SHORT).show();
				// }

				if (exit_flag)
					dialog.dismiss();
			}
		});
	}

    public void markDay() {
        final Button btnSunday = (Button) findViewById(R.id.btnSun);
        final Button btnMonday = (Button) findViewById(R.id.btnMon);
        final Button btnTuesday = (Button) findViewById(R.id.btnTue);
        final Button btnWednesday = (Button) findViewById(R.id.btnWed);
        final Button btnThursday = (Button) findViewById(R.id.btnThu);
        final Button btnFriday = (Button) findViewById(R.id.btnFri);
        final Button btnSaturday = (Button) findViewById(R.id.btnSat);

        if (selected_day.contains("Sunday")) {
            btnSunday.setBackgroundResource(R.drawable.circle_selected);
            btnSunday.setTextColor(Color.WHITE);
        } else {
            btnSunday.setBackgroundResource(R.drawable.circle);
            btnSunday.setTextColor(Color.BLACK);
        }
        if (selected_day.contains("Monday")) {
            btnMonday.setBackgroundResource(R.drawable.circle_selected);
            btnMonday.setTextColor(Color.WHITE);
        } else {
            btnMonday.setBackgroundResource(R.drawable.circle);
            btnMonday.setTextColor(Color.BLACK);
        }
        if (selected_day.contains("Tuesday")) {
            btnTuesday
                    .setBackgroundResource(R.drawable.circle_selected);
            btnTuesday.setTextColor(Color.WHITE);
        } else {
            btnTuesday.setBackgroundResource(R.drawable.circle);
            btnTuesday.setTextColor(Color.BLACK);
        }
        if (selected_day.contains("Wednesday")) {
            btnWednesday
                    .setBackgroundResource(R.drawable.circle_selected);
            btnWednesday.setTextColor(Color.WHITE);
        } else {
            btnWednesday.setBackgroundResource(R.drawable.circle);
            btnWednesday.setTextColor(Color.BLACK);
        }
        if (selected_day.contains("Thursday")) {
            btnThursday
                    .setBackgroundResource(R.drawable.circle_selected);
            btnThursday.setTextColor(Color.WHITE);
        } else {
            btnThursday.setBackgroundResource(R.drawable.circle);
            btnThursday.setTextColor(Color.BLACK);
        }
        if (selected_day.contains("Friday")) {
            btnFriday.setBackgroundResource(R.drawable.circle_selected);
            btnFriday.setTextColor(Color.WHITE);
        } else {
            btnFriday.setBackgroundResource(R.drawable.circle);
            btnFriday.setTextColor(Color.BLACK);
        }
        if (selected_day.contains("Saturday")) {
            btnSaturday
                    .setBackgroundResource(R.drawable.circle_selected);
            btnSaturday.setTextColor(Color.WHITE);
        } else {
            btnSaturday.setBackgroundResource(R.drawable.circle);
            btnSaturday.setTextColor(Color.BLACK);
        }

    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_event, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_done:
			if (saveData()) {
				Toast.makeText(getApplicationContext(), "Data Saved",
						Toast.LENGTH_LONG).show();
				NewTuitionActivity.this.finish();
			}

			return true;
		case R.id.action_cancel:
			NewTuitionActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private Boolean saveData() {
		// TODO Auto-generated method stub
		DatabaseHelper dbHelper = new DatabaseHelper(this);

        Boolean flagOnEdit = false;
        try {
            flagOnEdit = getIntent().getExtras().getBoolean("onEdit");
        } catch (NullPointerException e) {
            Log.v("Error", "Null Pointer in Flag");
        } catch (Exception e) {
            Log.v("Error", "Unknown Error");
        }

        if (flagOnEdit) {
            String tuitionName = getIntent().getExtras().getString("tuitionName");
            ContentValues tuitionDetails = dbHelper.getTuitionRecord(this, DatabaseHelper.COLUMN_fullname + "='" + tuitionName + "'");
            String class_time_id = tuitionDetails.getAsString(DatabaseHelper.COLUMN_class_time_id);
            dbHelper.deleteTuition(class_time_id);
        }

		final EditText txtFullName = (EditText) findViewById(R.id.txtFullName);
		final EditText txtClass = (EditText) findViewById(R.id.txtClass);
		final EditText txtAddress1 = (EditText) findViewById(R.id.txtAddress1);
		final EditText txtAddress2 = (EditText) findViewById(R.id.txtAddress2);
		final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
		final EditText txtPhone = (EditText) findViewById(R.id.txtPhone);
		final EditText txtNo_of_Hours = (EditText) findViewById(R.id.txtNo_of_Hrs);
		final EditText txtPay_per_Hour = (EditText) findViewById(R.id.txtPay_per_Hour);
		final CheckBox txtEnableCommission = (CheckBox) findViewById(R.id.txtEnableCommission);
		final Spinner txtPayment_Method = (Spinner) findViewById(R.id.txtPayment_Method);
		final Button txtStatus = (Button) findViewById(R.id.btnStatus);

		String Fullname = txtFullName.getText().toString();
		String Classname = txtClass.getText().toString();
		String Address1 = txtAddress1.getText().toString();
		String Address2 = txtAddress2.getText().toString();
		String Email = txtEmail.getText().toString();
		String Phone = txtPhone.getText().toString();

		Calendar calendar = Calendar.getInstance();
		String Class_time_id = String.valueOf(String.valueOf(
				calendar.getTimeInMillis()).hashCode());

		String No_of_Hours = txtNo_of_Hours.getText().toString();
		String Pay_per_Hour = txtPay_per_Hour.getText().toString();
		Boolean isEnableCommission = txtEnableCommission.isChecked();
		String Payment_Method = txtPayment_Method.getSelectedItem().toString();
		String Status = txtStatus.getText().toString();

		Boolean missing_field = false;

		if (Fullname.isEmpty()) {
			txtFullName.setHintTextColor(Color.RED);
			missing_field = true;
		}
		if (Classname.isEmpty()) {
			txtClass.setHintTextColor(Color.RED);
			missing_field = true;
		}
		if (No_of_Hours.isEmpty()) {
			txtNo_of_Hours.setHintTextColor(Color.RED);
			missing_field = true;
		}
		if (Phone.isEmpty()) {
			txtPhone.setHintTextColor(Color.RED);
			missing_field = true;
		}
		if (Pay_per_Hour.isEmpty()) {
			txtPay_per_Hour.setHintTextColor(Color.RED);
			missing_field = true;
		}

		if (missing_field) {
			Toast.makeText(NewTuitionActivity.this, "Missing Fields",
					Toast.LENGTH_LONG).show();
			return false;
		}
		Boolean return_type = dbHelper.insertIntoTuitionList(
				NewTuitionActivity.this, Fullname, Classname, Address1,
				Address2, Email, Phone, Class_time_id, No_of_Hours,
				Pay_per_Hour, isEnableCommission, Payment_Method, Status,
				selected_day, selected_subject, selected_time);

		return return_type;

	}

}
