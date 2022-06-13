package nettyChat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientFrame extends Frame {
    public static final ClientFrame INSTANCE = new ClientFrame();//饿汉式单例


    private TextArea ta = new TextArea();
    private TextField tf = new TextField();

    private ChatClient client = null;

    private ClientFrame() {
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
                //updateText(tf.getText());
                tf.setText("");
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.closeConnection();
                System.exit(0);
            }
        });
    }


    public static void main(String[] args) {
        ClientFrame frame = ClientFrame.INSTANCE;
        frame.setVisible(true);
        frame.connectToServer();
    }

    // 连接Server
    public void connectToServer(){
        client = new ChatClient();
        client.connect();
    }

    //更新界面上的信息
    public void updateText(String str) {
        ta.setText(ta.getText()+str+"\r\n");
    }
}
