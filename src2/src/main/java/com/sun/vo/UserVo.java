package com.sun.vo;

import java.util.ArrayList;

/**
 * Created by sunyang on 2016/12/8.
 */
public class UserVo {
    private int userId;
    // �û�Id
    private String userName ;
    // �û�����
    private String userPassword;
    // �û�����
    private static ArrayList<Integer> like=new ArrayList<Integer>();//����û�ϲ���Ĳ��׵�id

    public ArrayList<Integer> getLike() {
        return like;
    }

    public void setLike(ArrayList<Integer> like) {
        this.like = like;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
