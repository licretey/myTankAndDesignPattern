package com.licretey.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        if(buf.readableBytes()<37) return;//坐标数据的长度为8

        int length = buf.readInt();//length已经被读取，读指针已移动
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        TankJoinMsg tjm = new TankJoinMsg();
        tjm.parse(bytes);

        list.add(tjm);
    }
}
