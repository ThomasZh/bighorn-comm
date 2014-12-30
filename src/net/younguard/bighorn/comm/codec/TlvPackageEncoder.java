package net.younguard.bighorn.comm.codec;

import net.younguard.bighorn.comm.tlv.TlvObject;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * mina tlv package encoder
 * 
 * Copyright 2014 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class TlvPackageEncoder
		extends ProtocolEncoderAdapter
{
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception
	{
		TlvObject pkg = (TlvObject) message;
		byte[] b = pkg.toBytes();

//		logger.debug("from tlv:(tag=" + pkg.getTag() + ", length=" + pkg.getLength() + ") to ioBuffer");
		// TlvByteUtilPrinter.hexDump("tlv payload: ", b);

		IoBuffer buff = IoBuffer.allocate(TlvObject.HEADER_LENGTH + pkg.getLength());
		buff.setAutoExpand(true);
		if (b != null && b.length >= TlvObject.HEADER_LENGTH) {
			buff.put(b);
			buff.flip();
			out.write(buff);
		}
	}

//	private final static Logger logger = LoggerFactory.getLogger(TlvPackageEncoder.class);

}