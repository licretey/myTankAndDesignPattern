package com.licretey.tank.net.msg;

import com.licretey.tank.Bullet;
import com.licretey.tank.Direction;
import com.licretey.tank.Group;
import com.licretey.tank.TankFrame;
import com.licretey.tank.net.Msg;
import com.licretey.tank.net.MsgType;

import java.awt.*;
import java.io.*;
import java.util.UUID;

public class BulletNewMsg extends Msg {
    private UUID playerId;//开火者
    //子弹本身id
    private UUID id;
    private int x,y;
    private Direction dir;
    private Group group;

    public BulletNewMsg() {

    }

    public BulletNewMsg(Bullet bullet) {
        this.playerId = bullet.getPlayerId();
        this.id = bullet.getId();
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
    }


    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            //playerID
            dos.writeLong(playerId.getMostSignificantBits());//高位
            dos.writeLong(playerId.getLeastSignificantBits());//低位
            //bullet id
            dos.writeLong(id.getMostSignificantBits());//高位
            dos.writeLong(id.getLeastSignificantBits());//低位

            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());

            dos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos!=null){
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if(dos!=null){
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));

        try{
            this.playerId = new UUID(dis.readLong(),dis.readLong());
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Direction.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handle() {
        //自己发的不处理
        if(this.playerId.equals(TankFrame.SINGLE_FRAME.getGm().getPlayer().getId())) return;

        //添加并绘制子弹
        Bullet bullet = new Bullet(this.playerId,x,y,dir,group);
        bullet.setId(this.id);
        TankFrame.SINGLE_FRAME.getGm().add(bullet);
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "playerId=" + playerId +
                ", id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                '}';
    }
}
