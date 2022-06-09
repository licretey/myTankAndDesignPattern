package com.licretey.tank.net;

import com.licretey.tank.*;

import java.io.*;
import java.util.UUID;


public class TankJoinMsg extends Msg{
    private int x,y;
    private Direction dir;
    private boolean moving;
    private Group group;
    private UUID id;//分配一个id

    public TankJoinMsg() {
    }

    public TankJoinMsg(Player player) {
        this.x = player.getX();
        this.y = player.getY();
        this.dir = player.getDir();
        this.moving = player.isMoving();
        this.group = player.getGroup();
        this.id = player.getId();
    }

    @Override
    public byte[] toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

       try {
           baos = new ByteArrayOutputStream();
           dos = new DataOutputStream(baos);
           dos.writeInt(x);
           dos.writeInt(y);
           //枚举看作一个数组时，ordinal获取位于数组上的下标
           dos.writeInt(dir.ordinal());
           dos.writeBoolean(moving);
           dos.writeInt(group.ordinal());
           //uuid是两个long组合而成，所以分开写处理
           dos.writeLong(id.getMostSignificantBits());//高位
           dos.writeLong(id.getLeastSignificantBits());//低位
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
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Direction.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(),dis.readLong());
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
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", id=" + id +
                '}';
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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public void handle() {
        if(this.id.equals(TankFrame.SINGLE_FRAME.getGm().getPlayer().getId())) return; //是自己
        if(TankFrame.SINGLE_FRAME.getGm().findTankByUUID(this.id) != null) return;//已存在的其它对象

        //添加tank
        Tank tank = new Tank(this);
        TankFrame.SINGLE_FRAME.getGm().add(tank);

        //有新人加入，自己发送下通知对方自己的存在
        TankClient.INSTANCE.send(new TankJoinMsg(TankFrame.SINGLE_FRAME.getGm().getPlayer()));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankJoin;
    }
}
