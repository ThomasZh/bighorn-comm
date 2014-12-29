package net.younguard.bighorn.broadcast;

public class ErrorCode
{
	public static final short SUCCESS = 100;
	public static final short UNKNOWN_FAILURE = 200;
	public static final short CONNECTION_CLOSED = 300;
	public static final short NOT_ALLOW = 400; // operation not allowed
	public static final short SYNC_VER_NOT_SAME = 500; // operation not allowed
	public static final short ENCODING_FAILURE = 600; // EncodingException

}
