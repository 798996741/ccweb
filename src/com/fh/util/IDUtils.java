package com.fh.util;

import java.util.UUID;


/**
 * @author Aaron��Li
 * @date 2017��9��20�� ����11:51:31
 */
public class IDUtils {
	private static byte[] lock = new byte[0];

	private final static long w = 100000000;

	public static String createID() {
		long r = 0;
		synchronized (lock) {
			r = (long) ((Math.random() + 1) * w);
		}

		return System.currentTimeMillis() + String.valueOf(r).substring(1);
	}
	
	public static String createUUID() {
		String uuid = UUID.randomUUID().toString();
		System.out.println(uuid);
		uuid = uuid.replace("-", ""); 
		return uuid;
	}
	
	

}
