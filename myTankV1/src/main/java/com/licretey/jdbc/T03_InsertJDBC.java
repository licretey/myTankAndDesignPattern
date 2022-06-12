package com.licretey.jdbc;

import java.sql.*;

// postgre
public class T03_InsertJDBC {
    public static void main(String[] args) {
        try {
            //1. 加载驱动
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //创建连接
        Connection conn = null;
        //3.语句对象
        Statement statement = null;

        int minNo = 5;
        //String sql = "select * from student where limit " + minNo;
        //不安全
        String sql = "insert into student values (22222222222,'jian','jian') ";

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");
            statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (statement != null)
                    statement.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
