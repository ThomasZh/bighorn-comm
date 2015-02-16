package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.TlvByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

public abstract class QueryPaginationReq
		extends RequestCommand
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, TlvByteUtil.int2Byte(this.getSequence()));
		TlvObject tPageNum = new TlvObject(i++, TlvByteUtil.short2Byte(this.getPageNum()));
		TlvObject tPageSize = new TlvObject(i++, TlvByteUtil.short2Byte(this.getPageSize()));

		TlvObject tlv = new TlvObject(this.getTag());
		tlv.add(tSequence);
		tlv.add(tPageNum);
		tlv.add(tPageSize);

		return tlv;
	}

	@Override
	public QueryPaginationReq decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		this.setTag(tlv.getTag());

		int childCount = 3;
		TlvParser.decodeChildren(tlv, childCount);

		int i = 0;
		TlvObject tSequence = tlv.getChild(i++);
		this.setSequence(TlvByteUtil.byte2Int(tSequence.getValue()));

		TlvObject tPageNum = tlv.getChild(i++);
		this.setPageNum(TlvByteUtil.byte2Short(tPageNum.getValue()));

		TlvObject tPageSize = tlv.getChild(i++);
		this.setPageSize(TlvByteUtil.byte2Short(tPageSize.getValue()));

		return this;
	}

	private short pageNum;
	private short pageSize;

	public short getPageNum()
	{
		return pageNum;
	}

	public void setPageNum(short pageNum)
	{
		this.pageNum = pageNum;
	}

	public short getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(short pageSize)
	{
		this.pageSize = pageSize;
	}

}
