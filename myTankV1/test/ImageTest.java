import com.licretey.tank.ImageUtil;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ImageTest {
    // 基于junit的单元测试

    @Test
    public void testLoadImage(){
        try {
            // 绝对路径方式
            BufferedImage image = ImageIO.read(new File("D:\\code\\myTank\\myTankV1\\src\\images\\bulletD.gif"));
            assertNotNull(image);
            // 相对路径方式
            BufferedImage image2 = ImageIO.read(Objects.requireNonNull(ImageTest.class.getClassLoader().getResourceAsStream("images/bulletD.gif")));
            assertNotNull(image2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRotateImage(){
        try {
            // 绝对路径方式
            BufferedImage image = ImageIO.read(new File("D:\\code\\myTank\\myTankV1\\src\\images\\bulletD.gif"));
            image = ImageUtil.rotateImage(image,90);
            assertNotNull(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
