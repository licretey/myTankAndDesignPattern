package net.bio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",9999);//服务器地址
        OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write("Hello I'm Client!");
        bw.newLine();
        bw.flush();
        bw.close();
    }
}
