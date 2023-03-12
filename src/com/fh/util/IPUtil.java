package com.fh.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.xmlbeans.XmlException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取客户端ip
* @author zx 
* @date  上午11:56:05
 */
public class IPUtil {
	
	public static String getIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		
		  String ip = request.getHeader("x-forwarded-for");  
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = request.getHeader("Proxy-Client-IP");  
	       }  
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = request.getHeader("WL-Proxy-Client-IP");  
	       }  
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	           ip = request.getRemoteAddr();  
	       }  
	       return ip;  
//        String ip = request.getHeader("X-Forwarded-For");
//        System.out.println(ip);
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            //多次反向代理后会有多个ip值，第一个ip才是真实ip
//            int index = ip.indexOf(",");
//            if(index != -1){
//                return ip.substring(0,index);
//            }else{
//                return ip;
//            }
//        }
//        ip = request.getHeader("X-Real-IP");
//        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
//            return ip;
//        }
//        return request.getRemoteAddr();
    }

	public static String getLocalIpv4Address() throws SocketException {
		Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		while (allNetInterfaces.hasMoreElements()){
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()){
				InetAddress ip = (InetAddress) addresses.nextElement();
				if (ip != null
						&& ip instanceof Inet4Address
						&& !ip.isLoopbackAddress() //loopback地址即本机地址，IPv4的loopback范围是127.0.0.0 ~ 127.255.255.255
						&& ip.getHostAddress().indexOf(":")==-1){
					//System.out.println("本机的IP = " + ip.getHostAddress());
					return ip.getHostAddress();
				}
			}
		}
		return "";
	}

	public static void main(String[] args) throws XmlException, OpenXML4JException, IOException {

		System.out.println(getLocalIpv4Address());

	}


}
