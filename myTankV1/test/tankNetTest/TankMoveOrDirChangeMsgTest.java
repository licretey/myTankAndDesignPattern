package tankNetTest;

import com.licretey.tank.Direction;
import com.licretey.tank.net.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TankMoveOrDirChangeMsgTest {

    @Test
    void encode(){

        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgEncoder());

        TankMoveOrDirChangeMsg tjm = new TankMoveOrDirChangeMsg(UUID.randomUUID(),50,100,Direction.D);

        ch.writeOutbound(tjm);

        ByteBuf buf = ch.readOutbound();

        MsgType msgType = MsgType.values()[buf.readInt()];
        int length = buf.readInt();


        UUID id = new UUID(buf.readLong(),buf.readLong());
        int x = buf.readInt();
        int y = buf.readInt();
        Direction dir = Direction.values()[buf.readInt()];

        assertEquals(MsgType.TankMoveOrDirChange,msgType);
        assertEquals(28,length);
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(Direction.D,dir);
    }

    @Test
    void decode(){

        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgDecoder());


        UUID id = UUID.randomUUID();
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(MsgType.TankMoveOrDirChange.ordinal());
        buf.writeInt(28);

        buf.writeLong(id.getMostSignificantBits());
        buf.writeLong(id.getLeastSignificantBits());
        buf.writeInt(5);
        buf.writeInt(8);
        buf.writeInt(Direction.D.ordinal());

        ch.writeInbound(buf);

        TankMoveOrDirChangeMsg tjm = ch.readInbound();

        assertEquals(id ,tjm.getId());
        assertEquals(5,tjm.getX());
        assertEquals(8,tjm.getY());
        assertEquals(Direction.D,tjm.getDir());
    }


}
