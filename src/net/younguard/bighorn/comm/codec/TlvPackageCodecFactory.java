package net.younguard.bighorn.comm.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * mina tlv package encoder/decoder factory
 * 
 * Copyright 2014 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class TlvPackageCodecFactory
		implements ProtocolCodecFactory
{
	public TlvPackageCodecFactory()
	{
		encoder = new TlvPackageEncoder();
		decoder = new TlvPackageDecoder();
	}

	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	@Override
	public ProtocolEncoder getEncoder(IoSession session)
			throws Exception
	{
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session)
			throws Exception
	{
		return decoder;
	}
}
