package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.ByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

public abstract class ResponseCommand
		extends RequestCommand
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, ByteUtil.SHORT_LENGTH, ByteUtil.short2Byte(this.getSequence()));
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
		this.setSequence(ByteUtil.byte2Short(tSequence.getValue()));

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
