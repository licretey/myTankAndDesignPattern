package nettyCodec;

import com.licretey.tank.PropertyMgr;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServer {
    public ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//netty提供管理channel的类

    public void serverStart(){
        // 1. 定义两组线程
        // 一组管理者
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(2);
        // 一组负责服务
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup(4);


        try {
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
                    socketChannel
                            .pipeline()
                            .addLast(new MsgDecoder()) //添加解码器
                            .addLast(new ServerChildHandler());//添加对信息的处理逻辑类（责任链模式）
                }
            });//添加初始化channel的类
            int serverPort = Integer.parseInt(PropertyMgr.get("serverPort"));
            ChannelFuture future = sb.bind(serverPort).sync();
            ServerFrame.INSTANCE.updateServerMsg("Server stared!");

            //等待关闭时触发
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }


    }

    class ServerChildHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //确认channel能用时存入组中
            clients.add(ctx.channel());
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            TankMsg tankMsg = (TankMsg) msg;
            ServerFrame.INSTANCE.updateClientMsg(tankMsg.toString());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            //异常处理
            cause.printStackTrace();
            clients.remove(ctx.channel());
            ctx.close();
        }
    }

}




