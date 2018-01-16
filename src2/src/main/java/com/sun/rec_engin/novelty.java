package com.sun.rec_engin;

import com.sun.dao.DisDAO;
import com.sun.dao.LikeDAO;
import com.sun.dao.NovelDAO;
import com.sun.rec_engin.handle.sort;

import java.util.*;

/**
 * Created by sunyang on 2017/5/19.
 * ����ฺ�������ӱ��,Ϊ�û��Ƽ���ȫ����صĲ���.
 */
public class novelty {
    public static Map<Integer,Double> getNovelty(int id){
        LikeDAO likedao=new LikeDAO();
        DisDAO disDao=new DisDAO();

        Map<Integer,Double> res=new HashMap<Integer, Double>();
        ArrayList<Integer> userLikeList=likedao.queryLike(id);


//        ArrayList<String> typeList= NovelDAO.getColumn("�ڵ�ID");
//        ArrayList<Integer> userTypeList=new ArrayList<Integer>();
//        /*
//        ���û���ǹ��ò������ͺŴ��ȥ
//         */
//        for (int i = 0; i < userLikeList.size(); i++) {
//            int typeid=Integer.parseInt(NovelDAO.getColumn("�������ͺ�",userLikeList.get(i)));
//            if(!userTypeList.contains(typeid)) userTypeList.add(typeid);
//        }
//        /*
//        ����һ�����е����ͺ�,���û�û�еĴ��ȥ,���е�ɾ����
//         */
//        for (int i = 0; i < typeList.size(); i++) {
//            int typeid=Integer.parseInt(typeList.get(i));
//            Iterator<String> iterator = typeList.iterator();
//            while(iterator.hasNext()){
//                int it = Integer.parseInt(iterator.next());
//                if(it == typeid){
//                    iterator.remove();
//                }
//                else userTypeList.add(typeid);
//            }
//        }

        /*
        �����������
        whileѭ���ж�,����Ѿ����û�ϲ���б����ؾ���������һ��
        ��������2���˾��˳�.
         */
        int min=0;
        int max=disDao.idlist.size();
        boolean flag=true;
        while(flag){
            int num = min + (int)(Math.random() * (max-min+1));
            int tmp=Integer.parseInt(disDao.idlist.get(num).toString());
            if (!userLikeList.contains(tmp)){
                res.put(tmp,0.0);
            }
            if(res.size()==2) flag=false;
        }

        return res;
    }
    public static List<Map.Entry<Integer,Double>> getRes(Map<Integer,Double> res){
        return sort.sortRes(res);
    }
}
