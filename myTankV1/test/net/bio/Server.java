package net.bio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws Exception{
        ServerSocket socket = new ServerSocket();
        socket.bind(new InetSocketAddress("127.0.0.1",9999));//绑定ip标识（对ip创建一个socket管理对象）
        Socket accept = socket.accept();//阻塞监听（成功连接返回一个连接对象）
        System.out.println("accepted a socked");
        InputStreamReader isr = new InputStreamReader(accept.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        String readLineStr = br.readLine();
        System.out.println(readLineStr);
        accept.close();
    }
}
