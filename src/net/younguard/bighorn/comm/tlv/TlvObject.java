package net.younguard.bighorn.comm.tlv;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * TLV: Tag/Length/Value
 * 
 * <li>|----|------|------------|<li/>
 * <li>|Tag-|Length|Value-------|<li/>
 * <li>|2---|4-----|Length------|<li/>
 * <li>|1001|----12|012345678901|<li/>
 * 
 * @author thomas.zhang
 */
public class TlvObject
		implements Serializable
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 73978765361086014L;

	public static final int TAG_LENGTH = 2;
	public static final int HEADER_LENGTH = 6;
	public static final int MAX_LENGTH = 65535;

	// ////////////////////////////////////////////////////////
	public static final int VERSION = 1;

	// ////////////////////////////////////////////////////////

	private short tag = 0;
	/**
	 * value length or
	 */
	private int payloadLength = 0;
	private byte[] value;

	public TlvObject()
	{
	}

	public TlvObject(short tag)
	{
		this.tag = tag;
	}

	public TlvObject(short tag, int length)
	{
		this.tag = tag;
		this.payloadLength = length;
	}

	public TlvObject(short tag, int length, byte[] value)
	{
		this.tag = tag;
		this.payloadLength = length;
		this.value = value;
	}

	public TlvObject(short tag, String str)
	{
		this.tag = tag;

		if (str != null && str.length() > 0) {
			try {
				byte[] bytes = str.getBytes("UTF-8");
				this.payloadLength = bytes.length;
				this.value = bytes;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				this.payloadLength = 0;
			}
		} else {
			this.payloadLength = 0;
		}
	}

	public TlvObject(int tag)
	{
		this((short) tag);
	}

	public TlvObject(int tag, int length)
	{
		this((short) tag, length);
	}

	public TlvObject(int tag, int length, byte[] value)
	{
		this((short) tag, length, value);
	}

	public TlvObject(int tag, byte[] bytes)
	{
		this(tag, bytes != null ? bytes.length : 0, bytes);
	}

	public TlvObject(int tag, String str)
	{
		this((short) tag, str);
	}

	// ////////////////////////////////////////////////////////

	public short getTag()
	{
		return tag;
	}

	public void setTag(short tag)
	{
		this.tag = tag;
	}

	public void setLength(int length)
	{
		this.payloadLength = length;
	}

	public void setPayloadLength(int length)
	{
		this.payloadLength = length;
	}

	public int getPayloadLength()
	{
		return payloadLength;
	}

	public int getLength()
	{
		return payloadLength;
	}

	public byte[] getValue()
	{
		return value;
	}

	public void setValue(byte[] value)
	{
		this.value = value;
	}

	// ////////////////////////////////////////////////////////

	/**
	 * å­???¨å??TLVObject??????è¡?
	 */
	private List<TlvObject> children = null;

	/**
	 * Add another TLVObject into this one.
	 * 
	 * @param tlv
	 */
	public void add(TlvObject tlv)
	{
		if (children == null)
			children = new ArrayList<TlvObject>();
		children.add(tlv);

		// auto calculate children length
		this.payloadLength += tlv.getSelfLength();
	}

	/**
	 * calculate self length include: header and value length, no child exist!
	 * 
	 * @return total length of package
	 */
	private int getSelfLength()
	{
		if (value == null) {
			return HEADER_LENGTH;
		} else {
			return HEADER_LENGTH + value.length;
		}
	}

	/**
	 * ?????¤ä??ä¸?TLVObject
	 * 
	 * @param tlv
	 */
	public void remove(TlvObject tlv)
	{
		if (children != null) {
			children.remove(tlv);
			this.payloadLength -= tlv.getSelfLength();
		}
	}

	/**
	 * ???å¾?childrenä¸?å­?TLVObject???ä¸????
	 * 
	 * @return n
	 */
	public int getChildCount()
	{
		if (children == null)
			return 0;
		else
			return children.size();
	}

	/**
	 * ???å¾?childrenä¸?ç¬?iä¸?å­?TLVObject
	 * 
	 * @param i
	 *            (0,1,2,...n-1)
	 * @return TLVObject
	 */
	public TlvObject getChild(int i)
			throws IllegalArgumentException
	{
		if (children == null)
			throw new IllegalArgumentException("Illegal child index: " + i);
		if (i < 0 || i >= children.size())
			throw new IllegalArgumentException("Illegal child index: " + i);

		return (TlvObject) children.get(i);
	}

	// ////////////////////////////////////////////////////////

	/**
	 * ???å½???½æ??,merge?????????ä¸?é«?
	 */
	public byte[] toBytes()
	{
		byte[] header = new byte[HEADER_LENGTH];
		// put tag
		ByteUtil.short2Byte(header, 0, this.getTag());
		// put length from tag position
		ByteUtil.int2Byte(header, TAG_LENGTH, this.getPayloadLength());

		if (children == null) {
			if (this.getValue() == null)
				return header;
			else
				return ByteUtil.merge(header, this.getValue());
		} else {
			byte[] rs = null;
			for (int i = 0; i < children.size(); i++) {
				TlvObject tlv = (TlvObject) children.get(i);
				rs = ByteUtil.merge(rs, tlv.toBytes());
			}

			return ByteUtil.merge(header, rs);
		}
	}

	/**
	 * ???å½???½æ??,??¿å??newè¿?å¤????byte[]
	 * 
	 * @param b
	 * @param offset
	 * @return bytes
	 */
	public byte[] toBytes(byte[] b, int offset)
	{
		ByteUtil.short2Byte(b, offset, this.getTag());
		ByteUtil.int2Byte(b, offset + TAG_LENGTH, this.getPayloadLength());

		if (children == null) {
			ByteUtil.copy(b, this.getValue(), offset + HEADER_LENGTH);
		} else {
			int length = 0;
			for (int i = 0; i < children.size(); i++) {
				TlvObject tlv = (TlvObject) children.get(i);
				tlv.toBytes(b, offset + HEADER_LENGTH + i * HEADER_LENGTH + length);
				length += tlv.getPayloadLength();
			}
		}

		return b;
	}

	// ////////////////////////////////////////////////////////

	public void debugPrint()
	{
		if (children == null) {
			System.out.println("decode tag: " + this.getTag());
			System.out.println("decode length: " + this.getPayloadLength());
			System.out.println("decode value: [" + TlvByteUtilPrinter.getHex(this.getValue()) + "]");
			TlvByteUtilPrinter.hexDump("Value: ", this.getValue(), this.getPayloadLength());
		} else {
			System.out.println("decode tag: " + this.getTag());
			System.out.println("decode length: " + this.getPayloadLength());
			System.out.println("decode child ----------------------");
			for (int i = 0; i < children.size(); i++) {
				TlvObject tlv = (TlvObject) children.get(i);
				tlv.debugPrint();
			}
		}
	}
}
