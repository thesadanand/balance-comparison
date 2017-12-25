package com.balance.comparison.util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class BalanceComparisonUtils {


	/**
	 * returns the reporting quaters for a given date range
	 * @param from
	 * @param to
	 * @return
	 */
	public  LinkedList<String> getRrpPrd(int from, int to) {

		int fromYr = Integer.parseInt(String.valueOf(from).substring(0,String.valueOf(from).length()-2));
		int toYr = Integer.parseInt(String.valueOf(to).substring(0,String.valueOf(to).length()-2));

		Calendar fromDate = new GregorianCalendar(fromYr, (from%100), 1);
		Calendar toDate = new GregorianCalendar(toYr, (to%100), 1);

		/*Calendar fromDate = new GregorianCalendar(2017, 1, 1);
		Calendar toDate = new GregorianCalendar(2017, 4, 1);
		 */
		int yearDiff = toDate.get(Calendar.YEAR) - fromDate.get(Calendar.YEAR);
		int mntDiff = yearDiff*12 + toDate.get(Calendar.MONTH) - fromDate.get(Calendar.MONTH);

//		System.out.println("differnece "+mntDiff);

		LinkedList<String> mnthList = new LinkedList<String>();
		for(int i=0;i<=mntDiff;i++){
			mnthList.add(String.valueOf(from));
			from+=1;
			/*			if((from%100)==13) {

				String fromStr = mnthList.getLast().substring(0, mnthList.getLast().length()-2);
				int tmp = Integer.parseInt(fromStr) + 1;
				fromStr = String.valueOf(tmp).concat("01");
				mnthList.add(fromStr);
				from = (Integer.parseInt(fromStr));
			}*/
		}
//		System.out.println("mnthList:  "+mnthList);
		return mnthList;

	}
}
