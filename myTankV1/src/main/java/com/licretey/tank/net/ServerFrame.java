package com.licretey.tank.net;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerFrame extends Frame {
    public static final ServerFrame INSTANCE = new ServerFrame();

    private TextArea taServer = new TextArea();
    private TextArea taClient = new TextArea();

    private TankServer server = new TankServer();

    private ServerFrame(){
        this.setTitle("Tank Server");
        this.setSize(800,600);
        this.setLocation(300,30);
        Panel p = new Panel(new GridLayout(1,2));
        p.add(taServer);
        p.add(taClient);
        this.add(p);

        taServer.setFont(new Font("consolas",Font.BOLD,25));
        taClient.setFont(new Font("consolas",Font.BOLD,25));
        this.updateServerMsg("Server: ");
        this.updateClientMsg("Client: ");

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public void updateClientMsg(String text){
        String lineSplitChar = System.getProperty("line.separator");
        this.taClient.setText(taClient.getText()+text+ lineSplitChar);
    }

    public void updateServerMsg(String text){
        String lineSplitChar = System.getProperty("line.separator");
        this.taServer.setText(taServer.getText()+text+lineSplitChar);
    }

    public static void main(String[] args) {
        ServerFrame.INSTANCE.setVisible(true);
        ServerFrame.INSTANCE.server.serverStart();//启动server
    }
}
