import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        TankFrame tf = new TankFrame();
        tf.setVisible(true);

        while (true){
            try {
                TimeUnit.MICROSECONDS.sleep(10000);//sleep的另一种写法
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tf.repaint(); // repaint()会调用paint()
        }

    }
}
