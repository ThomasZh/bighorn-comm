package net.younguard.bighorn.broadcast;

/**
 * error code define.
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class ErrorCode
{
	public static final short SUCCESS = 100;
	public static final short UNKNOWN_FAILURE = 200;
	public static final short CONNECTION_CLOSED = 300;
	public static final short NOT_ALLOW = 400; // operation not allowed
	public static final short SYNC_VER_NOT_SAME = 500; // operation not allowed
	public static final short ENCODING_FAILURE = 600; // EncodingException

}
