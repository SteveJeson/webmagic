package netProgramDemos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/10/11.
 */
public class EchoPlayer {

    public String echo (String msg){
        return "echo:" + msg;
    }

    public void talk () throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String msg = null;
        while((msg=br.readLine())!=null){
            System.out.println(echo(msg));
            if(msg.equals("byte")){
                System.out.println("即将退出进程。。。");
                break;
            }
        }
    }

    public static void main (String[] args) throws IOException {
        new EchoPlayer().talk();
    }
}
