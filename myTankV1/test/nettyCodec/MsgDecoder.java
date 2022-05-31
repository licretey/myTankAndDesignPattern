package nettyCodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()<8) return;//坐标数据的长度为8

        //先写先读
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();

        list.add(new TankMsg(x,y));
    }
}
