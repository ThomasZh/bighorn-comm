package net.younguard.bighorn.comm.util;

import java.security.SecureRandom;

//import sun.misc.BASE64Encoder;
import org.apache.commons.codec.binary.Base64;

public class EcryptUtil
{
	public static String salt()
	{
		byte[] salt = new byte[10];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		// FIXME, use apache base64 encoder instead of sun base64encoder
		// lwz7512@2014/09/15
		String outp = Base64.encodeBase64String(salt);
		return outp.substring(0, 10);
	}

	public static String md5(String str)
	{
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(str.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
