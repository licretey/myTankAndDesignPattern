package com.licretey.jdbc;

import java.sql.*;

// postgre
public class T01_HelloJDBC {
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
        ResultSet resultSet = null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123456");

            statement = conn.createStatement();

            String sql = "select * from student";
            resultSet = statement.executeQuery(sql);

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
