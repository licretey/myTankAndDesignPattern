package nettyChat;

import com.licretey.tank.PropertyMgr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServer {
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//netty提供管理channel的类

    public static void main(String[] args) throws Exception{
        // 1. 定义两组线程
        // 一组管理者
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(2);
        // 一组负责服务
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup(4);

        //2. 配置Server启动辅助类（谁管理谁服务，建立什么样的通道，处理接口实现）
        // Server启动辅助类
        ServerBootstrap sb = new ServerBootstrap();
        //第一个参数为管理者，第二个参数为服务者
        sb.group(bossLoopGroup, workerLoopGroup);
        //设置channel类型为异步全双工类型
        sb.channel(NioServerSocketChannel.class);
        // netty帮我们处理了accept接收请求的过程，我们只需要写处理函数（观察者模式）
        sb.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception{
                //socketChannel参数是netty帮忙初始化建立的连接（建立完连接会调用initChannel方法实现）
                socketChannel.pipeline().addLast(new ServerChildHandler());//添加对信息的处理逻辑类（责任链模式）
            }
        });//添加初始化channel的类
        int serverPort = Integer.parseInt(PropertyMgr.get("serverPort"));
        ChannelFuture future = sb.bind(serverPort).sync();

        //阻塞
        future.channel().closeFuture().sync();

        //应写在finally中
        bossLoopGroup.shutdownGracefully();
        workerLoopGroup.shutdownGracefully();

    }

    static class ServerChildHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //确认channel能用时存入组中
            ChatServer.clients.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf buf = (ByteBuf)msg;//Byte是直接操作内存的，不会自动被gc
            byte[] bytes = new byte[buf.readableBytes()];//readableBytes返回要读取的字符长度
            //从readerIndex处读取字符到数组bytes中（readerIndex读指针）
            buf.getBytes(buf.readerIndex(),bytes);
            String str = new String(bytes);
            if("__bye__".equals(str)){
                System.out.println("Client Ready to Quit.");
                ChatServer.clients.remove(ctx.channel());
                ctx.close();
            }else {
                ChatServer.clients.writeAndFlush(buf);//writeAndFlush会自动对bug进行释放
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            //异常处理
            cause.printStackTrace();
            ChatServer.clients.remove(ctx.channel());
            ctx.close();
        }
    }

}




