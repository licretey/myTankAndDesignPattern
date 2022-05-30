import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

public class SerializeTest {
    //序列化测试

    @Test
    public void testSave() {
        try {
            T t = new T();
            File file = new File("C:/Users/myTank.dat");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //将对象写到文件中去
            oos.writeObject(t);
            oos.flush();
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoad() {
        try {
            File file = new File("C:/Users/myTank.dat");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            //将对象写到文件中去
            T t = (T)ois.readObject();
            Assertions.assertEquals(8,t.n);
            Assertions.assertEquals(4,t.m);
//            System.out.println(t.m+"  "+t.n);

            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

// 要序列化的对象，需要实现Serializable接口
class T implements Serializable {
    int m = 4;
    // transient 修饰的变量不参与序列化
    transient int n = 8;
    Apple apple = new Apple();//序列化对象中包含引用对象时，引用对象的类也需要实现Serializable
}

class Apple implements Serializable {
    int weight = 7;
}