[TOC]

## 一 项目介绍
基于B站马士兵老师的坦克大战实战课程的代码笔记

## 二 项目解构
本项目基于一个个的知识点创建一个个的分支，具体的分支对应具体的处理逻辑，如下

+ addImage 方块替换为图片
+ addBullet 监听ctl添加子弹,通过引用的方式在frame上添加
+ addBullet2 使用单例模式添加子弹
+ addBullet3 使用list添加多个子弹
+ borderChecking 子弹边界检查
+ addBomb 碰撞检测
+ enmeyMove  添加敌人移动1: 拆分敌人tank与操作tank
+ enmeyMove2 添加敌人移动2: enmey移动并发射子弹
+ enmeyMove3 添加敌人移动3: 处理敌人更新方向与开火太频繁问题
+ tankBorderCheck tank的边界检查 并添加多个敌人
+ addBomb2 添加爆炸特效
+ addVoice 添加背景音乐
+ reConsitution 重构
  + 配置文件让项目更灵活
  + 使用策略模式（多态）添加开火模式
  + 配合配置文件控制使用何种开火模式
+ reConsitution2 重构2
  + 添加物体的方法会重复写（只是参数不同）：设计一个抽象物体类
  > 什么时候使用接口，什么时候使用抽象类
  > 
  > 1.都行（看哪种方式更合适）
  > 
  > 2.一般物体确实存在使用抽象类，抽象的动作等用接口
  > 
  > （接口里的都是public final static的）
+ reConsitution3 重构3：抽象物体对象后的碰撞检测（使用责任链模式：未引入责任链中的list）
+ reConsitution4 重构4：list封装碰撞检测（bug:一颗子弹在爆炸后且未重绘前，会隐藏存在着去碰撞墙，因为碰撞处未删除对象）
+ reConsitution5 重构5：结合3，4（真正的责任链模式）
+ reConsitution6 添加一个新的碰撞检测
+ modelAndView model、view的分离（MVC、门面者facade模式、调停者Mediator模式）
+ serializableInterface 序列化
> 序列化：将内存中的多个对象数据，一同写到磁盘或网络上
> 
> transient 修饰的变量不参与序列化（会根据类型赋上默认值，实际不存在）
> 
> 序列化对象中包含引用对象时，引用对象的类也需要实现Serializable

**网络版**

#### 1.网络模型基础

> 网络模型
> 
> 1.编程模型
> + TCP：可靠，速度慢
> + UDP：不可靠，速度快
> 
> 2.BIO：阻塞式IO，基础网络模型（存再ddos攻击风险）
> 
> 3.NIO：非阻塞式IO（Netty封装了NIO）
> 
> 轮询处理请求和读写请求中的数据
> 
> 4.AIO：异步IO（仅win支持）
> 
> 

#### 2.Netty

+ [代码参考](github.com/bjmashibing/NettyStudy)

+ netty Netty的基本开发流程
+ nettyChat 实现客户界面并向Server发送消息后，能返回消息并解码读取
+ nettyCha2 处理多个客户端信息的管理、关闭通知与异常处理
+ encoderDecoder 协议底层编码解码，Netty单元测试
+ tankAndNet 初步联网

> 当前大致流程如下
>
>![img.png](sources/reademeImgs/img.png)

+ commonMsgEnAndDecoder 封装消息体父类，抽象消息体的公共方法
+ tankStartMovingMsg 添加实现了共有方法的tank移动消息体
+ tankStartMovingMsg2 实现hanlde方法，处理操作对象的网络同步
+ tankStopMsg 实现tank停止发送同步消息
+ TankMoveOrDirChange 移动和更换方向时都发送消息
+ BulletNewMsg 同步发射子弹的消息
+ tankDieMsg 同步发射爆炸死亡的消息

## 三 其它
+ slf4j slf4j日志框架
+ jdbc jdbc基础
+ jvm  jvm基础

> 1.classloader过程
> 
> 1.1 loading
> 
> 1.2 linking：校验、预处理，设置静态变量的默认值、确定多态中的具体实现位置
> 
> 1.3 initializing：设置静态变量的初始值



