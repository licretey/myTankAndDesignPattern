package nettyChat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientFrame extends Frame {
    private TextArea ta = new TextArea();
    private TextField tf = new TextField();

    private NettyClient client = null;

    public ClientFrame() {
        this.setSize(300,400);
        this.setLocation(400,20);
        this.add(ta,BorderLayout.CENTER);
        this.add(tf,BorderLayout.SOUTH);

        this.setTitle("licreteyChat");

        tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //发送消息
                client.send(tf.getText());
                //写道当前界面
                ta.setText(ta.getText() + tf.getText() + "\r\n");
                tf.setText("");
            }
        });
    }


    public static void main(String[] args) {
        ClientFrame frame = new ClientFrame();
        frame.setVisible(true);
        frame.connectToServer();
    }

    // 连接Server
    public void connectToServer(){
        client = new NettyClient();
        client.connect();
    }
}
