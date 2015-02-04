package com.spj.tuitionmanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewEventActivity extends Activity {

	protected ArrayList<String> selected_day = new ArrayList<String>();
	protected ArrayList<String> selected_time = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
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

		CheckBox txtFixedDate = (CheckBox) findViewById(R.id.txtFixedTime);
		final EditText txtTime = (EditText) findViewById(R.id.txtTime);
		txtTime.setFocusable(false);
		txtTime.setCursorVisible(false);
		txtTime.setClickable(true);
		txtTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				// Clearing old values
				selected_day.clear();
				selected_time.clear();
				response_marker();

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
						NewEventActivity.this, new OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker arg0, int arg1,
									int arg2) {
								// TODO Auto-generated method stub
								Integer hr = arg1, min = arg2;

								String am_pm = "am";
								if (hr >= 12) {
									hr = hr - 12;
									am_pm = "pm";
								} else {
									am_pm = "am";
								}
								txtTime.setText(String.format("%02d", hr) + ":"
										+ String.format("%02d", min) + " "
										+ am_pm);
							}
						}, hourOfDay, minute, false);

				tdialog.show();

			}
		});
		txtFixedDate.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub

				// Clearing old values
				selected_day.clear();
				selected_time.clear();
				response_marker();

				if (!isChecked) {
					txtTime.setVisibility(View.GONE);
				} else {
					txtTime.setVisibility(View.VISIBLE);
				}

			}
		});

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
				week_day_click("Sunday");
			}
		});
		btnMonday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_day_click("Monday");
			}
		});

		btnTuesday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_day_click("Tuesday");
			}
		});

		btnWednesday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_day_click("Wednesday");
			}
		});

		btnThursday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_day_click("Thursday");

			}
		});

		btnFriday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_day_click("Friday");

			}
		});

		btnSaturday.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				week_day_click("Saturday");

			}
		});

	}

	protected void week_day_click(String week_day) {
		// TODO Auto-generated method stub
		CheckBox txtFixedTime = (CheckBox) findViewById(R.id.txtFixedTime);
		if (txtFixedTime.isChecked())
			fixed_time_weekday(week_day);
		else
			variable_time_weekday(week_day);

		response_marker();
	}

	private void variable_time_weekday(final String week_day) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(NewEventActivity.this);
		dialog.setTitle("Select Time");

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
		btnDone.setGravity(Gravity.RIGHT);
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
		layoutBtnDoneCancelSet.setGravity(Gravity.CENTER);
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

					Button temp_time = (Button) new_row_linear.getChildAt(0);

					if (temp_time.getText().toString().endsWith("Select Time")) {

						Toast.makeText(getApplicationContext(),
								"Slot Available", Toast.LENGTH_SHORT).show();
						return;
					}

				}

				LinearLayout.LayoutParams lin_param = new LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				lin_param.setMargins(10, 10, 10, 10);

				final LinearLayout lin_sub_time = new LinearLayout(
						getApplicationContext());
				lin_sub_time.setOrientation(LinearLayout.HORIZONTAL);
				lin_sub_time.setLayoutParams(lin_param);

				final ImageView btnClose = new ImageView(NewEventActivity.this);
				btnClose.setImageResource(R.drawable.close);
				btnClose.setPadding(10, 10, 10, 10);

				btnClose.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog_scroll_linear_linear.removeView(lin_sub_time);
					}
				});

				final Button txtTime = new Button(NewEventActivity.this);
				txtTime.setText("Select Time");
				txtTime.setTextColor(Color.BLACK);
				txtTime.setBackgroundResource(R.drawable.button);
				txtTime.setLayoutParams(layoutparam);

				lin_sub_time.addView(txtTime);
				lin_sub_time.addView(btnClose);
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
								NewEventActivity.this, new OnTimeSetListener() {

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
		dialog_scroll_linear.addView(dialog_scroll_linear_button);
		dialog_scroll.addView(dialog_scroll_linear);

		dialog.setContentView(dialog_scroll);

		dialog.show();
		Boolean day_selected = false;
		for (int i = 0; i < selected_day.size(); i++) {
			if (selected_day.get(i) == week_day) {
				day_selected = true;
				addnew.performClick();
				int count = dialog_scroll_linear_linear.getChildCount() - 1;
				LinearLayout new_row_linear = (LinearLayout) dialog_scroll_linear_linear
						.getChildAt(count);

				Button temp_time_button = (Button) new_row_linear.getChildAt(0);
				temp_time_button.setText(selected_time.get(i));

			}
		}
		if (!day_selected)
			addnew.performClick();

		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				// TODO Auto-generated method stub
				response_marker();
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
						if (selected_day.get(j).toString().equals(week_day)) {
							selected_day.remove(j);
							selected_time.remove(j);
						}
					}
				Boolean exit_flag = true;
				for (int i = 0; i < dialog_scroll_linear_linear.getChildCount(); i++) {
					LinearLayout new_row_linear = (LinearLayout) dialog_scroll_linear_linear
							.getChildAt(i);

					Button temp_time_button = (Button) new_row_linear
							.getChildAt(0);

					if (temp_time_button.getText().toString() == "Select Time") {
						Toast.makeText(getApplicationContext(), "Select Time",
								Toast.LENGTH_SHORT).show();
						exit_flag = false;
						break;
					}
					selected_day.add(week_day);
					selected_time.add(temp_time_button.getText().toString());

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

	private void response_marker() {
		// TODO Auto-generated method stub
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
			btnTuesday.setBackgroundResource(R.drawable.circle_selected);
			btnTuesday.setTextColor(Color.WHITE);
		} else {
			btnTuesday.setBackgroundResource(R.drawable.circle);
			btnTuesday.setTextColor(Color.BLACK);
		}
		if (selected_day.contains("Wednesday")) {
			btnWednesday.setBackgroundResource(R.drawable.circle_selected);
			btnWednesday.setTextColor(Color.WHITE);
		} else {
			btnWednesday.setBackgroundResource(R.drawable.circle);
			btnWednesday.setTextColor(Color.BLACK);
		}
		if (selected_day.contains("Thursday")) {
			btnThursday.setBackgroundResource(R.drawable.circle_selected);
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
			btnSaturday.setBackgroundResource(R.drawable.circle_selected);
			btnSaturday.setTextColor(Color.WHITE);
		} else {
			btnSaturday.setBackgroundResource(R.drawable.circle);
			btnSaturday.setTextColor(Color.BLACK);
		}
	}

	private void fixed_time_weekday(String week_day) {
		// TODO Auto-generated method stub
		EditText txtTime = (EditText) findViewById(R.id.txtTime);
		if (txtTime.getText().toString().equals("Select Time")) {
			Toast.makeText(getApplicationContext(), "Select Time",
					Toast.LENGTH_LONG).show();
			return;
		}

		Boolean present = false;
		for (int j = 0; j < selected_day.size(); j++) {
			if (selected_day.get(j) == week_day) {
				selected_day.remove(j);
				selected_time.remove(j);
				present = true;
			}
		}
		if (!present) {
			selected_day.add(week_day);
			selected_time.add(txtTime.getText().toString());
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
				NewEventActivity.this.finish();
			}
			return true;
		case R.id.action_cancel:
			NewEventActivity.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private boolean saveData() {
		// TODO Auto-generated method stub
		DatabaseHelper dbHelper = new DatabaseHelper(this);

		final EditText txtFullName = (EditText) findViewById(R.id.txtFullName);
		final EditText txtAddress1 = (EditText) findViewById(R.id.txtAddress1);
		final EditText txtAddress2 = (EditText) findViewById(R.id.txtAddress2);
		final EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
		final EditText txtPhone = (EditText) findViewById(R.id.txtPhone);
		final EditText txtNo_of_Hours = (EditText) findViewById(R.id.txtNo_of_Hrs);
		final EditText txtPay_per_Hour = (EditText) findViewById(R.id.txtPay_per_Hour);
		final Spinner txtPayment_Method = (Spinner) findViewById(R.id.txtPayment_Method);
		final Button txtStatus = (Button) findViewById(R.id.btnStatus);

		String Fullname = txtFullName.getText().toString();
		String Address1 = txtAddress1.getText().toString();
		String Address2 = txtAddress2.getText().toString();
		String Email = txtEmail.getText().toString();
		String Phone = txtPhone.getText().toString();
		
		Calendar calendar = Calendar.getInstance();
		String Event_time_id = String.valueOf(String.valueOf(calendar.getTimeInMillis()).hashCode());
		
		String No_of_Hours = txtNo_of_Hours.getText().toString();
		String Pay_per_Hour = txtPay_per_Hour.getText().toString();
		String Payment_Method = txtPayment_Method.getSelectedItem().toString();
		String Status = txtStatus.getText().toString();
		Boolean missing_field = false;

		if (Fullname.isEmpty()) {
			txtFullName.setHintTextColor(Color.RED);
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
			Toast.makeText(NewEventActivity.this, "Missing Fields",
					Toast.LENGTH_LONG).show();
			return false;
		}
		Boolean return_type = dbHelper.insertIntoEventList(Fullname, Address1,
				Address2, Email, Phone, Event_time_id, No_of_Hours,
				Pay_per_Hour, Payment_Method, Status, selected_day,
				selected_time);
		return return_type;
	}

}
