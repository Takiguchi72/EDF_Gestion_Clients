package util;

import java.security.MessageDigest;

public class FonctionString {
	public static String md5(String chaine) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(chaine.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}//fin md5
}
