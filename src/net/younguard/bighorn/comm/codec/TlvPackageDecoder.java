package net.younguard.bighorn.comm.codec;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.TlvObject;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * mina tlv package decoder
 * 
 * Copyright 2014 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class TlvPackageDecoder
		extends CumulativeProtocolDecoder
{
	/**
	 * key point: 1. just enough data(length), return false, tell parent class
	 * do next parser; 2、not enough data, return false, let's parent class call
	 * CumulativeProtocolDecoder put message content into IoSession, waiting
	 * next data, merge it, do next doDecode 3、more data, return true, parser
	 * this data, do next parser used the remain data
	 */
	public boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception
	{
		if (in.remaining() > 0) {// has data, read 6 bytes as message length
			in.mark();// mark this place, for reset

			short tag = in.getShort();
			int length = in.getInt();
//			logger.debug("from ioBuffer to tlv:(tag=" + tag + ", length=" + length + ")");

			// unknown command tag
			if (tag < 1000 || tag > 5100) {
//				String hexDump = in.getHexDump(200);
//				logger.warn("Not define tag:" + tag, hexDump);

				throw new UnsupportedEncodingException("Not define tag:" + tag);
			}

			// unknown command length
			if (length < 0 || length > 65535) {
//				String hexDump = in.getHexDump(200);
//				logger.warn("Package out of length:" + length, hexDump);

				throw new UnsupportedEncodingException("Package out of length:" + length);
			}

			// not enough length, return true
			if (length > in.remaining()) {// length is not enough, reset, not
											// read size
				in.reset();
				return false;// receive new data, merge to completed data
			} else {
				TlvObject pkg = new TlvObject();
				byte[] body = new byte[length];
				in.get(body, 0, length);

				pkg.setTag(tag);
				pkg.setLength(length);
				pkg.setValue(body);

				out.write(pkg);

				if (in.remaining() > 0) {// has more data, let's parser next
											// package.
					return true;
				}
			}
		}
		return false;// success, let's parent class deal with next
	}

//	private final static Logger logger = LoggerFactory.getLogger(TlvPackageDecoder.class);

}