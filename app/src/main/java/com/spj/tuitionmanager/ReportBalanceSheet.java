package com.spj.tuitionmanager;

import java.util.ArrayList;

public class ReportBalanceSheet {

	private String vYear;
	private ArrayList<ArrayList<String>> vTablePerYear = new ArrayList<ArrayList<String>>();

	public ReportBalanceSheet() {
		// TODO Auto-generated constructor stub
		vYear = null;
	}

	public static ArrayList<String> getHeader() {
		ArrayList<String> headerColumns = new ArrayList<String>();
		headerColumns.clear();
		headerColumns.add(" Month ");
		headerColumns.add("No.of Session\r\n Covered");
		headerColumns.add("No.of Hours\r\n Spend");
		headerColumns.add("Total\r\n Commission Paid");
		headerColumns.add("Total Earned");
		headerColumns.add("Net Amount");
		return headerColumns;
	}

	public ReportBalanceSheet(String Year) {
		vYear = Year;
	}

	public void addRow(String dateMonthString, Double no_of_Session,
			Double no_of_hrs, Double commissionAmount, Double amount) {

		ArrayList<String> tableRow = new ArrayList<String>();
		ArrayList<String> tempRow = new ArrayList<String>();
		Boolean flagMonthPresent = false;
		Integer monthPosition = -1;
		// Finding and retrieving row for particular month, if there exists
		for (int i = 0; i < vTablePerYear.size(); i++) {
			tempRow = vTablePerYear.get(i);
			if (tempRow.get(0).equals(dateMonthString)) {
				tableRow = tempRow;
				vTablePerYear.remove(i);
				monthPosition = i;
				flagMonthPresent = true;
				break;
			}
		}
		if (flagMonthPresent) {
			// String month = tableRow.get(0);
			Double SessionNo = Double.parseDouble(tableRow.get(1));
			Double HoursNo = Double.parseDouble(tableRow.get(2));
			Double CommissionAmount = Double.parseDouble(tableRow.get(3));
			Double Amount = Double.parseDouble(tableRow.get(4));
			tableRow.clear();
			no_of_Session += SessionNo;
			no_of_hrs += HoursNo;
			commissionAmount += CommissionAmount;
			amount += Amount;
		}
		tableRow.add(dateMonthString);
		tableRow.add(String.valueOf(no_of_Session));
		tableRow.add(String.valueOf(no_of_hrs));
		tableRow.add(String.valueOf(commissionAmount));
		tableRow.add(String.valueOf(amount));
		tableRow.add(String.valueOf(amount - commissionAmount));
		if (flagMonthPresent)
			vTablePerYear.add(monthPosition, tableRow);
		else
			vTablePerYear.add(tableRow);

	}

	public String getYear() {
		return vYear;
	}

	public ArrayList<ArrayList<String>> getTable() {
		ArrayList<ArrayList<String>> returnTable = new ArrayList<ArrayList<String>>();
		returnTable = vTablePerYear;
		Double totalSessionCovered = 0.0;
		Double totalHrsSpend = 0.0;
		Double totalCommissionPaid = 0.0;
		Double totalEarnedAmount = 0.0;
		Double totalNetAmount = 0.0;
		for(int i=0;i<returnTable.size();i++){
			ArrayList<String> singleRow = new ArrayList<String>();
			singleRow = returnTable.get(i);
			totalSessionCovered += Double.valueOf(singleRow.get(1));
			totalHrsSpend += Double.valueOf(singleRow.get(2));
			totalCommissionPaid += Double.valueOf(singleRow.get(3));
			totalEarnedAmount += Double.valueOf(singleRow.get(4));
			totalNetAmount += Double.valueOf(singleRow.get(5));
		}
		ArrayList<String> totalRow = new ArrayList<String>();
		totalRow.add("TOTAL");
		totalRow.add(totalSessionCovered.toString());
		totalRow.add(totalHrsSpend.toString());
		totalRow.add(totalCommissionPaid.toString());
		totalRow.add(totalEarnedAmount.toString());
		totalRow.add(totalNetAmount.toString());
		returnTable.add(totalRow);
		return returnTable;
	}

	public void setYear(String Year) {
		vYear = Year;
	}

	public void setYear(Integer Year) {
		vYear = Year.toString();
	}

}
