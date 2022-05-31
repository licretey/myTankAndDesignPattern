package nettyChat;

import com.licretey.tank.PropertyMgr;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;

import javax.naming.Reference;
import java.lang.ref.ReferenceQueue;

public class ChatClient {
    private Channel channel = null;

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
                    socketChannel.pipeline().addLast(new MyHandler());
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

    public void send(String text){
        ByteBuf byteBufText = Unpooled.copiedBuffer(text.getBytes());//辅助类将信息转为byteBuf类型
        channel.writeAndFlush(byteBufText);
    }

    static class MyHandler extends ChannelInboundHandlerAdapter{

        /*
         *通道中有数据过来时触发此读取操作
         */
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = null;
            try {
                buf = (ByteBuf)msg;//Byte是直接操作内存的，不会自动被gc
                byte[] bytes = new byte[buf.readableBytes()];//readableBytes返回要读取的字符长度
                //从readerIndex处读取字符到数组bytes中（readerIndex读指针）
                buf.getBytes(buf.readerIndex(),bytes);
                String str = new String(bytes);
                ClientFrame.INSTANCE.updateText(str);
            } finally {
                if(buf!=null){
                    //buf引用指向的内存处总共存在的引用数（但只需释放自己的一次）
                    //buf.refCnt();
                    ReferenceCountUtil.release(buf);
                }
            }
            // System.out.println(msg.toString());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }

    }
}



