package com.sun.dao;

import com.sun.dbm.Dbmanage;

import com.sun.vo.UserVo;

import java.sql.*;

/**
 * Created by sunyang on 2016/12/8.
 * �����������ݿ���û����йصĲ���
 */
public class UserDAO {
    public void insertUser(UserVo user) {
        // �û�ע�᷽��
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;

        try {
            conn = dbmanage.initDB();

                sta = conn.createStatement();

//            sta2 = conn.createStatement();
            System.out.println("Created statement...");
            System.out.println(user.getUserName());
            System.out.println(user.getUserPassword());


            String sql = "INSERT INTO userTable (user_name,user_password)VALUES('"
                    + user.getUserName()
                    + "','"
                    + user.getUserPassword()
                    + "');";
            System.out.println(sql);
            sta.executeUpdate(sql);


            //sta.execute(sql);
        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            if (conn != null) {
                dbmanage.closeDBWithoutres(sta, conn);
            }


        }
    }
    public UserVo judgeUserPassword(String userName, String userPassword) {
        // �û���¼��֤
        Dbmanage dbmanage = new Dbmanage();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        UserVo user = null;
        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            System.out.println(userName);
            System.out.println(userPassword);
            String sql = "SELECT * FROM userTable WHERE user_name = '"
                    + userName + "' AND user_password= '" + userPassword + "'";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                user = new UserVo();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPassword(rs.getString("user_password"));

            }

        } catch (SQLException e) {

            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        // ���ز�ѯ���
        return user;
    }
}
