package tankNetTest;

import com.licretey.tank.Direction;
import com.licretey.tank.net.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankStopMsgTest {

    @Test
    void encode(){

        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgEncoder());

        TankStopMsg tjm = new TankStopMsg(UUID.randomUUID(),50,100);

        ch.writeOutbound(tjm);

        ByteBuf buf = ch.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();


        UUID id = new UUID(buf.readLong(),buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();

        assertEquals(MsgType.TankStop,msgType);
        assertEquals(24,length);
        assertEquals(50,x);
        assertEquals(100,y);
    }

    @Test
    void decode(){

        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());


        UUID id = UUID.randomUUID();
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankStop.ordinal());
        buf.writeInt(24);

        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);

        ch.writeInbound(buf);

        TankStopMsg tjm = ch.readInbound();

        assertEquals(id ,tjm.getId());
        assertEquals(5,tjm.getX());
        assertEquals(8,tjm.getY());
    }


}
