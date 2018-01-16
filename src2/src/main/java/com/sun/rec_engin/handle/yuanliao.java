package com.sun.rec_engin.handle;

import com.sun.dao.DisDAO;
import com.sun.dao.LikeDAO;
import com.sun.dao.SimDAO;
import com.sun.rec_engin.map_mix;
import com.sun.rec_engin.similarity;
import org.ansj.recognition.impl.StopRecognition;

import java.util.*;

/**
 * Created by sunyang on 2017/3/3.
 */
public class yuanliao {
    /*
    ������ԭ���еĴ���
     */
    public Map<Integer,Double> getSimList(int id){
        long startTime = System.currentTimeMillis();   //��ȡ��ʼʱ��

        DisDAO disDao=new DisDAO();
        SimDAO simdao=new SimDAO();
        similarity sim=new similarity();
        LikeDAO likedao=new LikeDAO();
        map_mix mix=new map_mix();
        String queue="ԭ��";//��Ҫ���е�����
        /*
        ��Ҫ�Ĵ���
         */
//        Set<String> expectedNature = new HashSet<String>() {{
//            add("n");add("v");add("nw");
//        }};
        Set<String> expectedNature = new HashSet<String>() {{
            add("n");add("nr");add("ns");add("nz");add("nl");add("ng");add("nw");
        }};
        /*
        ԭ������Ҫ�������Ļ��ͼ�
         */
        StopRecognition filter = new StopRecognition();//ʵ�û�������,��ӹ��˴�
        filter.insertStopWords("��");
        filter.insertStopWords("��");
        filter.insertStopWords("����");


        ArrayList<Integer> userLikeList=likedao.queryLike(id);

        Map<String, double[]> vec1=simdao.getUserVector(id,queue,expectedNature,filter);
        Map<Integer,Double> simList = new HashMap<Integer,Double>();//��ż��������ֵ

        for (int i = 0; i < DisDAO.idlist.size(); i++) {//�������޸��ܹ��Աȵ�����
            if(!(userLikeList.contains(disDao.idlist.get(i)))){//ȥ���Ѿ���ǹ�ϲ���Ĳ��ס�
                Map<String, double[]> vecSpa = new HashMap<String, double[]>();
                /*
                ��ӣ�����ֱ����=��Ĭ����ǳ����ָ��ͳһ���á�

                ��ô���ڶ�ָ��һ������
                �����ں���Ѽ��������ռ���ټ�ȥ�ˣ�ֻ�������ˡ�
                 */
                Map<String, double[]> vec2=simdao.getRecipeVector(i,queue,expectedNature,filter);
                vecSpa=mix.mapMix(vec1,vec2);
                double res=sim.countCos(vecSpa);
                if(!Double.isNaN(res)) {
                    simList.put((Integer) disDao.idlist.get(i),res);
                }
                else simList.put((Integer) disDao.idlist.get(i),0.0);
                System.out.println(res);
                vec1=mix.mapRemove(vec1,vec2);
            }
        }
//        /*
//        ��map��������ȡǰʮ
//        */
//        List<Map.Entry<Integer,Double>> infoIds = new ArrayList<Map.Entry<Integer,Double>>(simList.entrySet());
//        //��value��������
////        Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Double>>() {
////            @Override
////            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
////                return (o2.getValue()).toString().compareTo(o1.getValue().toString());
////            }
////
////        });
//        Collections.sort(infoIds, new Comparator<Map.Entry<Integer, Double>>() {
//            public int compare(Map.Entry<Integer, Double> o1, Map.Entry<Integer, Double> o2) {
//                if ((o2.getValue() - o1.getValue()) > 0)
//                    return 1;
//                else if ((o2.getValue() - o1.getValue()) == 0)
//                    return 0;
//                else
//                    return -1;
//            }
//        });

        long endTime=System.currentTimeMillis(); //��ȡ����ʱ��
        System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ms");

        return simList;

    }
    /*
    ���򵥶�дһ����������
    */
    public static List<Map.Entry<Integer,Double>> countSim(Map<Integer,Double> simList){
        return sort.sortRes(simList);
    }

}
