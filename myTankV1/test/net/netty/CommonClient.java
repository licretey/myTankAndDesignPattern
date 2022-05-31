package net.netty;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class CommonClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",9999);//服务器地址
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write("Hello I'm Netty Client!");
        bw.newLine();
        bw.flush();

        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String readLineStr =reader.readLine();
        System.out.println(readLineStr);

        bw.close();
    }
}
