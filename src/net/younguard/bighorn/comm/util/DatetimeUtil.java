package net.younguard.bighorn.comm.util;

/**
 * Datetime util
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class DatetimeUtil
{
	public static int currentTimestamp()
	{
		return new Long(System.currentTimeMillis() / 1000).intValue();
	}

	public static int nextDayTimestamp()
	{
		return currentTimestamp() + 86400; // 24*3600
	}

}
