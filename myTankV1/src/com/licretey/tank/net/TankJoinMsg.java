package com.licretey.tank.net;

import com.licretey.tank.Direction;
import com.licretey.tank.Group;
import com.licretey.tank.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;


public class TankJoinMsg {
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

   public byte[] boBytes(){
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
}
