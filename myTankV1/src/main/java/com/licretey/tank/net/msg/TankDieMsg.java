package com.licretey.tank.net.msg;

import com.licretey.tank.*;
import com.licretey.tank.net.Msg;
import com.licretey.tank.net.MsgType;
import com.licretey.tank.net.TankClient;

import java.io.*;
import java.util.Objects;
import java.util.UUID;


public class TankDieMsg extends Msg {
    private UUID id;       // tank id
    private UUID bulletId; // bulle id

    public TankDieMsg() {
    }

    public UUID getBulletId() {
        return bulletId;
    }

    public void setBulletId(UUID bulletId) {
        this.bulletId = bulletId;
    }

    public TankDieMsg(UUID id, UUID bulletId) {
        this.id = id;
        this.bulletId = bulletId;
    }

    @Override
    public byte[] toBytes(){
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;

       try {
           baos = new ByteArrayOutputStream();
           dos = new DataOutputStream(baos);

           //uuid是两个long组合而成，所以分开写处理
           dos.writeLong(id.getMostSignificantBits());//高位
           dos.writeLong(id.getLeastSignificantBits());//低位

           //子弹id
           dos.writeLong(bulletId.getMostSignificantBits());//高位
           dos.writeLong(bulletId.getLeastSignificantBits());//低位

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
            this.bulletId = new UUID(dis.readLong(),dis.readLong());
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
        return "TankDieMsg{" +
                "id=" + id +
                ", bulletId=" + bulletId +
                '}';
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public void handle() {
        //client向server发送消息后，server会向所有client发送一遍
        //TankClient中读取server发出的消息，交由handle处理
        Bullet bullet = TankFrame.SINGLE_FRAME.getGm().findBulletByUUID(bulletId);
        if(!Objects.isNull(bullet)) bullet.die();

        Tank tank = TankFrame.SINGLE_FRAME.getGm().findTankByUUID(this.id);
        if(this.id.equals(TankFrame.SINGLE_FRAME.getGm().getPlayer().getId())){
            TankFrame.SINGLE_FRAME.getGm().getPlayer().die();
        } else {
            if(!Objects.isNull(tank)) tank.die();
        }

        //有新人加入，自己发送下通知对方自己的存在
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }
}
