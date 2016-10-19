package netProgramDemos;

import java.io.IOException;
import java.net.*;

/**
 * Created by Administrator on 2016/10/13.
 */
public class ConnectTester {
    public void connect (String host, int port) {
        SocketAddress remoteAddr = new InetSocketAddress(host, port);
        Socket socket = null;
        String result = "";
        try {
            long begin = System.currentTimeMillis();
            socket =new Socket();
            socket.connect(remoteAddr, 1000);
            long end = System.currentTimeMillis();
            result = (end-begin)+"ms";
        }  catch (BindException e) {
            result = "Local address and port can't be binded";
        }  catch (UnknownHostException e) {
            result = "Unknown host";
        }  catch (ConnectException e) {
            result = "Connect refused";
        }  catch (SocketTimeoutException e) {
            result = "Timeout";
        }  catch (IOException e) {
            result = "failure";
        } finally {
            try{
                if(socket!=null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(remoteAddr+":"+result);
    }

    public static void main (String[] args) {
        String host = "www.ifeng.com";
        int port = 8080;
        if (args.length>1) {
            host = args[0];
            port = Integer.parseInt(args[1]);
        }
        new ConnectTester().connect(host, port);
    }
}
