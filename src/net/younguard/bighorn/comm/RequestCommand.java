package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.TlvByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

import org.apache.mina.core.session.IoSession;

/**
 * The father object of request command
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights
 * reserved. http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public abstract class RequestCommand
		implements Command
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, TlvByteUtil.int2Byte(this.getSequence()));

		TlvObject tlv = new TlvObject(this.getTag());
		tlv.add(tSequence);

		return tlv;
	}

	@Override
	public RequestCommand decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		this.setTag(tlv.getTag());

		int childCount = 1;
		TlvParser.decodeChildren(tlv, childCount);

		TlvObject tSequence = tlv.getChild(0);
		this.setSequence(TlvByteUtil.byte2Int(tSequence.getValue()));

		return this;
	}

	@Override
	public ResponseCommand execute(IoSession session)
			throws Exception
	{
		return null;
	}

	// //////////////////////////////////////////////////////

	public RequestCommand()
	{
	}

	public RequestCommand(short tag)
	{
		this.setTag(tag);
	}

	public RequestCommand(short tag, short sequence)
	{
		this(tag);

		this.setSequence(sequence);
	}

	private short tag;
	private int sequence;

	public short getTag()
	{
		return tag;
	}

	public void setTag(short tag)
	{
		this.tag = tag;
	}

	public int getSequence()
	{
		return sequence;
	}

	public void setSequence(int sequence)
	{
		this.sequence = sequence;
	}

}
