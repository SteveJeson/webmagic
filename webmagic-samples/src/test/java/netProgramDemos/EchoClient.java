package netProgramDemos;

import java.io.*;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/11.
 */
public class EchoClient {

    private String host = "localhost";
    private int port = 8000;
    private Socket socket;

    public EchoClient () throws IOException{
        socket = new Socket(host,port);
    }

    private PrintWriter getWriter (Socket socket) throws  IOException{
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }

    private BufferedReader getReader (Socket socket) throws  IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public void talk () throws IOException{
        try {
            BufferedReader br = getReader(socket);
            PrintWriter pw = getWriter(socket);
            BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
            String msg = null;
            while ((msg=localReader.readLine()) != null) {
                pw.println(msg);
                System.out.println(br.readLine());
                if (msg.equals("bye")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void main (String[] args) throws IOException {
        new EchoClient().talk();
    }
}
