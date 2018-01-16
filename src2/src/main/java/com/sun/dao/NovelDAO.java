package com.sun.dao;

import com.sun.dbm.Dbmanage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by sunyang on 2017/5/19.
 *�������������ص�
 *
 */
public class NovelDAO {
    /*
    �õ�ȫ����ĳһ��
     */
    public static ArrayList<String> getColumn(String column){
        ArrayList<String> result = new ArrayList<String>();


        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `������`.`"+column+"` FROM `������`";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                //Ϊ��ҲҪ��
                result.add(handle);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }
    /*
    �õ�ĳ������ĳһ��,idΪ����id
    */
    public static String getColumn(String column,int id){
        String result = null;
        DisDAO disDAO=new DisDAO();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `����`.`"+column+"` FROM `����`WHERE `����`.`����ID`=" + id;
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                if (handle != null) {//ע����Ϊ�յ����
                    result=handle;
                } else {
                    System.out.println("null");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // ִ����ر����ݿ�
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }

}
