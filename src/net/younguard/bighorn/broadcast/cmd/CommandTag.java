package net.younguard.bighorn.broadcast.cmd;

/**
 * command define.
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class CommandTag
{
	public static final short MESSAGE_PING_REQUEST = 1001;
	public static final short MESSAGE_PANG_RESPONSE = 1002;
	public static final short MESSAGE_PONG_RESPONSE = 1004;
	public static final short QUERY_ONLINE_NUMBER_REQUEST = 1005;
	public static final short QUERY_ONLINE_NUMBER_RESPONSE = 1006;
	public static final short REGISTER_NOTIFY_TOKEN_REQUEST = 1007;
	public static final short REGISTER_NOTIFY_TOKEN_RESPONSE = 1008;
	public static final short SOCKET_CLOSE_REQUEST = 1009;
}
