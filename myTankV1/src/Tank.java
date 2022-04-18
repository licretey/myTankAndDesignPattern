import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 面向对象：
 *      抽象出名词：属性、类
 *      抽象出动词：方法
 */
public class Tank {
    // 位置信息
    private int x , y;
    // 速度
    private static final int SPEED = 5;
    // 方向
    private Direction dir = Direction.R;
    // 几个变量，用于记录键盘的按下状态
    private boolean bL,bR,bU,bD;
    // tank是否在移动
    private boolean moving = false;


    public Tank(int x, int y, Direction dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
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


    // 移动
    private void move() {
        if(!this.moving) return;
        switch (dir){
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }
    }

    /**
     * paint、keyPressed两种方法的核心思想就是，将操作物给对象自己去处理（键盘对象给tank，tank自己判断加减；画笔给tank，tank自己绘制位置）
     */

    // 绘制方法
    public void paint(Graphics g) {
        g.fillRect(x,y,50,50);
        this.move();//移动
    }

    // 根据按键移动
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int keyCoder = 0;
        if(keyCode==KeyEvent.VK_LEFT || keyCode==KeyEvent.VK_A){
            keyCoder = 1;
        }else if(keyCode==KeyEvent.VK_RIGHT || keyCode==KeyEvent.VK_D){
            keyCoder = 2;
        }else if(keyCode==KeyEvent.VK_UP || keyCode==KeyEvent.VK_W) {
            keyCoder = 3;
        }else if(keyCode==KeyEvent.VK_DOWN || keyCode==KeyEvent.VK_S){
            keyCoder = 4;
        }

        switch (keyCoder){
            case 1:
                this.bL = true;
                break;
            case 2:
                this.bR = true;
                break;
            case 3:
                this.bU = true;
                break;
            case 4:
                this.bD = true;
                break;
        }

        setMainDir();
    }

    private void setMainDir() {
        if(!bL && !bR && !bU && !bD){
            this.moving = false;
        }else {
            this.moving = true;
            if(bL && !bR && !bU && !bD){
                this.dir = Direction.L;
            }
            if(!bL && bR && !bU && !bD){
                this.dir = Direction.R;
            }
            if(!bL && !bR && bU && !bD){
                this.dir = Direction.U;
            }
            if(!bL && !bR && !bU && bD){
                this.dir = Direction.D;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int keyCoder = 0;
        if(keyCode==KeyEvent.VK_LEFT || keyCode==KeyEvent.VK_A){
            keyCoder = 1;
        }else if(keyCode==KeyEvent.VK_RIGHT || keyCode==KeyEvent.VK_D){
            keyCoder = 2;
        }else if(keyCode==KeyEvent.VK_UP || keyCode==KeyEvent.VK_W) {
            keyCoder = 3;
        }else if(keyCode==KeyEvent.VK_DOWN || keyCode==KeyEvent.VK_S){
            keyCoder = 4;
        }

        switch (keyCoder){
            case 1:
                this.bL = false;
                break;
            case 2:
                this.bR = false;
                break;
            case 3:
                this.bU = false;
                break;
            case 4:
                this.bD = false;
                break;
        }

        setMainDir();
    }
}
