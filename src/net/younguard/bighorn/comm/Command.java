package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.TlvObject;

import org.apache.mina.core.session.IoSession;

public interface Command
{
	public TlvObject encode()
			throws UnsupportedEncodingException;

	public Command decode(TlvObject tlv)
			throws UnsupportedEncodingException;

	public Command execute(IoSession session)
			throws Exception;
}
