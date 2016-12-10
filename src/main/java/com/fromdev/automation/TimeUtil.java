package com.fromdev.automation;

import java.util.Calendar;

public class TimeUtil {
	public static enum SLEEP_TYPE {
		TYPING, READING, BREAK
	};

	public static int getTypingTimeSeconds() {
		return 1000 * ((int) (15 * Math.random()) + 4);
	}

	public static int getNumberBetween(int start, int end) {
		return (int) (end * Math.random()) + start;
	}

	public static boolean isWeekDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
				|| cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;

	}

	public static boolean isWorkingHour() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.HOUR_OF_DAY) >= 10
				&& cal.get(Calendar.HOUR_OF_DAY) < 18;

	}

	public static void sleep(SLEEP_TYPE type) {
		try {
			switch (type) {
			case READING: {
				Thread.sleep(TimeUtil.getTypingTimeSeconds() + 15000);
				break;
			}
			case BREAK: {
				Thread.sleep(TimeUtil.getTypingTimeSeconds() * 15);
				break;
			}
			case TYPING:
			default: {
				Thread.sleep(TimeUtil.getTypingTimeSeconds());
				break;
			}

			}
		} catch (Exception e) {
			//
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			int num = getNumberBetween(1, 25);
			if (num > 25 || num < 1)
				System.out.println("wrong");
		}
		System.out.println("done" + isWorkingHour()
				+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
	}
}
