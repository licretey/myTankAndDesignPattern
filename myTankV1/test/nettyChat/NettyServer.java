package nettyChat;

import com.licretey.tank.PropertyMgr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
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
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //msg是由netty帮忙收集封装成ByteBuf类型的读取数据
            ByteBuf byteBuf = (ByteBuf)msg;
            System.out.println(byteBuf.toString());
            ctx.writeAndFlush(msg);//将信息回写数据
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
        }
    }

}




