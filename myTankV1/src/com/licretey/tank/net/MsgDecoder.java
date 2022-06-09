package com.licretey.tank.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        if(buf.readableBytes()<8) return;//消息头都不够
        buf.markReaderIndex();//记录读取位置

        MsgType msgType = MsgType.values()[buf.readInt()];//确定何种消息
        int length = buf.readInt();//length已经被读取，读指针已移动

        if(buf.readableBytes()<length){
            //一次传输只接收了部分消息，复位读指针等待接收完全的消息
            buf.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        Msg msg = (Msg) Class.forName("com.licretey.tank.net."+msgType.toString()+"Msg")
                .getDeclaredConstructor()
                .newInstance();
        msg.parse(bytes);
//        switch (msgType){
//            case TankJoin:
//                msg = new TankJoinMsg();
//                msg.parse(bytes);
//                break;
//            case TankStartMoving:
//                msg = new TankStartMovingMsg();
//                break;
//        }

        list.add(msg);
    }
}
