package netProgramDemos;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/13.
 */
public class SimpleClient {
    public static void main (String[] args) throws Exception {
        Socket socket = new Socket("localhost",8000);
        socket.setTrafficClass(0x10 | 0x04);
//        socket.setSoLinger(true,0);         //socket关闭后,底层socket立即关闭
        socket.setSoLinger(true,3600);    //socket关闭后，底层socket延迟3600秒再关闭
        OutputStream out = socket.getOutputStream();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0;i<100000;i++) {
            buffer.append(i);
        }
        out.write(buffer.toString().getBytes());
        System.out.println("开始关闭socket");
        long begin = System.currentTimeMillis();
        socket.close();
        long end = System.currentTimeMillis();
        System.out.println("关闭socket所用时间为:"+(end-begin)+"ms");
    }
}
