package com.licretey.jdbc;

import java.sql.*;

// postgre
public class T02_PrepareJDBC {
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
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        int minNo = 5;
        //String sql = "select * from student where limit " + minNo;
        //不安全
        String sql = "select * from student where id > ? " ;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");
//            statement = conn.createStatement();
            ps = conn.prepareStatement(sql);
            ps.setInt(1,minNo);//将变量设到对应位置上
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getLong("id"));
                System.out.println(resultSet.getString("name"));
                System.out.println(resultSet.getString("code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (resultSet != null)
                    resultSet.close();
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
