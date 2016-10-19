package netProgramDemos;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/13.
 */
public class SimpleServer {
    public static void main (String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8000);
        Socket socket = serverSocket.accept();
        Thread.sleep(5000);         //睡眠5秒后再读输入流
        InputStream socketIn = socket.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int len = -1;
        do {
            len = socketIn.read(buff);
            if(len!=-1) buffer.write(buff,0,len);
        }while(len!=-1);
        System.out.println(new String(buffer.toByteArray()));       //把字节数组转换为字符串
    }
}
