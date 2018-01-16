package com.sun.dao;

import com.sun.dbm.Dbmanage;
import com.sun.rec_engin.map_mix;
import com.sun.rec_engin.similarity;

import com.sun.rec_engin.split;
import com.sun.vo.RecipeVo;
import com.sun.vo.UserListVector;
import com.sun.vo.UserVo;
import org.ansj.recognition.impl.StopRecognition;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
     * Created by sunyang on 2016/12/8.
 * 杩欎釜绫荤敤浜庡鐞嗚绠楃浉浼煎害鏃堕渶瑕佽繛鎺ユ暟鎹簱鐨勬搷浣�
 */
public class SimDAO {
    public Map<String, double[]> getUserVector(int id,String column,Set<String> expectedNature,StopRecognition filter) {//寰楀埌鐢ㄦ埛鐨勫悜閲忕┖闂�
        map_mix mix=new map_mix();
        similarity sim=new similarity();
        LikeDAO likeDAO=new LikeDAO();
        ArrayList<String> result=new ArrayList<String>();
        ArrayList<Integer> userLikeList=likeDAO.queryLike(id);
        Map<String, double[]> vectorSpace = new HashMap<String, double[]>();
        split sw=new split();

        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();

        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            for (int i = 0; i < userLikeList.size(); i++) {
                String sql = "SELECT `鑿滆氨`.`"+column+"` FROM `鑿滆氨` WHERE `鑿滆氨`.`鑿滆氨ID`="+userLikeList.get(i);
                rs = sta.executeQuery(sql);
                while (rs.next()) {
                    String handle = rs.getString(column);

                    if(handle!=null){//娉ㄦ剰鏈変负绌虹殑鎯呭喌
//                        sw.splitWord(handle);
//                        result=sw.splitWordtoArrDelUseless(handle);
                        if(filter!=null){
                            result=sw.getFilterWord(handle,expectedNature,filter);
                        }else{
                            result=sw.getFilterWord(handle,expectedNature);
                        }
                        mix.mapMix(vectorSpace,sim.getVector(result,0));
                    }else{
                        System.out.println("null");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 鎵ц瀹屽叧闂暟鎹簱
            dbmanage.closeDB(rs, sta, conn);
        }
        return vectorSpace;
    }

    public Map<String, double[]> getRecipeVector(int i,String column,Set<String> expectedNature,StopRecognition filter) {//寰楀埌鏌愪釜鑿滆氨鐨勫悜閲忕┖闂�i涓虹鍑犱釜鑿滆氨锛屽苟闈炶彍璋眎d
        similarity sim=new similarity();
        ArrayList<String> result = new ArrayList<String>();

        split sw = new split();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();
        DisDAO disDAO=new DisDAO();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `鑿滆氨`.`"+column+"` FROM `鑿滆氨` WHERE `鑿滆氨`.`鑿滆氨ID`=" + disDAO.idlist.get(i);
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                if (handle != null) {//娉ㄦ剰鏈変负绌虹殑鎯呭喌
//                    sw.splitWord(handle);
//                    result = sw.splitWordtoArrDelUseless(handle);
                    if(filter!=null){
                        result=sw.getFilterWord(handle,expectedNature,filter);
                    }else{
                        result=sw.getFilterWord(handle,expectedNature);
                    }
                } else {
                    System.out.println("null");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 鎵ц瀹屽叧闂暟鎹簱
            dbmanage.closeDB(rs, sta, conn);
        }
        return sim.getVector(result,1);
    }

    /*
    寰楀埌鏌愪釜鑿滆氨鏌愪竴鍒�id涓虹鍑犱釜
     */
    public String getColumn(String column,int id){
        String result = null;
        DisDAO disDAO=new DisDAO();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `鑿滆氨`.`"+column+"` FROM `鑿滆氨`WHERE `鑿滆氨`.`鑿滆氨ID`=" + disDAO.idlist.get(id);
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                if (handle != null) {//娉ㄦ剰鏈変负绌虹殑鎯呭喌
                    result=handle;
                } else {
                    System.out.println("null");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 鎵ц瀹屽叧闂暟鎹簱
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }
    /*
    寰楀埌鍏ㄩ儴鑿滆氨鐨勬煇涓�垪
     */
    public ArrayList<String> getColumn(String column){
        ArrayList<String> result = new ArrayList<String>();


        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `鑿滆氨`.`"+column+"` FROM `鑿滆氨`";
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                //涓虹┖涔熻绌�                result.add(handle);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 鎵ц瀹屽叧闂暟鎹簱
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }
    /*
    寰楀埌鏌愪釜鑿滆氨鏌愪竴鍒�id涓鸿彍璋眎d
     */
    public String getColumnWithId(String column,int id){
        String result = null;
        DisDAO disDAO=new DisDAO();
        Connection conn = null;
        Statement sta = null;
        ResultSet rs = null;
        Dbmanage dbmanage = new Dbmanage();


        try {
            conn = dbmanage.initDB();
            sta = conn.createStatement();
            String sql = "SELECT `鑿滆氨`.`"+column+"` FROM `鑿滆氨`WHERE `鑿滆氨`.`鑿滆氨ID`=" + id;
            rs = sta.executeQuery(sql);
            while (rs.next()) {
                String handle = rs.getString(column);
                if (handle != null) {//娉ㄦ剰鏈変负绌虹殑鎯呭喌
                    result=handle;
                } else {
                    System.out.println("null");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 鎵ц瀹屽叧闂暟鎹簱
            dbmanage.closeDB(rs, sta, conn);
        }
        return result;
    }
}
