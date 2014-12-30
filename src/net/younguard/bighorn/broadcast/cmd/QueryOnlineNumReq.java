package net.younguard.bighorn.broadcast.cmd;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.Command;
import net.younguard.bighorn.comm.RequestCommand;
import net.younguard.bighorn.comm.tlv.ByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

public class QueryOnlineNumReq
		extends RequestCommand
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, ByteUtil.INTEGER_LENGTH, ByteUtil.int2Byte(this.getSequence()));

		TlvObject tlv = new TlvObject(this.getTag());
		tlv.add(tSequence);

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

		int childCount = 1;
		TlvParser.decodeChildren(tlv, childCount);
		// logger.debug("from tlv:(tag=" + this.getTag() + ", child=" +
		// childCount + ") to command");

		int i = 0;
		TlvObject tSequence = tlv.getChild(i++);
		this.setSequence(ByteUtil.byte2Int(tSequence.getValue()));
		// logger.debug("sequence: " + this.getSequence());

		return this;
	}

	// //////////////////////////////////////////////////////

	public QueryOnlineNumReq()
	{
		this.setTag(CommandTag.QUERY_ONLINE_NUMBER_REQUEST);
	}

	public QueryOnlineNumReq(int sequence)
	{
		this();

		this.setSequence(sequence);
	}

	// private final static Logger logger =
	// LoggerFactory.getLogger(QueryOnlineNumReq.class);
}
