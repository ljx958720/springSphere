package com.ghy.shardingjdbc.jdbc;

import com.ghy.shardingjdbc.entity.Student;

import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        Student student = new Student();
        try {
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
            // 打开连接
            connection = DriverManager.getConnection("jdbc:mysql://192.168.2.106:3306/ljxmycat", "root", "root");

            // 执行查询
            statement = connection.createStatement();
            String sql = "SELECT sid, name,qq FROM student where sid = 1";
            ResultSet rs = statement.executeQuery(sql);

            // 获取结果集
            while (rs.next()) {
                Integer sid = rs.getInt("sid");
                String name = rs.getString("name");
                String qq = rs.getString("qq");
                student.setSid(sid);
                student.setName(name);
                student.setQq(qq);
            }
            System.out.println(student.toString());
            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
