package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.ByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

/**
 * The mother object of response command
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public abstract class ResponseCommand
		extends RequestCommand
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, ByteUtil.INTEGER_LENGTH, ByteUtil.int2Byte(this.getSequence()));
		TlvObject tRespState = new TlvObject(i++, ByteUtil.SHORT_LENGTH, ByteUtil.short2Byte(this.getRespState()));

		TlvObject tlv = new TlvObject(this.getTag());
		tlv.add(tSequence);
		tlv.add(tRespState);

		return tlv;
	}

	@Override
	public Command decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		this.setTag(tlv.getTag());

		int childCount = 2;
		TlvParser.decodeChildren(tlv, childCount);

		int i = 0;
		TlvObject tSequence = tlv.getChild(i++);
		this.setSequence(ByteUtil.byte2Int(tSequence.getValue()));

		TlvObject tRespState = tlv.getChild(i++);
		this.setRespState(ByteUtil.byte2Short(tRespState.getValue()));

		return this;
	}

	// //////////////////////////////////////////////////////

	public ResponseCommand()
	{
	}

	public ResponseCommand(short tag)
	{
		this.setTag(tag);
	}

	public ResponseCommand(short tag, short sequence)
	{
		this(tag);

		this.setSequence(sequence);
	}

	public ResponseCommand(short tag, short sequence, short state)
	{
		this(tag, sequence);

		this.setRespState(state);
	}

	private short respState;

	public short getRespState()
	{
		return respState;
	}

	public void setRespState(short respState)
	{
		this.respState = respState;
	}

}
