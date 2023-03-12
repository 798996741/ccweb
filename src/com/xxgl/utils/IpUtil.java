package com.xxgl.utils;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil {
	public static void main(String[] args){
		try {
			InetAddress ip4 = Inet4Address.getLocalHost();
			System.out.println(ip4.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}				
	}
		
	public static String getIp(){
		String ip="";
		try {
			InetAddress ip4 = Inet4Address.getLocalHost();
			ip=ip4.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}
}
