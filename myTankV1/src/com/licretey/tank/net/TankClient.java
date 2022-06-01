package com.licretey.tank.net;

import com.licretey.tank.GameModel;
import com.licretey.tank.PropertyMgr;
import com.licretey.tank.TankFrame;
import com.licretey.tank.net.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class TankClient {
    private Channel channel = null;

    public static final TankClient INSTANCE = new TankClient();

    private TankClient() {

    }

    public void connect(){
        String serverAddr = (String)PropertyMgr.get("server");
        int serverPort = Integer.parseInt(PropertyMgr.get("serverPort"));
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();

        try {
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>(){
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //Netty返回监听到的channel，对channel添加处理逻辑
                    channel = socketChannel;
                    socketChannel
                            .pipeline()
                            .addLast(new MsgEncoder())
                            .addLast(new MsgDecoder())
                            .addLast(new MyHandler());
                }
            });
            System.out.println("Connected to Server !");

            ChannelFuture future = bootstrap.connect(serverAddr, serverPort).sync();
            //连接上时可以获取channel，closeFuture是当连接触发close时的处理函数（没有则会一直等待监听，即等待关闭）
            future.channel().closeFuture().sync();
            System.out.println("Is Closeing !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭客户端服务进程
            workerGroup.shutdownGracefully();
        }
    }

    public void send(TankJoinMsg tankJoinMsg){
//        ByteBuf byteBufText = Unpooled.copiedBuffer();//辅助类将信息转为byteBuf类型
        channel.writeAndFlush(tankJoinMsg);//encoder将对象转为bytebuf
    }

    public void closeConnection() {
        //发送特殊标志，通知sever连接将主动断开
//        send("__bye__");
        channel.close();
    }

    static class MyHandler extends SimpleChannelInboundHandler<TankJoinMsg>{
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
//            ctx.writeAndFlush(new TankMsg(5,8));
            ctx.writeAndFlush(new TankJoinMsg(TankFrame.SINGLE_FRAME.getGm().getPlayer()));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg tankJoinMsg) throws Exception {
            //消息已转换为指定的类型
            System.out.println(tankJoinMsg.toString());
            tankJoinMsg.handle();
        }
    }

    public static void main(String[] args) {
        TankClient chatClient = new TankClient();
        chatClient.connect();
    }
}



