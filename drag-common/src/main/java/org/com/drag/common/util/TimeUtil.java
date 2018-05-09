package org.com.drag.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
/**
 * 
 * Copyright © 2016优易数据. All rights reserved.

 * @ClassName: TimeUtil

 * @Description: 时间工具类

 * @author: jiaonanyue

 * @date: 2016年10月27日 下午2:07:19
 */
public class TimeUtil {
	
	/**
	 * 将时间格式化yyyy-MM-dd  HH:mm:ss
	 * @param data
	 * @return
	 */
	public static String getSimpleDateFormat(Date data){
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		return f.format(data);
	}
	
	/**
	 * 获取某年的最后一天
	 * @param year
	 * @return
	 */
	public static String getCurrYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		Date currYearLast = calendar.getTime();
		Date addDays = DateUtils.addDays(currYearLast, 1);
		return f.format(addDays);
	}

	/**
	 * 获取某年的第一天
	 * @param year
	 * @return
	 */
	public static String getCurrYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		return f.format(currYearFirst);
	}

	/**
	 * 按自然月增加
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date addMonth(Date date, int num) {
		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(date);
		calBegin.add(Calendar.MONTH, num);
		return calBegin.getTime();
	}

}
