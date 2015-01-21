package net.younguard.bighorn.comm.tlv;

/**
 * Parser for tlv object
 * 
 * Copyright 2014-2015 by Young Guard Salon Community, China. All rights reserved.
 * http://www.younguard.net
 * 
 * NOTICE ! You can copy or redistribute this code freely, but you should not
 * remove the information about the copyright notice and the author.
 * 
 * @author ThomasZhang, thomas.zh@qq.com
 */
public class TlvParser
{
	/**
	 * encode: TLVObject->byte[]
	 * 
	 * @param tlv
	 * @return byte[]
	 */
	public static byte[] encode(TlvObject tlv)
	{
		byte[] b = new byte[6 + tlv.getLength()];
		return tlv.toBytes(b, 0);
	}

	// ///////////////////////////////////////////////////

	/**
	 * decode: byte[]->TLVObject
	 * 
	 * @param b
	 * @return TlvObject
	 */
	public static TlvObject decodeHeader(byte[] b)
	{
		TlvObject tlv = new TlvObject();

		tlv.setTag(ByteUtil.byte2Short(b, 0));
		tlv.setLength(ByteUtil.byte2Int(b, TlvObject.TAG_LENGTH));

		return tlv;
	}

	/**
	 * decode: byte[]->TLVObject
	 * 
	 * @param b
	 * @return TlvObject
	 */
	public static TlvObject decode(byte[] b)
	{
		TlvObject tlv = decodeHeader(b);
		byte[] value = ByteUtil.sub(b, TlvObject.HEADER_LENGTH);
		tlv.setValue(value);

		return tlv;
	}

	/**
	 * decode: TLVObject.body(byte[])->TLVObject
	 * 
	 * @param tlv
	 * @param childCount
	 */
	public static void decodeChildren(TlvObject tlv, int childCount)
			throws IllegalArgumentException
	{
		for (int i = 0; i < childCount; i++) {
			int leftLength = getLeftChildLenght(tlv, i);
			byte[] s1 = ByteUtil.sub(tlv.getValue(), leftLength + TlvObject.TAG_LENGTH, leftLength
					+ TlvObject.HEADER_LENGTH);

			int childLength = ByteUtil.byte2Int(s1, 0);
			if (childLength < 0 || childLength > tlv.getLength())
				throw new IllegalArgumentException("Parse child[" + i + "] length(" + childLength + ") error.");

			byte[] childStr = ByteUtil.sub(tlv.getValue(), leftLength, leftLength + TlvObject.HEADER_LENGTH
					+ childLength);
			TlvObject childTLV = TlvParser.decode(childStr);
			tlv.add(childTLV);
		}
	}

	/**
	 * get string length from begin to TLVObject[i]
	 * 
	 * @param tlv
	 * @param i
	 * @return int
	 */
	private static int getLeftChildLenght(TlvObject tlv, int index)
	{
		int rs = 0;
		for (int i = 0; i < index; i++) {
			TlvObject child = tlv.getChild(i);
			int length = child.getLength();
			rs += TlvObject.HEADER_LENGTH + length;
		}
		return rs;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		TlvObject tlv1 = new TlvObject((byte) 1, (byte) 14, "PM-00-01-00-01".getBytes());
		TlvObject tlv2 = new TlvObject((byte) 2, (byte) 3, "0.5".getBytes());
		TlvObject tlv = new TlvObject((byte) 12, (byte) 23, null);
		tlv.add(tlv1);
		tlv.add(tlv2);

		byte[] b = TlvParser.encode(tlv);
		System.out.println("encode: [" + TlvByteUtilPrinter.getHex(b) + "]");
		TlvObject rs = TlvParser.decode(b);
		TlvParser.decodeChildren(rs, 2);
		rs.debugPrint();
	}

}
