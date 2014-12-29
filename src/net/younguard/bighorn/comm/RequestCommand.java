package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.ByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

import org.apache.mina.core.session.IoSession;

public abstract class RequestCommand
		implements Command
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, ByteUtil.SHORT_LENGTH, ByteUtil.short2Byte(this.getSequence()));

		TlvObject tlv = new TlvObject(this.getTag());
		tlv.add(tSequence);

		return tlv;
	}

	@Override
	public Command decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		this.setTag(tlv.getTag());

		int childCount = 1;
		TlvParser.decodeChildren(tlv, childCount);

		TlvObject tSequence = tlv.getChild(0);
		this.setSequence(ByteUtil.byte2Short(tSequence.getValue()));

		return this;
	}

	@Override
	public Command execute(IoSession session)
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
	private short sequence;

	public short getTag()
	{
		return tag;
	}

	public void setTag(short tag)
	{
		this.tag = tag;
	}

	public short getSequence()
	{
		return sequence;
	}

	public void setSequence(short sequence)
	{
		this.sequence = sequence;
	}

}
