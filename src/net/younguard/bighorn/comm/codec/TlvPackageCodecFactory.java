package net.younguard.bighorn.comm.codec;


public class TlvPackageCodecFactory
		implements ProtocolCodecFactory
{
	public TlvPackageCodecFactory()
	{
		encoder = new TlvPackageEncoder();
		decoder = new TlvPackageDecoder();
	}

	private ProtocolEncoder encoder;
	private ProtocolDecoder decoder;

	@Override
	public ProtocolEncoder getEncoder(IoSession session)
			throws Exception
	{
		return encoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session)
			throws Exception
	{
		return decoder;
	}
}
