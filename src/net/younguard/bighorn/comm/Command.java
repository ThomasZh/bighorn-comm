package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.TlvObject;

import org.apache.mina.core.session.IoSession;

/**
 * Basic interface of command
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public interface Command
{
	public TlvObject encode()
			throws UnsupportedEncodingException;

	public Command decode(TlvObject tlv)
			throws UnsupportedEncodingException;

	public Command execute(IoSession session)
			throws Exception;
}
