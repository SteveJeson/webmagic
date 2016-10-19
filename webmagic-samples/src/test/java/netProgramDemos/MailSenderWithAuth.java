package netProgramDemos;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/13.
 */
public class MailSenderWithAuth {

    private String smtpServer = "smtp.mydomain.com";

    private int port = 25;

    public static void main (String[] args) {
        Message msg = new Message("java_mail@citiz.net","l1518911343@163.com","hello","hi,I miss you very much.");
        new MailSenderWithAuth().sendMail(msg);
    }

    public void sendMail (Message msg) {
        Socket socket = null;
        try {
            socket = new Socket(smtpServer,port);
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            String localhost = InetAddress.getLocalHost().getHostName();

            String username = "java_mail";
            String password = "123456";
            //对用户名和口令进行base64编码
            username = new BASE64Encoder().encode(username.getBytes());
            password = new BASE64Encoder().encode(password.getBytes());
            sendAndReceive(null,br,pw);
            sendAndReceive("EHLO"+localhost,br,pw);
            sendAndReceive("AUTH LOGIN",br,pw);
            sendAndReceive(username,br,pw);
            sendAndReceive(password,br,pw);
            sendAndReceive("MAIL FROM;"+msg.from+"",br,pw);
            sendAndReceive("RCPT TO:"+msg.to+"",br,pw);
            sendAndReceive("DATA",br,pw);
            pw.println(msg.data);
            System.out.println("Client>"+msg.data);
            sendAndReceive(".",br,pw);
            sendAndReceive("QUIT",br,pw);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(socket!=null) try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送一行数据,并接收一行服务器的响应数据
     */
    public void sendAndReceive (String str, BufferedReader br, PrintWriter pw) throws IOException {
        if (str != null) {
            System.out.println("Client>"+str);
            pw.println(str);
        }
        String response;
        if ((response = br.readLine())!=null) {
            System.out.println("Server>"+response);
        }
    }

    public BufferedReader getReader (Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public PrintWriter getWriter (Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }

}

