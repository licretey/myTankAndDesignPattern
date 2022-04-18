import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankFrame extends Frame {
    Tank tempTank;

    public TankFrame(){
        this.setTitle("tank war");
        this.setLocation(400,100); // 相对于屏幕window
        this.setSize(800,600);

        // 添加键盘监听
        this.addKeyListener(new TankKeyListener());

        // 抽象到tank类中
        this.tempTank = new Tank(100,100, Direction.D);
    }

    // awt自动调用
    // Graphics由系统提供
    @Override
    public void paint(Graphics g) {
        // 绘制方块（x,y相对于窗口）
        // 让这个方块动起来，就需要传入动态的x ，y坐标，并且不停的调用paint绘制（如下）
        //        g.fillRect(x,y,50,50);
        tempTank.paint(g);//x，y由局部变量抽出到一个对象中，这个对象自己去绘制
    }

    private class TankKeyListener extends KeyAdapter {
        // KeyAdapter 以空方法的形式实现许多KeyListener接口的方法
        @Override
        public void keyPressed(KeyEvent e) {
            // 如果创建了tank对象，对键盘的监听也交由这个对象自己来完成
            tempTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 如果创建了tank对象，对键盘的监听也交由这个对象自己来完成
            tempTank.keyReleased(e);
        }
    }
}
