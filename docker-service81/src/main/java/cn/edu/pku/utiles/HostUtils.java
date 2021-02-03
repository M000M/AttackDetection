package cn.edu.pku.utiles;

import java.net.InetAddress;

public class HostUtils {

    public static String getHostAddress() {
        String result = "0.0.0.0";
        try {
            InetAddress address = InetAddress.getLoopbackAddress();
            result = address.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(getHostAddress());
    }
}
