package br.edu.ifrn.eventos.seguraca;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AutenticacaoHelper {
	
	public static String toMD5(String senha) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");

			BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
			String s = hash.toString(16);

			while (s.length() < 32)
				s = "0" + s;

			return s;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
