package nettyUnitTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nettyCodec.MsgDecoder;
import nettyCodec.MsgEncoder;
import nettyCodec.TankMsg;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Netty提供了一个本地模拟的假的channel用于网络收发的测试
//Netty编程务必单元测试
public class NettyUnitTest {

    @Test
    void decode(){
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgDecoder());

        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(5);
        buf.writeInt(9);

        ch.writeInbound(buf);
        TankMsg tm = ch.readInbound();


        assertEquals(5,tm.x);
        assertEquals(9,tm.y);
    }

    @Test
    void encode(){
        EmbeddedChannel ch = new EmbeddedChannel();

        ch.pipeline().addLast(new MsgEncoder());
        TankMsg tankMsg = new TankMsg(5,9);
        ch.writeOutbound(tankMsg);

        ByteBuf buf = ch.readOutbound();

        int x = buf.readInt();
        int y = buf.readInt();
        assertEquals(5,x);
        assertEquals(9,y);
    }
}
