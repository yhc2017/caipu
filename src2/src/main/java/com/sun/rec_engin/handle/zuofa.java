package com.sun.rec_engin.handle;

import com.sun.dao.DisDAO;
import com.sun.dao.LikeDAO;
import com.sun.dao.SimDAO;
import com.sun.rec_engin.*;
import org.ansj.recognition.impl.StopRecognition;

import java.util.*;

/**
 * Created by sunyang on 2017/4/27.
 */
public class zuofa {
    static SimDAO simDao=new SimDAO();
    static DisDAO disDao=new DisDAO();
    static ArrayList<ArrayList<String>> docs=new ArrayList<ArrayList<String>>();
    static Set<String> expectedNature = new HashSet<String>() {{
        add("Ag");add("a");add("ad");add("an");add("Bg");add("b");add("d");add("i");
        add("j");add("n");add("nr");add("ns");add("nt");add("nx");add("nz");add("v");
        add("vd");add("vn");
    }};
    /*
    �����洢,���еĲ���������,�ִʽ��
     */
    static Map<Integer,ArrayList<String>> map=new HashMap<Integer, ArrayList<String>>();
    /*
    �����洢���в��׵�������,�ִʵ�,tf-idfֵ
     */
    public static Map<Integer,Map<String,Double>> tfidfmap=new HashMap<Integer, Map<String, Double>>();
    static {

        StopRecognition filter=null;
        ArrayList<String> res=new ArrayList<String>();
        split sw=new split();

        /*
        ��ȡȫ���ĵ��ķִ�
         */
        res=simDao.getColumn("����");
        for (int i = 0; i < res.size(); i++) {
            ArrayList<String> document=new ArrayList<String>();
            /*
            ����õ���Ϊ��,�͸�����һ��{null},Ҳ���ǽ�����null�Ķ�̬����
             */
            if(res.get(i)!=null){
                document=sw.getFilterWord(res.get(i),expectedNature);
            }else document.add(null);
            map.put(Integer.parseInt(disDao.idlist.get(i).toString()),document);
            docs.add(document);
        }

    }


    public static Map<String,Double> getTFIDF(ArrayList<String> doc){//��doc��ȥ,������ȫ��docs��tf-idf
        TFIDFCalculator tfidfcalculator=new TFIDFCalculator();
        Map<String,Double> tfidfres=new HashMap<String, Double>();
        for (int i = 0; i < doc.size(); i++) {
            double tfidf=tfidfcalculator.tfIdf(doc,docs,doc.get(i));
            tfidfres.put(doc.get(i),tfidf);
        }
        return tfidfres;
    }
    public static ArrayList<String> getUserWord(int id){//�õ��û��ķִʽ��
        ArrayList<String> res=new ArrayList<String>();
        split sw=new split();
        LikeDAO likedao=new LikeDAO();
        ArrayList<Integer> userLikeList=likedao.queryLike(id);
        ArrayList<String> doc=new ArrayList<String>();
        for (int i = 0; i < userLikeList.size(); i++) {
            res=sw.getFilterWord(simDao.getColumnWithId("����",userLikeList.get(i)),expectedNature);
            for (int j = 0; j < res.size(); j++) {
                doc.add(res.get(j));
            }
        }
        return doc;
    }
    public static Map<String,Double> getUserTFIDF(int id){//�õ��û��Ĵʵ�tfidf���
        ArrayList<String> doc=new ArrayList<String>();
        doc=getUserWord(id);
        return getTFIDF(doc);
    }
    public static void getRecipeTFIDF(){//�õ����в��׵�tf idf����
        ArrayList<String> doc=new ArrayList<String>();
        for(Map.Entry<Integer,ArrayList<String>> entry: map.entrySet()){
            doc=entry.getValue();
            tfidfmap.put(entry.getKey(),getTFIDF(doc));
        }
    }


    /*
    ��������ǽ�,����,��ӵ������ռ���
    typeΪ0������û���,����һ��
    1����Ӳ��׵�,���ڶ���
     */
    public static Map<String, double[]> getVector(Map<String,Double> TFIDF,int type){
        Map<String, double[]> vectorSpace = new HashMap<String, double[]>();//�����ռ�{"Ƥ��":[1.973,1.792];"����":[....}
        double[] itemCountArray = null;//�����Ȩ��
        /*
        ����û������ݵ������ռ�
         */
        if(type==0){
            for(Map.Entry<String,Double> entry:TFIDF.entrySet()){
                double[] value=vectorSpace.get(entry.getKey());
                if(value!=null){
                    value[0]+=entry.getValue();
                }
                else{
                    itemCountArray = new double[2];
                    itemCountArray[1]=0;
                    itemCountArray[0]=entry.getValue();
                    vectorSpace.put(entry.getKey(),itemCountArray);
                }
            }
        }
        /*
        ��Ӳ��׵�
         */
        else{
            for (Map.Entry<String,Double> entry:TFIDF.entrySet()) {//entry1����ʽ{"Ƥ��":1.973,"����":"1.889"....}
                double[] value=vectorSpace.get(entry.getKey());
                if(value!=null){//�൱��containsKey()
                    value[1]+=entry.getValue();
                }else{
                    itemCountArray = new double[2];
                    itemCountArray[0]=0;
                    itemCountArray[1]=entry.getValue();
                    vectorSpace.put(entry.getKey(),itemCountArray);
                }
            }
        }

        return vectorSpace;

    }
    /*
    Ȩ��ȫ��Ϊ1
     */
    public static Map<String,double[]> getVector1(ArrayList<String> str,int type){
        int flag=1;
        if(type==1){
            flag=0;
        }
        Map<String, double[]> vectorSpace = new HashMap<String, double[]>();
        double[] itemCountArray = null;

        //��̬����תΪ����
        double weight=1;//���һ��Ȩ��ֵ��ԭ���г��ֵĵ�һ��Ϊ10��Ȼ����-1�����ֵ��Ҫƾ�о����ţ�
        double wei_reduce=1;//Ȩ���½���ֵ��Ĭ��Ϊ1
        double wei_value=weight;//����ѭ����ʵ���õ���ֵ

        int size=str.size();
        String[] strArray = (String[])str.toArray(new String[size]);
        for(int i=0; i<strArray.length; ++i)
        {
            if (wei_value<=1){//С��1�Ļ�����Ϊ1
                wei_value=1;
            }
            else wei_value=weight-i*wei_reduce;

            if(vectorSpace.containsKey(strArray[i]))
                vectorSpace.get(strArray[i])[type]+=wei_value;
            else
            {
                itemCountArray = new double[2];
                itemCountArray[type] = wei_value;
                itemCountArray[flag] = 0;
                vectorSpace.put(strArray[i], itemCountArray);
            }
        }

        return vectorSpace;

    }



    /*
    ���������ռ�������ƶ�,�����Ҫ�����������ռ��key��ȫһ��
    ����tf-idf��Ȩ��,Ч�ʵ͵Ŀ���ֱ�Ӻ���
    ���ǿ��������Ƽ���,ֻ������׼�����ƶ�,��ִֻ��һ�ξͻ����������ݹ��Ժ��ѯ.
    ���׼����ƶ�����,�����Ƽ����������.
     */
    public static Map<Integer,Double> getSimList1(int id){
        long startTime = System.currentTimeMillis();   //��ȡ��ʼʱ��


        Map<Integer,Double> simList = new HashMap<Integer,Double>();//��ż��������ֵ
        Map<String,Double> userTFIDF=getUserTFIDF(id);//����û��Ĵʵ�,tfidf���.��ʽ{"Ƥ��":1.973,"����":"1.889"....}
        getRecipeTFIDF();
        LikeDAO likedao=new LikeDAO();
        ArrayList<Integer> userLikeList=likedao.queryLike(id);
        map_mix mix=new map_mix();
        similarity sim=new similarity();

        Map<String, double[]> vec1=getVector(userTFIDF,0);//�û��������ռ�

        for (Map.Entry<Integer,Map<String,Double>> entry: tfidfmap.entrySet()) {//entry0����ʽΪ{966,{"Ƥ��":1.973,"����":....}}
            if(!(userLikeList.contains(entry.getKey()))){//ȥ���Ѿ���ǹ�ϲ���Ĳ��ס�
                Map<String, double[]> vecSpa = new HashMap<String, double[]>();
                /*
                ��ӣ�����ֱ����=��Ĭ����ǳ����ָ��ͳһ���á�

                ��ô���ڶ�ָ��һ������
                �����ں���Ѽ��������ռ���ټ�ȥ�ˣ�ֻ�������ˡ�
                 */
                Map<String, double[]> vec2=getVector(entry.getValue(),1);//�õ����׵�
                if(vec2.isEmpty()){
                    simList.put(entry.getKey(),0.0);
                }else{
                    vecSpa=mix.mapMix(vec1,vec2);
                    double res=sim.countCos(vecSpa);
                    if(!Double.isNaN(res)) {
                        simList.put(entry.getKey(),res);
                    }
                    System.out.println(res);
                    vec1=mix.mapRemove(vec1,vec2);
                }
            }
        }

        long endTime=System.currentTimeMillis(); //��ȡ����ʱ��
        System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ms");

        return simList;
    }
    //������Ƶ(��Ȩ��Ϊ1)�������ƶ�,������tf-idfֵ
    public static Map<Integer,Double> getSimList(int id){
        long startTime = System.currentTimeMillis();   //��ȡ��ʼʱ��


        Map<Integer,Double> simList = new HashMap<Integer,Double>();//��ż��������ֵ

        LikeDAO likedao=new LikeDAO();
        ArrayList<Integer> userLikeList=likedao.queryLike(id);
        map_mix mix=new map_mix();
        similarity sim=new similarity();

        ArrayList<String> doc=getUserWord(id);
        Map<String, double[]> vec1=getVector1(doc,0);//�û��������ռ�

        for (Map.Entry<Integer,ArrayList<String>> entry: map.entrySet()) {//entry0����ʽΪ{966,{"Ƥ��":1.973,"����":....}}

//            if(entry.getKey()!=2894)continue;
            if(!(userLikeList.contains(entry.getKey()))){//ȥ���Ѿ���ǹ�ϲ���Ĳ��ס�
                Map<String, double[]> vecSpa = new HashMap<String, double[]>();
                /*
                �������null,˵����һ��Ϊ��,������
                 */
                ArrayList<String> value=entry.getValue();
                if(value.contains(null))
                {
                    simList.put(entry.getKey(),0.0);
                    continue;
                }

                Map<String, double[]> vec2=getVector1(value,1);//�õ����׵�
                if(vec2.isEmpty()){
                    simList.put(entry.getKey(),0.0);
                }else{
                    vecSpa=mix.mapMix(vec1,vec2);
                    double res=sim.countCos(vecSpa);
                    if(!Double.isNaN(res)) {
                        simList.put(entry.getKey(),res);
                    }
                    else simList.put(entry.getKey(),0.0);
                    System.out.println(res);
                    vec1=mix.mapRemove(vec1,vec2);
                }
            }
        }


        long endTime=System.currentTimeMillis(); //��ȡ����ʱ��
        System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ms");

        return simList;
    }
    public static List<Map.Entry<Integer,Double>> countSim(Map<Integer,Double> simList){
        return sort.sortRes(simList);
    }
}
