package com.kxingyi.common.util;

import java.io.IOException;
import java.net.*;

/**
 * @Author: chengpan
 * @Date: 2020/2/6
 */
public class LinuxServiceUtil {

    public static boolean isUdpPortOpen(int port){
        try {
            DatagramSocket server = new DatagramSocket(port);
            server.close();
            return false;
        } catch (SocketException ex) {
            return true;
        }
    }

    public static boolean isTcpPortOpen(int port){
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", port), 500);
        }catch (Exception e){
            return false;
        }finally {
            try {
                if(socket != null){
                    socket.close();
                }
            }catch (IOException e){}

        }
        return true;
    }
}
