package net.younguard.bighorn.comm.util;

/**
 * print full log error message util
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class LogErrorMessage
{
	public static String getFullInfo(Throwable cause)
	{
		String ss = String.format("Exception in thread '%s' %s\n", Thread.currentThread().getName(), cause);
		for (StackTraceElement te : cause.getStackTrace()) {
			if (te.getFileName() == null) {
				ss += String.format("\t|- %s.%s(Unknown Source)\n", te.getClassName(), te.getMethodName());
			} else {
				ss += String.format("\t|- %s.%s(%s:%d)\n", te.getClassName(), te.getMethodName(), te.getFileName(),
						te.getLineNumber());
			}
		}
		return ss;
	}
}
