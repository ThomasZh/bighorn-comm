package net.younguard.bighorn.comm;

import java.io.UnsupportedEncodingException;

import net.younguard.bighorn.comm.tlv.TlvByteUtil;
import net.younguard.bighorn.comm.tlv.TlvObject;
import net.younguard.bighorn.comm.tlv.TlvParser;

public abstract class QueryPaginationResp
		extends ResponseCommand
{
	@Override
	public TlvObject encode()
			throws UnsupportedEncodingException
	{
		int i = 0;
		TlvObject tSequence = new TlvObject(i++, TlvByteUtil.int2Byte(this.getSequence()));
		TlvObject tRespState = new TlvObject(i++, TlvByteUtil.short2Byte(this.getRespState()));
		TlvObject tJson = new TlvObject(i++, this.getJson());

		TlvObject tlv = new TlvObject(this.getTag());
		tlv.add(tSequence);
		tlv.add(tRespState);
		tlv.add(tJson);

		return tlv;
	}

	@Override
	public QueryPaginationResp decode(TlvObject tlv)
			throws UnsupportedEncodingException
	{
		this.setTag(tlv.getTag());

		int childCount = 3;
		TlvParser.decodeChildren(tlv, childCount);

		int i = 0;
		TlvObject tSequence = tlv.getChild(i++);
		this.setSequence(TlvByteUtil.byte2Int(tSequence.getValue()));

		TlvObject tRespState = tlv.getChild(i++);
		this.setRespState(TlvByteUtil.byte2Short(tRespState.getValue()));

		TlvObject tJson = tlv.getChild(i++);
		json = new String(tJson.getValue(), "UTF-8");

		return this;
	}

	private String json;

	public String getJson()
	{
		return json;
	}

	public void setJson(String json)
	{
		this.json = json;
	}

}
