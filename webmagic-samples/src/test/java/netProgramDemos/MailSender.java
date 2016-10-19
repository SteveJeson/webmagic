package netProgramDemos;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/13.
 */
public class MailSender {
    private String smtpServer = "smtp.mydomain.com";

    private int port = 25;

    public static void main (String[] args) {
        Message msg = new Message("tom@abc.com","l1518911343@163.com","Hello","hi，I miss you very much");
        new MailSender().sendMail(msg);
    }

    public void sendMail (Message msg) {
        Socket socket = null;
        try {
            socket = new Socket(smtpServer, port);
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            String localhost = InetAddress.getLocalHost().getHostName();        //客户主机名字

            sendAndReceive(null,br,pw);
            sendAndReceive("Hello"+localhost,br,pw);
            sendAndReceive("MAIL FROM:<"+msg.from+">",br,pw);
            sendAndReceive("RCPT TO:<"+msg.to+">",br,pw);
            sendAndReceive("DATA",br,pw);
            pw.println(msg.data);
            System.out.println("Client>"+msg.data);
            sendAndReceive(".",br,pw);
            sendAndReceive("QUIT",br,pw);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket!=null) try {
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

class Message {
    String from;
    String to;
    String subject;
    String content;
    String data;
    public Message (String from,String to,String subject,String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.data = "Subject:"+subject+"\r\n"+content;
    }
}