package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.TlvObject;

public abstract class CommandParser
{
	public static TlvObject encode(Command cmd)
			throws UnsupportedEncodingException
	{
		return null;
	}

	public static Command decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		return null;
	}

}
