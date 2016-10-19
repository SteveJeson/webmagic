package netProgramDemos;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/13.
 */
public class PortScanner {
    public void scan (String host) {
        Socket socket = null;
        for (int port = 8080;port < 9000;port++) {
            try {
                InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
                socket = new Socket("115.239.211.112",port);
                System.out.println("There is a server on port:"+port);
            } catch (IOException e) {
                System.out.println("Cannot connect to port:"+port);
            } finally {
                try {
                    if (socket!=null) socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main (String[] args) {
        String host = "localhost";
        if (args.length > 0) {
            host = args[0];
        }
        new PortScanner().scan(host);
    }
}
