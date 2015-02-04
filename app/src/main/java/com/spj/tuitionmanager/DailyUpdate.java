package com.spj.tuitionmanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class DailyUpdate extends Activity {

	private static ContentValues row_data = new ContentValues();
	private Double vCommission_amount = 0.0;
	TimePickerDialog tdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_update);
		// Show the Up button in the action bar.
		setupActionBar();

		select_student();

		final EditText txtDaily_Fullname = (EditText) findViewById(R.id.txtDaily_FullName);
		txtDaily_Fullname.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				select_student();

			}
		});

		final EditText txtSessionNo = (EditText) findViewById(R.id.txtSessionNo);
		txtSessionNo.setSelection(txtSessionNo.getText().length());

		final CheckBox ckrPayCommission = (CheckBox) findViewById(R.id.txtPayCommission);
		final EditText txtCommissionSessionNo = (EditText) findViewById(R.id.txtCommissionSessionNo);

		ckrPayCommission
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean ischecked) {
						// TODO Auto-generated method stub
						if (ischecked) {
							txtCommissionSessionNo.setVisibility(View.VISIBLE);
						} else
							txtCommissionSessionNo.setVisibility(View.GONE);

					}
				});

		final CheckBox ckrPaymentReceived = (CheckBox) findViewById(R.id.txtPaymentReceived);
		final EditText txtPaymentSessionNo = (EditText) findViewById(R.id.txtPaymentSessionNo);

		txtPaymentSessionNo.setOnKeyListener(new View.OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				calculate_amount();
				return false;
			}
		});

		ckrPaymentReceived
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean ischecked) {
						// TODO Auto-generated method stub
						if (ischecked) {
							txtPaymentSessionNo.setVisibility(View.VISIBLE);
							DatabaseHelper dbHelper = new DatabaseHelper(
									DailyUpdate.this);
							Double No_of_Unpaid_session = dbHelper
									.getUnpaidSessionNo(txtDaily_Fullname
											.getText().toString());
							No_of_Unpaid_session += Double
									.parseDouble(txtSessionNo.getText()
											.toString());
							txtPaymentSessionNo.setText(No_of_Unpaid_session
									.toString());
						} else
							txtPaymentSessionNo.setVisibility(View.GONE);

						calculate_amount();

					}
				});

		final EditText txtDate = (EditText) findViewById(R.id.txtTuitionName);
		txtDate.setCursorVisible(false);
		final EditText txtTimeFrom = (EditText) findViewById(R.id.txtTimeFrom);
		txtTimeFrom.setCursorVisible(false);
		final EditText txtTimeTo = (EditText) findViewById(R.id.txtTimeTo);
		txtTimeTo.setCursorVisible(false);
		Calendar calender = Calendar.getInstance();
		Integer year = calender.get(Calendar.YEAR);
		Integer monthOfYear = calender.get(Calendar.MONTH) + 1;
		Integer dayOfMonth = calender.get(Calendar.DAY_OF_MONTH);
		txtDate.setText(dayOfMonth.toString() + "/" + monthOfYear + "/" + year);
		txtDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar calender = Calendar.getInstance();
				Integer year = calender.get(Calendar.YEAR);
				Integer monthOfYear = calender.get(Calendar.MONTH);
				Integer dayOfMonth = calender.get(Calendar.DAY_OF_MONTH);
				try {
					String[] temp_date = txtDate.getText().toString()
							.split("/");
					year = Integer.parseInt(temp_date[2]);
					monthOfYear = Integer.parseInt(temp_date[1]) - 1;
					dayOfMonth = Integer.parseInt(temp_date[0]);
				} catch (Exception e) {
					// TODO: handle exception
				}
				DatePickerDialog date_dialog = new DatePickerDialog(
						DailyUpdate.this, new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker arg0, int arg1,
									int arg2, int arg3) {
								// TODO Auto-generated method stub
								arg2++;
								txtDate.setText(arg3 + "/" + arg2 + "/" + arg1);
							}
						}, year, monthOfYear, dayOfMonth);

				date_dialog.show();
			}
		});

		txtTimeFrom.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int hourOfDay = 16, minute = 0;

				if (!txtTimeFrom.getText().toString().equals("From")
						&& !txtTimeFrom.getText().toString().isEmpty()) {
					String[] temp_time1 = txtTimeFrom.getText().toString()
							.split(":");
					String[] temp_time2 = temp_time1[1].split(" ");
					hourOfDay = Integer.parseInt(temp_time1[0]);
					if (temp_time2[1].equals("pm"))
						hourOfDay = hourOfDay + 12;
					minute = Integer.parseInt(temp_time2[0]);
				}

				tdialog = new TimePickerDialog(DailyUpdate.this,
						new OnTimeSetListener() {

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
								txtTimeFrom.setText(String.format("%02d", hr)
										+ ":" + String.format("%02d", min)
										+ " " + am_pm);

								Integer to_hr = hr + 2;

								if (to_hr >= 12) {
									to_hr = to_hr - 12;
									if (am_pm.equals("am"))
										am_pm = "pm";
									else
										am_pm = "am";
								}
								txtTimeTo.setText(String.format("%02d", to_hr)
										+ ":" + String.format("%02d", min)
										+ " " + am_pm);

							}
						}, hourOfDay, minute, false);
				tdialog.show();
			}

		});

		txtTimeTo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int hourOfDay = 16, minute = 0;

				if (!txtTimeTo.getText().toString().equals("To")
						&& !txtTimeTo.getText().toString().isEmpty()) {
					String[] temp_time1 = txtTimeTo.getText().toString()
							.split(":");
					String[] temp_time2 = temp_time1[1].split(" ");
					hourOfDay = Integer.parseInt(temp_time1[0]);
					if (temp_time2[1].equals("pm"))
						hourOfDay = hourOfDay + 12;
					minute = Integer.parseInt(temp_time2[0]);
				}

				TimePickerDialog tdialog = new TimePickerDialog(
						DailyUpdate.this, new OnTimeSetListener() {

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
								txtTimeTo.setText(String.format("%02d", hr)
										+ ":" + String.format("%02d", min)
										+ " " + am_pm);
							}
						}, hourOfDay, minute, false);
				tdialog.show();
			}
		});

	}

	protected void calculate_amount() {
		// TODO Auto-generated method stub
		Double amount = 0.0,commission_amount=0.0;
		CheckBox ckrPaymentReceived = (CheckBox) findViewById(R.id.txtPaymentReceived);
		EditText txtPaymentSessionNo = (EditText) findViewById(R.id.txtPaymentSessionNo);
		TextView txtAmount = (TextView) findViewById(R.id.lblAmount);
		Double rate = Double
				.parseDouble(row_data.getAsString("no_of_hour"))
				* Double.parseDouble(row_data.getAsString("pay_per_hour"));
		if (ckrPaymentReceived.isChecked()) {	
			String session_no = txtPaymentSessionNo.getText().toString();
			if (!session_no.isEmpty()) {
				amount = Double.parseDouble(session_no) * rate;
			} else
				amount = 0.0;
		} else
			amount = 0.0;
		
		final CheckBox chkPayCommission = (CheckBox) findViewById(R.id.txtPayCommission);
		final EditText txtCommissionSessionNo = (EditText) findViewById(R.id.txtCommissionSessionNo);
		if (chkPayCommission.isChecked()) {	
			String commision_session_no = txtCommissionSessionNo.getText().toString();
			if (!commision_session_no.isEmpty()) {
				commission_amount = Double.parseDouble(commision_session_no) * rate;
			} else
				commission_amount = 0.0;
		} else
			commission_amount = 0.0;
		vCommission_amount = commission_amount;
		txtAmount.setText(amount.toString());
	}

	protected void select_student() {
		// TODO Auto-generated method stub
		final Dialog student_list = new Dialog(DailyUpdate.this);

		student_list.setTitle("Select Tuition");
		student_list.setCancelable(false);
		View list_scroll = LayoutInflater.from(DailyUpdate.this).inflate(
				R.layout.student_list, null);

		LinearLayout list_today = (LinearLayout) list_scroll
				.findViewById(R.id.list_today);
		LinearLayout list_frequently = (LinearLayout) list_scroll
				.findViewById(R.id.list_frquently_used);
		LinearLayout list_all_active = (LinearLayout) list_scroll
				.findViewById(R.id.list_all_active);
		LinearLayout list_all_inactive = (LinearLayout) list_scroll
				.findViewById(R.id.list_all_inactive);

		Button btnClose = (Button) list_scroll.findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// student_list.dismiss();
				DailyUpdate.this.finish();

			}
		});

		final DatabaseHelper dbHelper = new DatabaseHelper(DailyUpdate.this);

		ArrayList<String> today_list = dbHelper.getTodayTuitionList();

		ArrayList<String> frequent_list = dbHelper.getActiveTuitionList();

		ArrayList<String> active_list = dbHelper.getActiveTuitionList();
		ArrayList<String> inactive_list = dbHelper.getInactiveTuitionList();

		// For Today Section
		for (int i = 0; i < today_list.size(); i++) {
			final TextView txtItem = new TextView(DailyUpdate.this);
			txtItem.setTextSize(19);
			txtItem.setGravity(Gravity.START);
			txtItem.setPadding(10, 10, 10, 10);
			txtItem.setBackgroundResource(R.drawable.item);
			txtItem.setText(today_list.get(i).toString().toUpperCase());

			list_today.addView(txtItem);

			txtItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					EditText txtFullName = (EditText) findViewById(R.id.txtDaily_FullName);
					String where = "UPPER(fullname) = '"
							+ txtItem.getText().toString() + "'";
					row_data = dbHelper.getTuitionRecord(DailyUpdate.this,
							where);
					txtFullName.setText(row_data.get("fullname").toString());
					student_list.dismiss();

				}
			});
		}
		if (list_today.getChildCount() <= 0) {
			TextView txtlist_today = (TextView) list_scroll
					.findViewById(R.id.txtlist_today);
			View line = (View) list_scroll.findViewById(R.id.viewlist_today);
			line.setVisibility(View.GONE);
			txtlist_today.setVisibility(View.GONE);
		}

		// For Frequent Section

		if (list_frequently.getChildCount() <= 0) {
			TextView txtlist_frequent = (TextView) list_scroll
					.findViewById(R.id.txtlist_frequent);
			View line = (View) list_scroll.findViewById(R.id.viewlist_frequent);
			line.setVisibility(View.GONE);
			txtlist_frequent.setVisibility(View.GONE);
		}

		// For Active Section
		for (int i = 0; i < active_list.size(); i++) {
			final TextView txtItem = new TextView(DailyUpdate.this);
			txtItem.setTextSize(19);
			txtItem.setGravity(Gravity.START);
			txtItem.setPadding(10, 10, 10, 10);
			txtItem.setBackgroundResource(R.drawable.item);
			txtItem.setText(active_list.get(i).toString().toUpperCase());

			list_all_active.addView(txtItem);

			txtItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					EditText txtFullName = (EditText) findViewById(R.id.txtDaily_FullName);
					String where = "UPPER(fullname) = '"
							+ txtItem.getText().toString() + "'";
					row_data = dbHelper.getTuitionRecord(DailyUpdate.this,
							where);
					txtFullName.setText(row_data.get("fullname").toString());
					student_list.dismiss();

				}
			});
		}
		if (list_all_active.getChildCount() <= 0) {
			TextView txtlist_active = (TextView) list_scroll
					.findViewById(R.id.txtlist_active);
			View line = (View) list_scroll.findViewById(R.id.viewlist_active);
			line.setVisibility(View.GONE);
			txtlist_active.setVisibility(View.GONE);
		}

		// For Inactive Section
		for (int i = 0; i < inactive_list.size(); i++) {
			final TextView txtItem = new TextView(DailyUpdate.this);
			txtItem.setTextSize(19);
			txtItem.setGravity(Gravity.START);
			txtItem.setPadding(10, 10, 10, 10);
			txtItem.setBackgroundResource(R.drawable.item);
			txtItem.setText(inactive_list.get(i).toString().toUpperCase());

			list_all_inactive.addView(txtItem);

			txtItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					EditText txtFullName = (EditText) findViewById(R.id.txtDaily_FullName);
					String where = "UPPER(fullname) = '"
							+ txtItem.getText().toString() + "'";
					row_data = dbHelper.getTuitionRecord(DailyUpdate.this,
							where);
					txtFullName.setText(row_data.get("fullname").toString());
					student_list.dismiss();

				}
			});
		}
		if (list_all_inactive.getChildCount() <= 0) {
			TextView txtlist_inactive = (TextView) list_scroll
					.findViewById(R.id.txtlist_inactive);
			View line = (View) list_scroll.findViewById(R.id.viewlist_inactive);
			line.setVisibility(View.GONE);
			txtlist_inactive.setVisibility(View.GONE);
		}

		// list_frequently.addView(txtItem);
		// list_all_active.addView(txtItem);
		// list_all_inactive.addView(txtItem);

		student_list.setContentView(list_scroll);
		student_list.show();

		student_list.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface arg0) {
				// TODO Auto-generated method stub
				predictTime();
			}
		});
	}

	private void predictTime() {
		DatabaseHelper dbHelper = new DatabaseHelper(this);
		EditText txtTimeFrom = (EditText) this.findViewById(R.id.txtTimeFrom);
		txtTimeFrom.setText(dbHelper.getNearTuitionTime(this,
				row_data.getAsString("class_time_id")));
		Integer hourOfDay = 0, minute = 0;
		if (!txtTimeFrom.getText().toString().equals("From")
				&& !txtTimeFrom.getText().toString().isEmpty()) {
			String[] temp_time1 = txtTimeFrom.getText().toString().split(":");
			String[] temp_time2 = temp_time1[1].split(" ");
			hourOfDay = Integer.parseInt(temp_time1[0]);
			if (temp_time2[1].equals("pm"))
				hourOfDay = hourOfDay + 12;
			minute = Integer.parseInt(temp_time2[0]);
		}
		Double no_of_hour = Double.valueOf(row_data.getAsString("no_of_hour"));
		Integer to_hr = hourOfDay + no_of_hour.intValue();
		minute = minute
				+ Double.valueOf(60 * (no_of_hour - no_of_hour.intValue()))
						.intValue();
		if (minute >= 60) {
			to_hr++;
			minute = minute - 60;
		}
		String am_pm = "am";
		if (to_hr >= 12) {
			to_hr = to_hr - 12;
			if (am_pm.equals("am"))
				am_pm = "pm";
			else
				am_pm = "am";
		}
		EditText txtTimeTo = (EditText) this.findViewById(R.id.txtTimeTo);
		txtTimeTo.setText(String.format("%02d", to_hr) + ":"
				+ String.format("%02d", minute) + " " + am_pm);
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
				DailyUpdate.this.finish();
			}
			return true;
		case R.id.action_cancel:
			DailyUpdate.this.finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private boolean saveData() {
		// TODO Auto-generated method stub
		DatabaseHelper dbHelper = new DatabaseHelper(this);

		if (!isValid()) {
			Toast.makeText(this, "Missing Fields..", Toast.LENGTH_LONG).show();
			return false;
		}
		final EditText txtFullName = (EditText) findViewById(R.id.txtDaily_FullName);
		final EditText txtDate = (EditText) findViewById(R.id.txtTuitionName);
		final EditText txtTimeFrom = (EditText) findViewById(R.id.txtTimeFrom);
		final EditText txtTimeTo = (EditText) findViewById(R.id.txtTimeTo);
		final CheckBox chkPayCommission = (CheckBox) findViewById(R.id.txtPayCommission);
		final EditText txtCommissionSessionNo = (EditText) findViewById(R.id.txtCommissionSessionNo);
		final CheckBox chkPaymentRecevied = (CheckBox) findViewById(R.id.txtPaymentReceived);
		final EditText txtPaymentSessionNo = (EditText) findViewById(R.id.txtPaymentSessionNo);
		final EditText txtSessionNo = (EditText) findViewById(R.id.txtSessionNo);

		calculate_amount();

		final TextView txtAmount = (TextView) findViewById(R.id.lblAmount);

		String Fullname = txtFullName.getText().toString();
		String Date = txtDate.getText().toString();
		String TimeFrom = txtTimeFrom.getText().toString();
		String TimeTo = txtTimeTo.getText().toString();
		String PayCommission, PaymentRecevied;
		Double CommissionSessionNo, PaymentSessionNo;

		if (chkPayCommission.isChecked()) {
			PayCommission = "1";
			CommissionSessionNo = Double.parseDouble(txtCommissionSessionNo
					.getText().toString());
		} else {
			PayCommission = "0";
			CommissionSessionNo = 0.0;
		}
		if (chkPaymentRecevied.isChecked()) {
			PaymentRecevied = "1";
			PaymentSessionNo = Double.parseDouble(txtPaymentSessionNo.getText()
					.toString());
		} else {
			PaymentRecevied = "0";
			PaymentSessionNo = 0.0;
		}

		Double SessionNo = Double
				.parseDouble(txtSessionNo.getText().toString());
		Double Amount = Double.parseDouble(txtAmount.getText().toString());

		Boolean return_type = dbHelper.insertIntoDailyEvents(DailyUpdate.this,
				Fullname, Date, TimeFrom, TimeTo, PayCommission,
				CommissionSessionNo, PaymentRecevied, PaymentSessionNo,
				SessionNo, Amount,vCommission_amount);

		return return_type;
	}

	private boolean isValid() {
		// TODO Auto-generated method stub
		final EditText txtFullName = (EditText) findViewById(R.id.txtDaily_FullName);
		final EditText txtDate = (EditText) findViewById(R.id.txtTuitionName);
		final EditText txtTimeFrom = (EditText) findViewById(R.id.txtTimeFrom);
		final EditText txtTimeTo = (EditText) findViewById(R.id.txtTimeTo);
		final CheckBox chkPayCommission = (CheckBox) findViewById(R.id.txtPayCommission);
		final EditText txtCommissionSessionNo = (EditText) findViewById(R.id.txtCommissionSessionNo);
		final CheckBox chkPaymentRecevied = (CheckBox) findViewById(R.id.txtPaymentReceived);
		final EditText txtPaymentSessionNo = (EditText) findViewById(R.id.txtPaymentSessionNo);
		final EditText txtSessionNo = (EditText) findViewById(R.id.txtSessionNo);
		final TextView txtAmount = (TextView) findViewById(R.id.lblAmount);
		
		Boolean return_type = true;
		
		String Fullname = txtFullName.getText().toString();
		if (Fullname.isEmpty()) {
			return_type = false;
			txtFullName.setHintTextColor(Color.RED);
		}
		String Date = txtDate.getText().toString();
		if (Date.isEmpty()) {
			return_type = false;
			txtDate.setHintTextColor(Color.RED);
		}
		String TimeFrom = txtTimeFrom.getText().toString();
		if (TimeFrom.isEmpty()) {
			return_type = false;
			txtTimeFrom.setHintTextColor(Color.RED);
		}
		String TimeTo = txtTimeTo.getText().toString();
		if (TimeTo.isEmpty()) {
			return_type = false;
			txtTimeTo.setHintTextColor(Color.RED);
		}
		if(chkPayCommission.isChecked()){
			String CommissionSessionNo = txtCommissionSessionNo.getText()
					.toString();
			if(CommissionSessionNo.isEmpty()){
				return_type = false;
				txtCommissionSessionNo.setHintTextColor(Color.RED);
			}
		}
		if(chkPaymentRecevied.isChecked()){
			String PaymentSessionNo = txtPaymentSessionNo.getText()
					.toString();
			if(PaymentSessionNo.isEmpty()){
				return_type = false;
				txtPaymentSessionNo.setHintTextColor(Color.RED);
			}
		}
		String SessionNo =txtSessionNo.getText().toString();
		if (SessionNo.isEmpty()) {
			return_type = false;
			txtSessionNo.setHintTextColor(Color.RED);
		}
		String Amount = txtAmount.getText().toString();
		if (Amount.isEmpty()) {
			return_type = false;
			txtAmount.setHintTextColor(Color.RED);
		}
		return return_type;
	}
}
