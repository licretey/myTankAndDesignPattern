package com.licretey.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncoder extends MessageToByteEncoder<TankJoinMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankJoinMsg msg, ByteBuf buf) throws Exception {
        byte[] bytes = msg.boBytes();
        buf.writeInt(bytes.length); //通知总共传递了多长的字符
        buf.writeBytes(bytes);//消息体
    }
}
