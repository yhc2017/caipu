package com.sun.vo;

import java.util.ArrayList;

/**
 * Created by sunyang on 2016/12/8.
 * ������¼�Ѿ���½���û�,�����ɾ�̬���͵Ķ�̬����,�������û��б�
 */
public class UserListVector {
    private static ArrayList<UserVo> list=new ArrayList<UserVo>();

    public static void addUser(UserVo user){
        list.add(user);
    }

    public static ArrayList<UserVo> getList() {
        return list;
    }
}
