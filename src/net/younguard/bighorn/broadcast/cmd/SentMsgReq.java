package net.younguard.bighorn.broadcast.cmd;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import net.younguard.bighorn.broadcast.ErrorCode;
import net.younguard.bighorn.comm.Command;
import net.younguard.bighorn.comm.RequestCommand;
import net.younguard.bighorn.comm.tlv.ByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

import org.apache.mina.core.service.IoService;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SentMsgReq
		extends RequestCommand
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, ByteUtil.SHORT_LENGTH, ByteUtil.short2Byte(this.getSequence()));
		TlvObject tContent = new TlvObject(i++, content);

		TlvObject tlv = new TlvObject(this.getTag());
		tlv.add(tSequence);
		tlv.add(tContent);

		logger.debug("from command to tlv package:(tag=" + this.getTag() + ", child=" + i + ", length="
				+ tlv.getLength() + ")");
		return tlv;
	}

	@Override
	public Command decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		this.setTag(tlv.getTag());

		int childCount = 2;
		TlvParser.decodeChildren(tlv, childCount);
		logger.debug("from tlv:(tag=" + this.getTag() + ", child=" + childCount + ") to command");

		int i = 0;
		TlvObject tSequence = tlv.getChild(i++);
		this.setSequence(ByteUtil.byte2Short(tSequence.getValue()));
		logger.debug("sequence: " + this.getSequence());

		TlvObject tContent = tlv.getChild(i++);
		content = new String(tContent.getValue(), "UTF-8");
		logger.debug("content: " + content);

		return this;
	}

	@Override
	public Command execute(IoSession session)
			throws Exception
	{
		IoService ioService = session.getService();
		Map<Long, IoSession> sessions = ioService.getManagedSessions();

		// broadcast
		for (Map.Entry<Long, IoSession> it : sessions.entrySet()) {
			long sessionId = it.getKey();
			IoSession ioSession = it.getValue();

			if (sessionId == session.getId()) {
				logger.debug("This is sender's session=[" + sessionId + "]");
				continue; // don't send me again.
			}

			TlvObject sentMsgReq = BroadcastCommandParser.encode(this);

			ioSession.write(sentMsgReq);
			logger.debug("broadcast message=[" + this.getContent() + "] to session=[" + sessionId + "].");
		}

		SentMsgResp respCmd = new SentMsgResp(this.getSequence(), ErrorCode.SUCCESS);
		return respCmd;
	}

	// //////////////////////////////////////////////////////

	public SentMsgReq()
	{
		this.setTag(CommandTag.SENT_MESSAGE_REQUEST);
	}

	public SentMsgReq(short sequence)
	{
		this();

		this.setSequence(sequence);
	}

	public SentMsgReq(short sequence, String content)
	{
		this(sequence);

		this.setContent(content);
	}

	private String content;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	private final static Logger logger = LoggerFactory.getLogger(SentMsgReq.class);

}
