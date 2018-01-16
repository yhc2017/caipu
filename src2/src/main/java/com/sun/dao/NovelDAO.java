package com.sun.dao;

import com.sun.dbm.Dbmanage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by sunyang on 2017/5/19.
 *处理和类型树相关的
 *
 */
public class NovelDAO {
    /*
    得到全部的某一列
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
            String sql = "SELECT `类型树`.`"+column+"` FROM `类型树`";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                //为空也要穿
                result.add(handle);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }
    /*
    得到某个菜谱某一列,id为菜谱id
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
            String sql = "SELECT `菜谱`.`"+column+"` FROM `菜谱`WHERE `菜谱`.`菜谱ID`=" + id;
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                if (handle != null) {//注意有为空的情况
                    result=handle;
                } else {
                    System.out.println("null");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 执行完关闭数据库
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }

}
