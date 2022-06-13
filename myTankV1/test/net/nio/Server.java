package net.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress("127.0.0.1",9999));
        ssc.configureBlocking(false);//配置非阻塞
        System.out.println("server listening on :"+ssc.getLocalAddress());

        Selector selector = Selector.open();//轮询者
        ssc.register(selector, SelectionKey.OP_ACCEPT);//在ServerSocketChannel对Selector注册OP_ACCEPT事件

        while (true){
            selector.select();//未监听到事件时阻塞
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            while (it.hasNext()){
                SelectionKey key = it.next();
                it.remove();
                handle(key);
            }
        }
    }

    private static void handle(SelectionKey key) {
        if(key.isAcceptable()){
        // 有请求过来是，新创建一个连接处理请求
        // 同时将新连接的 读事件 交付给selector
            try {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                sc.configureBlocking(false);//配置非阻塞

                sc.register(key.selector(),SelectionKey.OP_READ);
            }catch (IOException e){
                e.printStackTrace();
            }
        } else if(key.isReadable()){
            SocketChannel sc = null;
            try {
                sc = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                byteBuffer.clear();
                int len = sc.read(byteBuffer);
                if(len!=-1){
                    System.out.println(new String(byteBuffer.array(),0,len));
                }
                ByteBuffer buffer2Write = ByteBuffer.wrap("HelloClient".getBytes());
                sc.write(buffer2Write);

                sc.register(key.selector(),SelectionKey.OP_READ);
            }catch (IOException e){
                e.printStackTrace();
            } finally {
                try {
                    if(sc!=null){
                        sc.close();//写在finally中
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
