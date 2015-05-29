package util;

import java.security.MessageDigest;

public class FonctionString {
	/**
	 * Retourne le hachage md5 de la chaîne passée en paramètres
	 * @param La chaine à hacher [String]
	 * @return Le chaîne hachée [String:md5]
	 * @throws Exception
	 */
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
