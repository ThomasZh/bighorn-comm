package net.younguard.bighorn.broadcast.cmd;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.Command;
import net.younguard.bighorn.comm.ResponseCommand;
import net.younguard.bighorn.comm.tlv.ByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

public class QueryOnlineNumResp
		extends ResponseCommand
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, ByteUtil.INTEGER_LENGTH, ByteUtil.int2Byte(this.getSequence()));
		TlvObject tRespState = new TlvObject(i++, ByteUtil.SHORT_LENGTH, ByteUtil.short2Byte(this.getRespState()));
		TlvObject tNum = new TlvObject(i++, ByteUtil.INTEGER_LENGTH, ByteUtil.int2Byte(this.getNum()));

		TlvObject tlv = new TlvObject(this.getTag());
		tlv.add(tSequence);
		tlv.add(tRespState);
		tlv.add(tNum);

		// logger.debug("from command to tlv package:(tag=" + this.getTag() +
		// ", child=" + i + ", length="
		// + tlv.getLength() + ")");
		return tlv;
	}

	@Override
	public Command decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		this.setTag(tlv.getTag());

		int childCount = 3;
		TlvParser.decodeChildren(tlv, childCount);
		// logger.debug("from tlv:(tag=" + this.getTag() + ", child=" +
		// childCount + ") to command");

		int i = 0;
		TlvObject tSequence = tlv.getChild(i++);
		this.setSequence(ByteUtil.byte2Int(tSequence.getValue()));
		// logger.debug("sequence: " + this.getSequence());

		TlvObject tRespState = tlv.getChild(i++);
		this.setRespState(ByteUtil.byte2Short(tRespState.getValue()));
		// logger.debug("respState: " + this.getRespState());

		TlvObject tNum = tlv.getChild(i++);
		this.setNum(ByteUtil.byte2Int(tNum.getValue()));
		// logger.debug("num: " + this.getNum());

		return this;
	}

	// //////////////////////////////////////////////////////

	public QueryOnlineNumResp()
	{
		this.setTag(CommandTag.QUERY_ONLINE_NUMBER_RESPONSE);
	}

	public QueryOnlineNumResp(int sequence)
	{
		this();

		this.setSequence(sequence);
	}

	public QueryOnlineNumResp(int sequence, short state)
	{
		this(sequence);

		this.setRespState(state);
	}

	public QueryOnlineNumResp(int sequence, short state, int num)
	{
		this(sequence, state);

		this.setNum(num);
	}

	private int num = 0;

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	// private final static Logger logger =
	// LoggerFactory.getLogger(QueryOnlineNumResp.class);

}
