package com.licretey.tank.net.msg;

import com.licretey.tank.Direction;
import com.licretey.tank.Tank;
import com.licretey.tank.TankFrame;
import com.licretey.tank.net.Msg;
import com.licretey.tank.net.MsgType;

import java.io.*;
import java.util.Objects;
import java.util.UUID;

public class TankMoveOrDirChangeMsg extends Msg {
    private UUID id;
    private int x,y;
    private Direction dir;

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            dos.writeLong(id.getMostSignificantBits());//高位
            dos.writeLong(id.getLeastSignificantBits());//低位
            dos.writeInt(x);
            dos.writeInt(y);
            //枚举看作一个数组时，ordinal获取位于数组上的下标
            dos.writeInt(dir.ordinal());
            //uuid是两个long组合而成，所以分开写处理
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
            this.id = new UUID(dis.readLong(),dis.readLong());
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Direction.values()[dis.readInt()];
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
        //client向server发送消息后，server会向所有client发送一遍
        //TankClient中读取server发出的消息，交由handle处理
        if(this.id.equals(TankFrame.SINGLE_FRAME.getGm().getPlayer().getId())) return; //是自己不处理

        Tank tank = TankFrame.SINGLE_FRAME.getGm().findTankByUUID(this.id);
        if(!Objects.isNull(tank)){
            tank.setMoving(true);
            tank.setX(this.x);
            tank.setY(this.y);
            tank.setDir(this.dir);
        }
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankMoveOrDirChange;
    }

    public TankMoveOrDirChangeMsg(UUID id, int x, int y, Direction dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    //反射的时候使用
    public TankMoveOrDirChangeMsg() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "TankMoveOrDirChangeMsg{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                '}';
    }
}
