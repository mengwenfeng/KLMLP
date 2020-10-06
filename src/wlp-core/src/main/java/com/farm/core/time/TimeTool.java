package com.farm.core.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TimeTool {

	 
	public static TimeStopWatch startTimeStopWatch() {
		return TimeStopWatch.start();
	}

	 
	public static String getTimeDate12() {
		// yyyyMMddhhmm
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Date _date = new Date();

		return _sdf.format(_date);
	}

	 
	public static int getMonthMaxDay(String year, String month) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		if (month.trim().length() == 1) {
			month = "0" + month;
		}
		try {
			cal.setTime(sdf.parse(year + month));
		} catch (ParseException e) {
			throw new RuntimeException();
		}
		return cal.getActualMaximum(Calendar.DATE);
	}

	 
	public static List<String[]> getFirstYearList(int num) {
		String currentYear = getTimeDate12().substring(0, 4);
		List<String[]> list = new ArrayList<String[]>();
		Integer curentYearint = Integer.valueOf(currentYear);
		for (int i = 0; i < num; i++) {
			String[] yearNode = new String[2];
			yearNode[0] = curentYearint.toString();
			yearNode[1] = curentYearint.toString() + "年";
			list.add(yearNode);
			curentYearint--;
		}
		return list;
	}

	 
	public static List<String[]> getQuarterList() {
		List<String[]> list = new ArrayList<String[]>();
		String[] node = new String[2];
		node[0] = "1";
		node[1] = "一季度";
		list.add(node);

		String[] node2 = new String[2];
		node2[0] = "2";
		node2[1] = "二季度";
		list.add(node2);

		String[] node3 = new String[2];
		node3[0] = "3";
		node3[1] = "三季度";
		list.add(node3);

		String[] node4 = new String[2];
		node4[0] = "4";
		node4[1] = "四季度";
		list.add(node4);
		return list;
	}

	 
	public static List<String[]> getMonthList() {
		List<String[]> list = new ArrayList<String[]>();
		String[] node = new String[2];
		node[0] = "1";
		node[1] = "一月";
		list.add(node);

		String[] node2 = new String[2];
		node2[0] = "2";
		node2[1] = "二月";
		list.add(node2);

		String[] node3 = new String[2];
		node3[0] = "3";
		node3[1] = "三月";
		list.add(node3);

		String[] node4 = new String[2];
		node4[0] = "4";
		node4[1] = "四月";
		list.add(node4);

		String[] node5 = new String[2];
		node5[0] = "5";
		node5[1] = "五月";
		list.add(node5);

		String[] node6 = new String[2];
		node6[0] = "6";
		node6[1] = "六月";
		list.add(node6);

		String[] node7 = new String[2];
		node7[0] = "7";
		node7[1] = "七月";
		list.add(node7);

		String[] node8 = new String[2];
		node8[0] = "8";
		node8[1] = "八月";
		list.add(node8);

		String[] node9 = new String[2];
		node9[0] = "9";
		node9[1] = "九月";
		list.add(node9);

		String[] node10 = new String[2];
		node10[0] = "10";
		node10[1] = "十月";
		list.add(node10);

		String[] node11 = new String[2];
		node11[0] = "11";
		node11[1] = "十一月";
		list.add(node11);

		String[] node12 = new String[2];
		node12[0] = "12";
		node12[1] = "十二月";
		list.add(node12);
		return list;
	}

	 
	public static Date getTimeDate12ForDay(int addday, Date date) {
		Date _date = null;
		_date = date;
		return getDateAfter(_date, addday);
	}

	 
	public static Date getTimeDate12ForMinute(int frontMinute, Date date) {
		Date _date = null;
		Calendar cal = Calendar.getInstance();// 使用默认时区和语言环境获得一个日历。
		cal.setTime(date);
		cal.add(Calendar.MINUTE, frontMinute);// 取当前日期的前一天.
		_date = cal.getTime();
		return _date;
	}

	 
	public static String getFormatTimeDate12(String timeDate12_14, String yyyyMMddHHmmss) {
		try {
			if (timeDate12_14.length() > 14) {
				timeDate12_14 = timeDate12_14.substring(0, 14);
			}
			if (12 == timeDate12_14.length()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
				SimpleDateFormat newSdf = new SimpleDateFormat(yyyyMMddHHmmss);
				Date date;
				date = sdf.parse(timeDate12_14);
				return newSdf.format(date);
			} else {
				if (14 == timeDate12_14.length()) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					SimpleDateFormat newSdf = new SimpleDateFormat(yyyyMMddHHmmss);
					Date date = sdf.parse(timeDate12_14);
					return newSdf.format(date);
				} else {
					if (8 == timeDate12_14.length()) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						SimpleDateFormat newSdf = new SimpleDateFormat(yyyyMMddHHmmss);
						Date date = sdf.parse(timeDate12_14);
						return newSdf.format(date);
					} else {
						return timeDate12_14;
					}
				}
			}
		} catch (ParseException e) {
			return e.toString();
		}
	}

	 
	public static String getTimeDate14() {
		 
		SimpleDateFormat _sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date _date = new Date();

		return _sdf.format(_date);
	}

	 
	public static String getRandom18() {
		// TODO Auto-generated method stub
		String _random = UUID.randomUUID().toString();

		return _random.substring(0, 18);
	}

	@SuppressWarnings("static-access")
	 
	public static String getOid() {
		String _oid = new TimeTool().getTimeDate14() + new TimeTool().getRandom18();
		return _oid;
	}

	 
	private static Date getDateAfter(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	 
	public static String getCurrentMonth() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		return String.valueOf(now.get(Calendar.MONTH) + 1);
	}

	 
	public static String getCurrentQuarter() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		int month = now.get(Calendar.MONTH) + 1;
		switch (month) {
		case 1:
			return "1";
		case 2:
			return "1";
		case 3:
			return "1";
		case 4:
			return "2";
		case 5:
			return "2";
		case 6:
			return "2";
		case 7:
			return "3";
		case 8:
			return "3";
		case 9:
			return "3";
		case 10:
			return "4";
		case 11:
			return "4";
		case 12:
			return "4";
		default:
			break;
		}
		return "-1";
	}

	 
	public static List<String> getDayListByMonth(String rankingYear, String rankingMonth) {
		List<String> list = new ArrayList<String>();
		String[] dayStr = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15",
				"16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
		// 如果是1月、3月、5月、7月、8月、10月、12月，为31天
		if ("1".equals(rankingMonth) || "3".equals(rankingMonth) || "5".equals(rankingMonth) || "7".equals(rankingMonth)
				|| "8".equals(rankingMonth) || "10".equals(rankingMonth) || "12".equals(rankingMonth)) {
			for (int i = 0; i < dayStr.length; i++) {
				list.add(dayStr[i]);
			}
			// 如果是4月、6月、9月、11月，为30天
		} else if ("4".equals(rankingMonth) || "6".equals(rankingMonth) || "9".equals(rankingMonth)
				|| "11".equals(rankingMonth)) {
			for (int i = 0; i < dayStr.length - 1; i++) {
				list.add(dayStr[i]);
			}
		} else {
			int yearInt = Integer.parseInt(rankingYear);
			// 判断是平年还是闰年，平年2月28天，闰年2月29天
			if (yearInt % 4 == 0 && yearInt % 100 != 0 || yearInt % 400 == 0) {
				for (int i = 0; i < dayStr.length - 2; i++) {
					list.add(dayStr[i]);
				}
			} else {
				for (int i = 0; i < dayStr.length - 3; i++) {
					list.add(dayStr[i]);
				}
			}
		}
		return list;
	}

	 
	public static int countMinuteMinus(Date dateBefore, Date dateCurrent) {
		// 当前时间
		Date curentTime = dateCurrent;
		// 当前时间
		Calendar curentC = Calendar.getInstance();
		curentC.setTime(curentTime);
		// 上次访问时间
		Calendar curentV = Calendar.getInstance();
		curentV.setTime(dateBefore);
		// 相差分钟数
		long timeMillis = (curentC.getTimeInMillis() - curentV.getTimeInMillis()) / (1000 * 60);
		int time = (int) timeMillis;
		return time;
	}

	 
	public static int countDayMinus(Date dateBefore, Date dateCurrent) {
		// 当前时间
		Date curentTime = dateCurrent;
		// 当前时间
		Calendar curentC = Calendar.getInstance();
		curentC.setTime(curentTime);
		// 上次访问时间
		Calendar curentV = Calendar.getInstance();
		curentV.setTime(dateBefore);
		// 相差分钟数
		long timeMillis = (curentC.getTimeInMillis() - curentV.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		int time = (int) timeMillis;
		return time;
	}

	 
	public static int countDayMinus(String starttime14, String endtime14) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return countDayMinus(sdf.parse(starttime14), sdf.parse(endtime14));
	}

	 
	public static Date parseDate(String dateStr, String yyyyMMdd) throws ParseException {
		SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
		df.applyPattern(yyyyMMdd);
		Date ddTest = df.parse(dateStr);
		return ddTest;
	}

	 
	public static Date nextDay(Date date, int i) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		Date date1 = new Date(calendar.getTimeInMillis());
		return date1;
	}

	 
	public static String format(Date date, String yyyyMMddHHmmss) {
		SimpleDateFormat sdf = new SimpleDateFormat(yyyyMMddHHmmss);
		return sdf.format(date);
	}

	 
	public static int getWeekNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return w;
	}

	 
	public static int getDayNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_MONTH);
		if (w < 0) {
			w = 0;
		}
		return w;
	}

	public static String getTimeDate(Date timeDate12ForDay, String yyyyMMddHHmmss) {
		 
		SimpleDateFormat _sdf = new SimpleDateFormat(yyyyMMddHHmmss);
		return _sdf.format(timeDate12ForDay);
	}

}
