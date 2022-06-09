package com.licretey.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<Msg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf buf) throws Exception {
        //消息头
        buf.writeInt(msg.getMsgType().ordinal()); //传递消息的类型
        byte[] bytes = msg.toBytes();
        buf.writeInt(bytes.length); //通知总共传递了多长的字符

        //消息体
        buf.writeBytes(bytes);//消息体
    }
}
