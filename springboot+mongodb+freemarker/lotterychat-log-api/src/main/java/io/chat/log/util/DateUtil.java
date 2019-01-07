/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.chat.log.util;


import io.chat.log.vo.ApplicationException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date数据转换
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月5日
 */
public class DateUtil {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	public final static String DATE_BACKSLACH_PATTERN = "yyyy/MM/dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public final static String DATE_BACKSLACH_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
	public final static String LONG_TIME_PATTERN = "yyyyMMddHH24mmssms";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    
    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     * @throws Exception 
     */
    public static String format(Date date,String pattern,boolean isAuto) throws Exception {
        if(date != null){
        	SimpleDateFormat df = null;
        	 try {
        		 df = new SimpleDateFormat(pattern);
  				return df.format(date);
 			} catch (Exception e) {
 				if(!isAuto) throw new Exception(e);
 			}
        	if(isAuto) {
        		try {
    				df = new SimpleDateFormat(DATE_PATTERN);
    				return df.format(date);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
                try {
    				df = new SimpleDateFormat(DATE_PATTERN);
    				return df.format(date);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        	}
        }
        return null;
    }
    
    /**
     * 将日期格式转为13位的时间戳,用来比较大小
     * @param date
     * @return
     */
    public static long format2Long(Date date) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(LONG_TIME_PATTERN);
            return Long.valueOf(df.format(date));
        }
        return 0;
    }
    
    /**
     * 将时间格式的字符串转为Date类型
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
     */
    public static Date parse(Object value)  throws ParseException {
    	if(value == null) {
    		return null;
    	}
    	if(value instanceof Date) {
    		return (Date)value;
    	}
    	if(value instanceof Timestamp) {
    		return (Timestamp)value;
    	}
    	if(value instanceof Long) {
    		try {return new Date((Long)value);} catch (Exception e) {}
    	}
    	if(value instanceof String) {
    		try {return parse(value.toString(),DATE_TIME_PATTERN);} catch (Exception e) {}
        	try {return parse(value.toString(),DATE_BACKSLACH_TIME_PATTERN);} catch (Exception e) {}
        	try {return parse(value.toString(),DATE_PATTERN);} catch (Exception e) {}
        	try {return parse(value.toString(),DATE_BACKSLACH_PATTERN);} catch (Exception e) {}
    	}
    	return null;
    }
    
    /**
     * 将时间格式的字符串转为Date类型
     * @author Kevin zhaosheji.kevin@gmail.com
     * @date 2019年1月5日
     */
    public static Date parse(String date, String pattern) throws ParseException {
        if(date != null){
            try {
				SimpleDateFormat df = new SimpleDateFormat(pattern);
				return df.parse(date);
			} catch (Exception e) {}
        }
        return null;
    }
}
