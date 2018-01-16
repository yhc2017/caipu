package com.sun.rec_engin.handle;

import com.sun.dao.DisDAO;
import com.sun.dao.LikeDAO;
import com.sun.dao.SimDAO;
import com.sun.rec_engin.*;
import org.ansj.recognition.impl.StopRecognition;

import java.util.*;

/**
 * Created by sunyang on 2017/4/25.
 * ��������"��������"��
 * �������Ƚ����Ʒִ�,��ͨ��tf-idf����Ȩ��,��"�Ǵ��Ź�",""
 * �����{"��" -> "1.5426173900993556","�Ź�" -> "1.536144694813655","��" -> "1.4997449308253243"}
 *
 * ��tf-idf��ֵ��ΪȨ��,�ټ���
 */
public class mingcheng {
    static SimDAO simDao=new SimDAO();
    static DisDAO disDao=new DisDAO();
    static ArrayList<ArrayList<String>> docs=new ArrayList<ArrayList<String>>();
    static Set<String> expectedNature = new HashSet<String>() {{
        add("Ag");add("a");add("ad");add("an");add("Bg");add("b");add("d");add("i");
        add("j");add("n");add("nr");add("ns");add("nt");add("nx");add("nz");add("v");
        add("vd");add("vn");
    }};
    /*
    �����洢,���еĲ������ֵ�,�ִʽ��
     */
    static Map<Integer,ArrayList<String>> map=new HashMap<Integer, ArrayList<String>>();
    /*
    �����洢���в��׵����ֵ�,�ִʵ�,tf-idfֵ
     */
    public static Map<Integer,Map<String,Double>> tfidfmap=new HashMap<Integer, Map<String, Double>>();
    static {

        StopRecognition filter=null;
        ArrayList<String> res=new ArrayList<String>();
        split sw=new split();

        /*
        ��ȡȫ���ĵ��ķִ�
         */
        res=simDao.getColumn("��������");
        for (int i = 0; i < res.size(); i++) {
            ArrayList<String> document=new ArrayList<String>();
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
            res=sw.getFilterWord(simDao.getColumnWithId("��������",userLikeList.get(i)),expectedNature);
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
    ����"ţ����"��"������",�������{"ţ��":1.0,"��":0.8}{"����":1.1,"��":0.7},
    �������ƶ�[ţ��--����]0.8,[ţ��--��]0.2,[��--����]0.2,[��--��]0.7
    vec1�����"����",value=max(����*(����--ţ��),����*(����--��))=0.88....
    {"ţ��":1.0,"��":0.8,"����":0.88...}

    value1=max(value2*sim(key2,key1))
    vec1.put(key2,value1)

    Collections.max(Arrays.asList(numbers))//��ȡ���������ֵ
     */
    public static Map<String,Double> addSimWord(Map<String,Double> vec1,Map<String,Double> vec2){
        for(Map.Entry<String,Double> entry2:vec2.entrySet()){
            Double value1=vec1.get(entry2.getKey());
            if(value1!=null){
                vec1.put(entry2.getKey(),(value1+=entry2.getValue()));
            }else{
                ArrayList<Double> list=new ArrayList<Double>();
                Double sim=null;
                for(Map.Entry<String,Double> entry1:vec1.entrySet()){
                    sim=WordSimilarity.getSimilarity(entry1.getKey(),entry2.getKey());
                    list.add((entry2.getValue()*sim));
                }
                Double max=Collections.max(list);
                vec1.put(entry2.getKey(),max);
            }

        }
        return vec1;
    }
    /*
    ���û���vector���ͬ���,type==0
    �����׵�vector���ͬ���,type==1
    ���ܺܲ�,�Ƚ�һ�������Ҫһ���ʱ��,��Ҫ�������ڲ�ѯ
     */
    public static Map<String,double[]> addSimWordToVec(Map<String,double[]> vec1,Map<String,double[]> vec2,int type){
        Map<String,double[]> vecplus=new HashMap<String, double[]>();
        int flag=1;
        if(type==1){
            flag=0;
        }
        vecplus.putAll(vec1);
        for(Map.Entry<String,double[]> entry2:vec2.entrySet()){
            double[] itemCountArray = vec1.get(entry2.getKey());

            if(itemCountArray!=null){
                itemCountArray[type]+=entry2.getValue()[flag];
                itemCountArray[flag]=0;
                vecplus.put(entry2.getKey(),itemCountArray);
            }else{
                ArrayList<Double> list=new ArrayList<Double>();
                double sim=0.0;
                for(Map.Entry<String,double[]> entry1:vec1.entrySet()){
                    sim=WordSimilarity.getSimilarity(entry1.getKey(),entry2.getKey());
                    list.add(((entry2.getValue()[flag])*sim));
                }
                Double max=Collections.max(list);
                double[] value=new double[2];
                if(max!=null){
                    value[type]=max;
                }else value[type]=0;
                value[flag]=0;
                vecplus.put(entry2.getKey(),value);
            }
        }
        return vecplus;
    }


    /*
    ���������ռ�������ƶ�,�����Ҫ�����������ռ��key��ȫһ��
     */
    public static Map<Integer,Double> getSimList(int id){
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

    /*
    countSim1�������������,��������ͬ��ʵ�Ȩ��
     */
    public static  Map<Integer,Double>  getSimList1(int id){
        long startTime = System.currentTimeMillis();   //��ȡ��ʼʱ��
        System.out.println("��ʼ��������������ƶ�");

        Map<Integer,Double> simList = new HashMap<Integer,Double>();//��ż��������ֵ
        Map<String,Double> userTFIDF=getUserTFIDF(id);//����û��Ĵʵ�,tfidf���.��ʽ{"Ƥ��":1.973,"����":"1.889"....}
        getRecipeTFIDF();
        LikeDAO likedao=new LikeDAO();
        ArrayList<Integer> userLikeList=likedao.queryLike(id);
        map_mix mix=new map_mix();
        similarity sim=new similarity();


        Map<String, double[]> vec1=getVector(userTFIDF,0);//�û��������ռ�

//        tfidfmap.clear();
//        Map<String,Double> mapv=new HashMap<String, Double>();
//        tfidfmap.put(1047,mapv);

        for (Map.Entry<Integer,Map<String,Double>> entry: tfidfmap.entrySet()) {//entry0����ʽΪ{966,{"Ƥ��":1.973,"����":....}}
            long startTime1 = System.currentTimeMillis();   //��ȡ��ʼʱ��

            if(!(userLikeList.contains(entry.getKey()))){//ȥ���Ѿ���ǹ�ϲ���Ĳ��ס�
                Map<String, double[]> vecSpa = new HashMap<String, double[]>();
                /*
                ��ӣ�����ֱ����=��Ĭ����ǳ����ָ��ͳһ���á�

                ��ô���ڶ�ָ��һ������
                �����ں���Ѽ��������ռ���ټ�ȥ�ˣ�ֻ�������ˡ�
                 */
                Map<String, double[]> vec2=getVector(entry.getValue(),1);//�õ����׵�

                Map<String,double[]> vec1plus=new HashMap<String, double[]>();
                Map<String,double[]> vec2plus=new HashMap<String, double[]>();
                if(vec2.isEmpty()){
                    simList.put(entry.getKey(),0.0);
                }else{
                    vec1plus=mingcheng.addSimWordToVec(vec1,vec2,0);
                    vec2plus=mingcheng.addSimWordToVec(vec2,vec1,1);


                    vecSpa=mix.mapMix(vec1plus,vec2plus);
                    double res=sim.countCos(vecSpa);
                    if(!Double.isNaN(res)) {
                        simList.put(entry.getKey(),res);
                    }
                    System.out.println(res);
                    vec1=mix.mapRemove(vec1plus,vec2plus);
                }
            }
            long endTime1=System.currentTimeMillis(); //��ȡ����ʱ��
            System.out.println("����id="+entry.getKey()+"����:"+(endTime1-startTime1)+"ms");
        }

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
