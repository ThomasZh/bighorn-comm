package net.younguard.bighorn.broadcast.cmd;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.Command;
import net.younguard.bighorn.comm.ResponseCommand;
import net.younguard.bighorn.comm.tlv.ByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

/**
 * this the the message response from server for ping request.
 * 
 * Copyright 2014 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class MsgPangResp
		extends ResponseCommand
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

//		logger.debug("from command to tlv package:(tag=" + this.getTag() + ", child=" + i + ", length="
//				+ tlv.getLength() + ")");
		return tlv;
	}

	@Override
	public Command decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		this.setTag(tlv.getTag());

		int childCount = 2;
		TlvParser.decodeChildren(tlv, childCount);
//		logger.debug("from tlv:(tag=" + this.getTag() + ", child=" + childCount + ") to command");

		int i = 0;
		TlvObject tSequence = tlv.getChild(i++);
		this.setSequence(ByteUtil.byte2Int(tSequence.getValue()));
//		logger.debug("sequence: " + this.getSequence());

		TlvObject tRespState = tlv.getChild(i++);
		this.setRespState(ByteUtil.byte2Short(tRespState.getValue()));
//		logger.debug("respState: " + this.getRespState());

		return this;
	}

	// //////////////////////////////////////////////////////

	public MsgPangResp()
	{
		this.setTag(CommandTag.MESSAGE_PANG_RESPONSE);
	}

	public MsgPangResp(int sequence)
	{
		this();

		this.setSequence(sequence);
	}

	public MsgPangResp(int sequence, short state)
	{
		this(sequence);

		this.setRespState(state);
	}

//	private final static Logger logger = LoggerFactory.getLogger(MsgPangResp.class);

}
