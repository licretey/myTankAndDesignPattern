package com.licretey.tank.net;

// 抽象消息父类，所有消息需要继承实现父类中的如下方法
public abstract class Msg {
    public abstract byte[] toBytes(); //转为字节数组

    public abstract void parse(byte[] bytes);//解析字节数组

    public abstract void handle();//处理方法

    //消息协议：一种标志代表一类消息做对应的处理
    public abstract MsgType getMsgType();
}
