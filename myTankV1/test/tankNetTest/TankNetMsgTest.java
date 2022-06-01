package tankNetTest;

import com.licretey.tank.Direction;
import com.licretey.tank.Group;
import com.licretey.tank.Player;
import com.licretey.tank.net.MsgEncoder;
import com.licretey.tank.net.TankJoinMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TankNetMsgTest {

    @Test
    void encode(){

        EmbeddedChannel ch = new EmbeddedChannel();
        ch.pipeline().addLast(new MsgEncoder());

        Player p = new Player(50,100, Direction.R, Group.BAD);
        TankJoinMsg tjm = new TankJoinMsg(p);

        ch.writeOutbound(tjm);

        ByteBuf buf = ch.readOutbound();

        int length = buf.readInt();
        int x = buf.readInt();
        int y = buf.readInt();
        Direction dir = Direction.values()[buf.readInt()];
        boolean moving = buf.readBoolean();
        Group group = Group.values()[buf.readInt()];
        UUID id = new UUID(buf.readLong(),buf.readLong());

        assertEquals(33,length);
        assertEquals(50,x);
        assertEquals(100,y);
        assertEquals(Direction.R,dir);
        assertFalse(moving);
        assertEquals(Group.BAD,group);
        assertEquals(p.getId(),id);
    }


}
