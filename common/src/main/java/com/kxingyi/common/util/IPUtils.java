package com.kxingyi.common.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;

import sun.net.util.IPAddressUtil;

/**
 * IP工具类
 * @author admin
 *
 */
public class IPUtils {
	
	/**
     * @return 本机IP
     * @throws SocketException
     */
    public static String getRealIp() {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        
        Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                
                if (!ip.isSiteLocalAddress() 
                        && !ip.isLoopbackAddress() 
                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                	if (ip instanceof Inet4Address)
    				{
    					netip = ip.getHostAddress();
    				}
                	if(netip == null || netip.equals("")){
                		if (ip instanceof Inet6Address)
        				{
        					netip = ip.getHostAddress();
        				}
                	}
                    
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() 
                        && !ip.isLoopbackAddress() 
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                	if (ip instanceof Inet4Address)
    				{
    					localip = ip.getHostAddress();
    				}
                	if(localip == null || localip.equals("")){
                		if (ip instanceof Inet6Address)
        				{
        					localip = ip.getHostAddress();
        				}
                	}
                }
            }
        }
     
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

	/**
	 * ipv4地址校验
	 * @param ip
	 * @return
	 */
	public static boolean isIpv4Format(String ip) {
		if (StringUtils.isBlank(ip)) return false;
		return IPAddressUtil.isIPv4LiteralAddress(ip);
		
	}
	
	/**
	 * ipv6地址校验
	 * @param ip
	 * @return
	 */
	public static boolean isIpv6Format(String ip) {
		if (StringUtils.isBlank(ip)) return false;
		return IPAddressUtil.isIPv6LiteralAddress(ip);
		
	}
	
	public static void main(String[] args) {
		System.out.println(isIpv4Format("127.0.0.1"));
		System.out.println(isIpv6Format("::0"));
	}
}
