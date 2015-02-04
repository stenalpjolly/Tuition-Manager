package com.spj.tuitionmanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class CreateReport extends Activity {

	private String based_on_String = null;
	private ContentValues row_data;
	private String selectedTuitionName = null;

	private String vReportTitle = null;
    private String vReportType = null;
    private String vDateFrom_BasedonTime = null;
    private String vDateTo_BasedonTime = null;
    private String vTuitionName_BasedOnTuition = null;
    private String vDateFrom_BasedonTuition = null;
    private String vDateTo_BasedonTuition = null;
    private String vTuitionName = null;
    private String vTargetAmount = null;
    private String vDateFrom_BasedonFee = null;
    private String vDateTo_BasedonFee = null;
	private boolean vTime_BasedOnFee;
	private boolean vSetTarget;
	private boolean vSpecifyTuition;
	private boolean vTime_BasedOnTuition;

	private ContentValues fullDataRowToStore = new ContentValues();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_report);
		// Show the Up button in the action bar.
		setupActionBar();

		final Spinner txtBased_on = (Spinner) findViewById(R.id.txtBasedOn);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.based_on, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		txtBased_on.setAdapter(adapter);

		OnItemSelectedListener basedOnSelected = new basedOnItemSelectedListener(
				this, adapter);
		txtBased_on.setOnItemSelectedListener(basedOnSelected);

		final Spinner txtTimePeriod_Spinner = (Spinner) findViewById(R.id.txtPeriodType);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> Time_adapter = ArrayAdapter
				.createFromResource(this, R.array.time_periods,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		Time_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		txtTimePeriod_Spinner.setAdapter(Time_adapter);

		OnItemSelectedListener timePeriodOnSelected = new periodTypeOnItemSelectedListener(
				this, Time_adapter);
		txtTimePeriod_Spinner.setOnItemSelectedListener(timePeriodOnSelected);

		CheckBox ckbTime_BasedOnTuition = (CheckBox) findViewById(R.id.txtCustomTimePeriod_BasedOnTuition);
		ckbTime_BasedOnTuition
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						// TODO Auto-generated method stub
						LinearLayout time_layout = (LinearLayout) findViewById(R.id.layoutTime_BasedOnTuition);
						if (arg1) {
							time_layout.setVisibility(View.VISIBLE);
						} else {
							time_layout.setVisibility(View.GONE);
						}
					}
				});
		CheckBox ckbSpecifyTuition = (CheckBox) findViewById(R.id.txtSpecifyTuition);
		ckbSpecifyTuition
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						// TODO Auto-generated method stub
						EditText txtTuitionName = (EditText) findViewById(R.id.txtTuitionName);
						if (arg1) {
							txtTuitionName.setVisibility(View.VISIBLE);
						} else {
							txtTuitionName.setVisibility(View.GONE);
						}
					}
				});
		CheckBox ckbSetTarget = (CheckBox) findViewById(R.id.txtSetTarget);
		ckbSetTarget.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				EditText txtTargetAmount = (EditText) findViewById(R.id.txtTargetAmount);
				if (arg1) {
					txtTargetAmount.setVisibility(View.VISIBLE);
				} else {
					txtTargetAmount.setVisibility(View.GONE);
				}
			}
		});
		CheckBox ckbTime_BasedOnFee = (CheckBox) findViewById(R.id.txtCustomTimePeriod);
		ckbTime_BasedOnFee
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						// TODO Auto-generated method stub
						LinearLayout time_layout = (LinearLayout) findViewById(R.id.layoutTime_BasedOnFee);
						if (arg1) {
							time_layout.setVisibility(View.VISIBLE);
						} else {
							time_layout.setVisibility(View.GONE);
						}
					}
				});

		final EditText txtTuitionName_BasedOnTuition = (EditText) findViewById(R.id.txtTuitionName_BasedOnTuition);
		txtTuitionName_BasedOnTuition
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						select_student(txtTuitionName_BasedOnTuition);
					}
				});

		final EditText txtTuitionName_BasedOnFee = (EditText) findViewById(R.id.txtTuitionName);
		txtTuitionName_BasedOnFee
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						select_student(txtTuitionName_BasedOnFee);
					}
				});

		EditText txtDateFrom_BasedonTime = (EditText) findViewById(R.id.txtDateFrom_BasedOnTime);
		selectDate(txtDateFrom_BasedonTime);
		EditText txtDateTo_BasedonTime = (EditText) findViewById(R.id.txtDateTo_BasedOnTime);
		selectDate(txtDateTo_BasedonTime);
		EditText txtDateFrom_BasedonTuition = (EditText) findViewById(R.id.txtDateFrom_BasedOnTuition);
		selectDate(txtDateFrom_BasedonTuition);
		EditText txtDateTo_BasedonTuition = (EditText) findViewById(R.id.txtDateTo_BasedOnTuition);
		selectDate(txtDateTo_BasedonTuition);
		EditText txtDateFrom_BasedonFee = (EditText) findViewById(R.id.txtDateFrom);
		selectDate(txtDateFrom_BasedonFee);
		EditText txtDateTo_BasedonFee = (EditText) findViewById(R.id.txtDateTo);
		selectDate(txtDateTo_BasedonFee);

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
		case R.id.action_cancel:
			CreateReport.this.finish();
			return true;
		case R.id.action_done:
			if (saveData()) {
				Toast.makeText(getApplicationContext(), "Data Saved",
						Toast.LENGTH_LONG).show();
				CreateReport.this.finish();
			}

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void selectDate(final EditText txtDate) {
		txtDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();
				Integer year = calendar.get(Calendar.YEAR);
				Integer monthOfYear = calendar.get(Calendar.MONTH);
				Integer dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				if (!txtDate.getText().toString().isEmpty()) {
					try {
						String[] temp_date = txtDate.getText().toString()
								.split("/");
						year = Integer.parseInt(temp_date[2]);
						monthOfYear = Integer.parseInt(temp_date[1]) - 1;
						dayOfMonth = Integer.parseInt(temp_date[0]);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				DatePickerDialog dateDialog = new DatePickerDialog(
						CreateReport.this, new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker arg0, int arg1,
									int arg2, int arg3) {
								// TODO Auto-generated method stub
								arg2++;
								txtDate.setText(arg3 + "/" + arg2 + "/" + arg1);
							}
						}, year, monthOfYear, dayOfMonth);
				dateDialog.show();
			}
		});
	}

	private void selectDate(final TextView txtDate) {
		txtDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();
				Integer year = calendar.get(Calendar.YEAR);
				Integer monthOfYear = calendar.get(Calendar.MONTH);
				Integer dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
				if (!txtDate.getText().toString().isEmpty()) {
					try {
						String[] temp_date = txtDate.getText().toString()
								.split("/");
						year = Integer.parseInt(temp_date[2]);
						monthOfYear = Integer.parseInt(temp_date[1]) - 1;
						dayOfMonth = Integer.parseInt(temp_date[0]);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				DatePickerDialog dateDialog = new DatePickerDialog(
						CreateReport.this, new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker arg0, int arg1,
									int arg2, int arg3) {
								// TODO Auto-generated method stub
								arg2++;
								txtDate.setText(arg3 + "/" + arg2 + "/" + arg1);
							}
						}, year, monthOfYear, dayOfMonth);
				dateDialog.show();
			}
		});
	}

	private void selectYear(final TextView txtDate) {
		txtDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Dialog yearDialog = new Dialog(CreateReport.this);
				yearDialog.setTitle("Select Year");
				Calendar calendar = Calendar.getInstance();
				final NumberPicker npYear = new NumberPicker(CreateReport.this);
				LinearLayout linearLayout = new LinearLayout(CreateReport.this);
				linearLayout.setOrientation(LinearLayout.VERTICAL);
				Button btnDone = new Button(CreateReport.this);
				btnDone.setText("Done");
				btnDone.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						yearDialog.dismiss();
					}
				});
				npYear.setMaxValue(2050);
				npYear.setMinValue(1999);
				npYear.setValue(calendar.get(Calendar.YEAR));
				npYear.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
				linearLayout.addView(npYear);
				linearLayout.addView(btnDone);
				yearDialog.setContentView(linearLayout);
				yearDialog.show();
				yearDialog.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface arg0) {
						// TODO Auto-generated method stub
						txtDate.setText(String.valueOf(npYear.getValue()));
					}
				});
			}
		});
	}

	private void selectMonth(final TextView txtDate) {
		txtDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final Dialog yearDialog = new Dialog(CreateReport.this);
				yearDialog.setTitle("Select Month/Year");
				Calendar calendar = Calendar.getInstance();
				final NumberPicker npYear = new NumberPicker(CreateReport.this);
				final NumberPicker npMonth = new NumberPicker(CreateReport.this);
				final String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
						"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
				npMonth.setMaxValue(months.length-1);
				npMonth.setMinValue(0);
				npMonth.setDisplayedValues(months);
				npYear.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
				npMonth.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
				LinearLayout vLinearLayout = new LinearLayout(CreateReport.this);
				vLinearLayout.setOrientation(LinearLayout.VERTICAL);
				LinearLayout hLinearLayout = new LinearLayout(CreateReport.this);
				hLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
			
				final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT, 1);
				npMonth.setLayoutParams(layoutParams);
				npYear.setLayoutParams(layoutParams);
				hLinearLayout.setLayoutParams(layoutParams);

				Button btnDone = new Button(CreateReport.this);
				btnDone.setText("Done");
				btnDone.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						yearDialog.dismiss();
					}
				});
				npYear.setMaxValue(2050);
				npYear.setMinValue(1999);
				npYear.setValue(calendar.get(Calendar.YEAR));
				hLinearLayout.addView(npMonth);
				hLinearLayout.addView(npYear);
				vLinearLayout.addView(hLinearLayout);
				vLinearLayout.addView(btnDone);
				yearDialog.setContentView(vLinearLayout);
				yearDialog.show();
				yearDialog.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface arg0) {
						// TODO Auto-generated method stub
						String text = months[npMonth.getValue()] +" " + String.valueOf(npYear.getValue());
						txtDate.setText(text);
					}
				});
			}
		});
	}

	private boolean saveData() {
		// TODO Auto-generated method stub
		Boolean return_value = false;
		if (isFieldVaild()) {
			DatabaseHelper dbHelper = new DatabaseHelper(CreateReport.this);
			fullDataRowToStore.clear();
			fullDataRowToStore.put(DatabaseHelper.COLUMN_report_title,
					vReportTitle);
			fullDataRowToStore.put(DatabaseHelper.COLUMN_based_on, vReportType);

			if (vReportType.equals("Time Period")) {
				
				fullDataRowToStore.put(DatabaseHelper.COLUMN_date_from,
						vDateFrom_BasedonTime);
				fullDataRowToStore.put(DatabaseHelper.COLUMN_date_to,
						vDateTo_BasedonTime);
			}
			if (vReportType.equals("Tuition")) {
				fullDataRowToStore.put(DatabaseHelper.COLUMN_tuition_name,
						vTuitionName_BasedOnTuition);
				fullDataRowToStore.put(DatabaseHelper.COLUMN_custom_time_check,
						vTime_BasedOnTuition);
				fullDataRowToStore.put(DatabaseHelper.COLUMN_date_from,
						vDateFrom_BasedonTuition);
				fullDataRowToStore.put(DatabaseHelper.COLUMN_date_to,
						vDateTo_BasedonTuition);
			}
			if (vReportType.equals("Fee Due")
					|| vReportType.equals("Balance Sheet")) {
				fullDataRowToStore.put(DatabaseHelper.COLUMN_custom_time_check,
						vTime_BasedOnFee);
				fullDataRowToStore.put(DatabaseHelper.COLUMN_date_from,
						vDateFrom_BasedonFee);
				fullDataRowToStore.put(DatabaseHelper.COLUMN_date_to,
						vDateTo_BasedonFee);
				fullDataRowToStore.put(
						DatabaseHelper.COLUMN_specify_tuition_check,
						vSpecifyTuition);
				fullDataRowToStore.put(DatabaseHelper.COLUMN_tuition_name,
						vTuitionName);
				fullDataRowToStore.put(
						DatabaseHelper.COLUMN_set_target_amount_check,
						vSetTarget);
				fullDataRowToStore.put(DatabaseHelper.COLUMN_target_amount,
						vTargetAmount);
			}
			dbHelper.insertIntoReports(CreateReport.this, fullDataRowToStore);
			return_value = true;
		} else
			Toast.makeText(CreateReport.this, "Missing Fields",
					Toast.LENGTH_LONG).show();
		return return_value;
	}

	private boolean isFieldVaild() {
		// TODO Auto-generated method stub
		Boolean return_value = true;
		EditText txtReportTitle = (EditText) findViewById(R.id.txtReportTitle);
		vReportTitle = txtReportTitle.getText().toString();
		Spinner txtReportType = (Spinner) findViewById(R.id.txtBasedOn);
		vReportType = txtReportType.getSelectedItem().toString();

		// Based on Time Section
		Spinner txtTimePeriodType = (Spinner) findViewById(R.id.txtPeriodType);
        String vTimePeriodType = txtTimePeriodType.getSelectedItem().toString();
		TextView txtYearMonthDay = (TextView) findViewById(R.id.txtYearMonth);
        String vYearMonthDay = txtYearMonthDay.getText().toString();

		EditText txtDateFrom_BasedonTime = (EditText) findViewById(R.id.txtDateFrom_BasedOnTime);
		vDateFrom_BasedonTime = txtDateFrom_BasedonTime.getText().toString();
		EditText txtDateTo_BasedonTime = (EditText) findViewById(R.id.txtDateTo_BasedOnTime);
		vDateTo_BasedonTime = txtDateTo_BasedonTime.getText().toString();

		// Based on Tuition Section
		EditText txtTuitionName_BasedOnTuition = (EditText) findViewById(R.id.txtTuitionName_BasedOnTuition);
		vTuitionName_BasedOnTuition = txtTuitionName_BasedOnTuition.getText()
				.toString();
		CheckBox ckbTime_BasedOnTuition = (CheckBox) findViewById(R.id.txtCustomTimePeriod_BasedOnTuition);
		vTime_BasedOnTuition = ckbTime_BasedOnTuition.isChecked();
		EditText txtDateFrom_BasedonTuition = (EditText) findViewById(R.id.txtDateFrom_BasedOnTuition);
		vDateFrom_BasedonTuition = txtDateFrom_BasedonTuition.getText()
				.toString();
		EditText txtDateTo_BasedonTuition = (EditText) findViewById(R.id.txtDateTo_BasedOnTuition);
		vDateTo_BasedonTuition = txtDateTo_BasedonTuition.getText().toString();

		// Based on Fee/Balance Sheet
		CheckBox ckbSpecifyTuition = (CheckBox) findViewById(R.id.txtSpecifyTuition);
		vSpecifyTuition = ckbSpecifyTuition.isChecked();
		EditText txtTuitionName = (EditText) findViewById(R.id.txtTuitionName);
		vTuitionName = txtTuitionName.getText().toString();
		CheckBox ckbSetTarget = (CheckBox) findViewById(R.id.txtSetTarget);
		vSetTarget = ckbSetTarget.isChecked();
		EditText txtTargetAmount = (EditText) findViewById(R.id.txtTargetAmount);
		vTargetAmount = txtTargetAmount.getText().toString();
		CheckBox ckbTime_BasedOnFee = (CheckBox) findViewById(R.id.txtCustomTimePeriod);
		vTime_BasedOnFee = ckbTime_BasedOnFee.isChecked();
		EditText txtDateFrom_BasedonFee = (EditText) findViewById(R.id.txtDateFrom);
		vDateFrom_BasedonFee = txtDateFrom_BasedonFee.getText().toString();
		EditText txtDateTo_BasedonFee = (EditText) findViewById(R.id.txtDateTo);
		vDateTo_BasedonFee = txtDateTo_BasedonFee.getText().toString();

		if (vReportTitle.isEmpty()) {
			return_value = false;
			txtReportTitle.setHintTextColor(Color.RED);
		}
		if (vReportType.equals("Time Period")) {

			if (vTimePeriodType.equals("Custom")) {
				if (vDateFrom_BasedonTime.isEmpty()
						|| vDateTo_BasedonTime.isEmpty()) {
					return_value = false;
					txtDateFrom_BasedonTime.setHintTextColor(Color.RED);
					txtDateTo_BasedonTime.setHintTextColor(Color.RED);
				}

			} else {
				if (vYearMonthDay.isEmpty()) {
					return_value = false;
					txtYearMonthDay.setHintTextColor(Color.RED);
				}else{
					if(vTimePeriodType.equals("Year")){
						vDateFrom_BasedonTime = "1/1/" + vYearMonthDay;
						vDateTo_BasedonTime = "31/12/" + vYearMonthDay;
					}
					if(vTimePeriodType.equals("Month")){
						String[] vMonthYear = vYearMonthDay.split(" ");
						String vMonth = vMonthYear[0];
						String vYear = vMonthYear[1];
						String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
								"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
						Integer month = null;
						for(int i=0;i<months.length;i++)
							if(months[i].equals(vMonth)){
								month = ++i;
								break;
							}
						vDateFrom_BasedonTime = "1/"+String.valueOf(month)+"/" + vYear;
						vDateTo_BasedonTime = "31/"+String.valueOf(month)+"/" + vYear;
					}
					if(vTimePeriodType.equals("Day")){
						vDateFrom_BasedonTime = vYearMonthDay;
						vDateTo_BasedonTime = vYearMonthDay;
					}
					
				}
			}
		}

		if (vReportType.equals("Tuition")) {
			if (vTuitionName_BasedOnTuition.isEmpty()) {
				return_value = false;
				txtTuitionName_BasedOnTuition.setHintTextColor(Color.RED);
			}
			if (vTime_BasedOnTuition) {
				if (vDateFrom_BasedonTuition.isEmpty()
						|| vDateTo_BasedonTuition.isEmpty()) {
					return_value = false;
					txtDateFrom_BasedonTuition.setHintTextColor(Color.RED);
					txtDateTo_BasedonTuition.setHintTextColor(Color.RED);
				}
			}
		}

		if (vReportType.equals("Fee Due")
				|| vReportType.equals("Balance Sheet")) {
			if (vSpecifyTuition)
				if (vTuitionName.isEmpty()) {
					return_value = false;
					txtTuitionName.setHintTextColor(Color.RED);
				}
			if (vSetTarget)
				if (vTargetAmount.isEmpty()) {
					return_value = false;
					txtTargetAmount.setHintTextColor(Color.RED);
				}
			if (vTime_BasedOnFee)
				if (vDateFrom_BasedonFee.isEmpty()
						|| vDateTo_BasedonFee.isEmpty()) {
					return_value = false;
					txtDateFrom_BasedonFee.setHintTextColor(Color.RED);
					txtDateTo_BasedonFee.setHintTextColor(Color.RED);
				}
		}

		return return_value;
	}

	public class basedOnItemSelectedListener implements OnItemSelectedListener {

		ArrayAdapter<CharSequence> mLocalAdapter;
		Activity mLocalContext;
		String selectedItem;

		public basedOnItemSelectedListener(Activity c,
				ArrayAdapter<CharSequence> ad) {

			this.mLocalContext = c;
			this.mLocalAdapter = ad;

		}

		/**
		 * When the user selects an item in the spinner, this method is invoked
		 * by the callback chain. Android calls the item selected listener for
		 * the spinner, which invokes the onItemSelected method.
		 * 
		 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView,
		 *      android.view.View, int, long)
		 * @param parent
		 *            - the AdapterView for this listener
		 * @param v
		 *            - the View for this listener
		 * @param pos
		 *            - the 0-based position of the selection in the
		 *            mLocalAdapter
		 * @param row
		 *            - the 0-based row number of the selection in the View
		 */
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long row) {

			selectedItem = parent.getItemAtPosition(pos).toString();
			// Toast.makeText(mLocalContext, selectedItem,
			// Toast.LENGTH_SHORT).show();
			if (selectedItem.equals("Time Period"))
				By_Time();
			if (selectedItem.equals("Tuition"))
				By_Tuition();
			if (selectedItem.equals("Fee Due"))
				By_FeeDue();
			if (selectedItem.equals("Balance Sheet"))
				By_BalanceSheet();

		}

		public void onNothingSelected(AdapterView<?> parent) {

			// do nothing

		}
	}

	public void By_Time() {
		// TODO Auto-generated method stub
		LinearLayout linearlayout_byTime = (LinearLayout) findViewById(R.id.layoutBasedOnTime);
		LinearLayout linearlayout_byTuition = (LinearLayout) findViewById(R.id.layoutBasedOnTuition);
		LinearLayout linearlayout_byFee = (LinearLayout) findViewById(R.id.layoutBasedOnFee);

		linearlayout_byTuition.setVisibility(View.GONE);
		linearlayout_byFee.setVisibility(View.GONE);
		linearlayout_byTime.setVisibility(View.VISIBLE);
	}

	public void By_BalanceSheet() {
		// TODO Auto-generated method stub
		LinearLayout linearlayout_byTime = (LinearLayout) findViewById(R.id.layoutBasedOnTime);
		LinearLayout linearlayout_byTuition = (LinearLayout) findViewById(R.id.layoutBasedOnTuition);
		LinearLayout linearlayout_byFee = (LinearLayout) findViewById(R.id.layoutBasedOnFee);

		linearlayout_byTuition.setVisibility(View.GONE);
		linearlayout_byFee.setVisibility(View.VISIBLE);
		linearlayout_byTime.setVisibility(View.GONE);

		TextView lblBalanceSheet = (TextView) findViewById(R.id.lblBalanceSheet);
		TextView lblFeeDue = (TextView) findViewById(R.id.lblBasedOnFeeDue);

		lblBalanceSheet.setVisibility(View.VISIBLE);
		lblFeeDue.setVisibility(View.GONE);

	}

	public void By_FeeDue() {
		// TODO Auto-generated method stub
		LinearLayout linearlayout_byTime = (LinearLayout) findViewById(R.id.layoutBasedOnTime);
		LinearLayout linearlayout_byTuition = (LinearLayout) findViewById(R.id.layoutBasedOnTuition);
		LinearLayout linearlayout_byFee = (LinearLayout) findViewById(R.id.layoutBasedOnFee);

		linearlayout_byTuition.setVisibility(View.GONE);
		linearlayout_byFee.setVisibility(View.VISIBLE);
		linearlayout_byTime.setVisibility(View.GONE);
		TextView lblBalanceSheet = (TextView) findViewById(R.id.lblBalanceSheet);
		TextView lblFeeDue = (TextView) findViewById(R.id.lblBasedOnFeeDue);

		lblBalanceSheet.setVisibility(View.GONE);
		lblFeeDue.setVisibility(View.VISIBLE);
	}

	public void By_Tuition() {
		// TODO Auto-generated method stub
		LinearLayout linearlayout_byTime = (LinearLayout) findViewById(R.id.layoutBasedOnTime);
		LinearLayout linearlayout_byTuition = (LinearLayout) findViewById(R.id.layoutBasedOnTuition);
		LinearLayout linearlayout_byFee = (LinearLayout) findViewById(R.id.layoutBasedOnFee);

		linearlayout_byTime.setVisibility(View.GONE);
		linearlayout_byFee.setVisibility(View.GONE);
		linearlayout_byTuition.setVisibility(View.VISIBLE);

	}

	protected void select_student(final EditText arg) {
		// TODO Auto-generated method stub
		final Dialog student_list = new Dialog(CreateReport.this);

		student_list.setTitle("Select Tution");
		student_list.setCancelable(false);
		View list_scroll = LayoutInflater.from(CreateReport.this).inflate(
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
				student_list.dismiss();

			}
		});

		final DatabaseHelper dbHelper = new DatabaseHelper(CreateReport.this);

		ArrayList<String> today_list = dbHelper.getTodayTuitionList();
		ArrayList<String> active_list = dbHelper.getActiveTuitionList();
		ArrayList<String> inactive_list = dbHelper.getInactiveTuitionList();

		// For Today Section
		for (int i = 0; i < today_list.size(); i++) {
			final TextView txtItem = new TextView(CreateReport.this);
			txtItem.setTextSize(19);
			txtItem.setGravity(Gravity.LEFT);
			txtItem.setPadding(10, 10, 10, 10);
			txtItem.setBackgroundResource(R.drawable.item);
			txtItem.setText(today_list.get(i).toUpperCase());

			list_today.addView(txtItem);

			txtItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					String where = "UPPER(fullname) = '"
							+ txtItem.getText().toString() + "'";
					row_data = dbHelper.getTuitionRecord(CreateReport.this,
							where);
					selectedTuitionName = row_data.get("fullname").toString();
					student_list.dismiss();

				}
			});
		}
		if (list_today.getChildCount() <= 0) {
			TextView txtlist_today = (TextView) list_scroll
					.findViewById(R.id.txtlist_today);
			View line = list_scroll.findViewById(R.id.viewlist_today);
			line.setVisibility(View.GONE);
			txtlist_today.setVisibility(View.GONE);
		}

		// For Frequent Section

		if (list_frequently.getChildCount() <= 0) {
			TextView txtlist_frequent = (TextView) list_scroll
					.findViewById(R.id.txtlist_frequent);
			View line = list_scroll.findViewById(R.id.viewlist_frequent);
			line.setVisibility(View.GONE);
			txtlist_frequent.setVisibility(View.GONE);
		}

		// For Active Section
		for (int i = 0; i < active_list.size(); i++) {
			final TextView txtItem = new TextView(CreateReport.this);
			txtItem.setTextSize(19);
			txtItem.setGravity(Gravity.LEFT);
			txtItem.setPadding(10, 10, 10, 10);
			txtItem.setBackgroundResource(R.drawable.item);
			txtItem.setText(active_list.get(i).toUpperCase());

			list_all_active.addView(txtItem);

			txtItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					String where = "UPPER(fullname) = '"
							+ txtItem.getText().toString() + "'";
					row_data = dbHelper.getTuitionRecord(CreateReport.this,
							where);
					selectedTuitionName = row_data.get("fullname").toString();
					student_list.dismiss();

				}
			});
		}
		if (list_all_active.getChildCount() <= 0) {
			TextView txtlist_active = (TextView) list_scroll
					.findViewById(R.id.txtlist_active);
			View line = list_scroll.findViewById(R.id.viewlist_active);
			line.setVisibility(View.GONE);
			txtlist_active.setVisibility(View.GONE);
		}

		// For Inactive Section
		for (int i = 0; i < inactive_list.size(); i++) {
			final TextView txtItem = new TextView(CreateReport.this);
			txtItem.setTextSize(19);
			txtItem.setGravity(Gravity.LEFT);
			txtItem.setPadding(10, 10, 10, 10);
			txtItem.setBackgroundResource(R.drawable.item);
			txtItem.setText(inactive_list.get(i).toUpperCase());

			list_all_inactive.addView(txtItem);

			txtItem.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					String where = "UPPER(fullname) = '"
							+ txtItem.getText().toString() + "'";
					row_data = dbHelper.getTuitionRecord(CreateReport.this,
							where);

					selectedTuitionName = row_data.get("fullname").toString();
					student_list.dismiss();

				}
			});
		}
		if (list_all_inactive.getChildCount() <= 0) {
			TextView txtlist_inactive = (TextView) list_scroll
					.findViewById(R.id.txtlist_inactive);
			View line = list_scroll.findViewById(R.id.viewlist_inactive);
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
				// EditText txtTuitionName_BasedOnTuition =
				// (EditText)findViewById(R.id.txtTuitionName_BasedOnTuition);
				// txtTuitionName_BasedOnTuition.setText(TuitionName);
				arg.setText(selectedTuitionName);
			}
		});

	}

	public class periodTypeOnItemSelectedListener implements
			OnItemSelectedListener {

		ArrayAdapter<CharSequence> mLocalAdapter;
		Activity mLocalContext;
		String selectedItem;

		public periodTypeOnItemSelectedListener(Activity c,
				ArrayAdapter<CharSequence> ad) {

			this.mLocalContext = c;
			this.mLocalAdapter = ad;

		}

		/**
		 * When the user selects an item in the spinner, this method is invoked
		 * by the callback chain. Android calls the item selected listener for
		 * the spinner, which invokes the onItemSelected method.
		 * 
		 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView,
		 *      android.view.View, int, long)
		 * @param parent
		 *            - the AdapterView for this listener
		 * @param v
		 *            - the View for this listener
		 * @param pos
		 *            - the 0-based position of the selection in the
		 *            mLocalAdapter
		 * @param row
		 *            - the 0-based row number of the selection in the View
		 */
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long row) {

			selectedItem = parent.getItemAtPosition(pos).toString();
			// Toast.makeText(mLocalContext, selectedItem,
			// Toast.LENGTH_SHORT).show();
			LinearLayout layout_Custom = (LinearLayout) findViewById(R.id.layoutTime_BasedOnTime);
			TextView lblYearMonthDay = (TextView) findViewById(R.id.txtYearMonth);
			lblYearMonthDay.setText(null);
			if (selectedItem.equals("Custom")) {
				lblYearMonthDay.setVisibility(View.GONE);
				layout_Custom.setVisibility(View.VISIBLE);
			} else {
				lblYearMonthDay.setVisibility(View.VISIBLE);
				layout_Custom.setVisibility(View.GONE);
				if (selectedItem.equals("Day"))
					selectDate(lblYearMonthDay);
				if (selectedItem.equals("Month"))
					selectMonth(lblYearMonthDay);
				if (selectedItem.equals("Year"))
					selectYear(lblYearMonthDay);
				lblYearMonthDay.performClick();
			}
		}

		public void onNothingSelected(AdapterView<?> parent) {

			// do nothing

		}
	}

}
