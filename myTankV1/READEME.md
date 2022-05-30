[TOC]

## 一 项目介绍
基于B站马士兵老师的tank实战课程的代码笔记

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
+ 重构3：抽象物体对象后的碰撞检测（使用责任链模式）
+ 

