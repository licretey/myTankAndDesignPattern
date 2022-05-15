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