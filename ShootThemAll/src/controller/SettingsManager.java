package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import model.User;

public class SettingsManager {
	private static SettingsManager ls;
	public static final User ADMIN = new User("admin", "admin");
	public static final int MAX_LEVEL = 3;

	private SettingsManager() {
		
	}



	// MD5
	public static String cryptMD5(String message) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(message.getBytes());
			byte byteData[] = md.digest();
			// convert the byte to hex format
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

			// System.out.println("Digest(in hex format):: " + sb.toString());
			message = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return message;
	}

}
