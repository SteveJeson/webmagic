package netProgramDemos;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/11.
 */
public class EchoServer {

    private int port = 8000;
    private ServerSocket serverSocket;

    public EchoServer () throws IOException{
        serverSocket = new ServerSocket(port);
        System.out.println("----服务器启动----");
    }

    public void service () {
        while(true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                Thread workThread = new Thread(new Handler(socket));
               /* BufferedReader br = getReader(socket);
                PrintWriter pw = getWriter(socket);
                String msg = null;
                while ((msg=br.readLine())!=null) {
                    System.out.println(msg);
                    pw.println(echo(msg));
                    if (msg.equals("byte")) {
                        break;
                    }
                }*/
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main (String[] args) throws IOException{
        new EchoServer().service();
    }
}

class Handler implements Runnable {

    private Socket socket;

    public Handler (Socket socket) {
        this.socket = socket;
    }

    public String echo (String msg) {
        return "echo:" + msg;
    }

    private PrintWriter getWriter (Socket socket) throws IOException{
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }

    private BufferedReader getReader (Socket socket) throws IOException{
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    @Override
    public void run() {
        try {
            System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            String msg = null;
            while ((msg = br.readLine()) != null) {
                System.out.println(msg);
                pw.write(echo(msg));
                if (msg.equals("bye")) {
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
             try {
                 if(socket!=null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}