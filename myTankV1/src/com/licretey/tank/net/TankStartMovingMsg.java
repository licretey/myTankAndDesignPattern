package com.licretey.tank.net;

import com.licretey.tank.Direction;
import com.licretey.tank.Group;

import java.io.*;
import java.util.UUID;

public class TankStartMovingMsg extends Msg{
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

    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankStartMoving;
    }

    public TankStartMovingMsg(UUID id, int x, int y, Direction dir) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    //反射的时候使用
    public TankStartMovingMsg() {
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
}
