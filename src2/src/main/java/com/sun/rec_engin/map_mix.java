package com.sun.rec_engin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by �� on 2017/3/3.
 * �ϲ����������ռ�ģ�
 */
public class map_mix {
    /*
    ��vec1+=vec2;
     */
    public Map<String, double[]> mapMix(Map<String, double[]> vec1,Map<String, double[]> vec2){
        Iterator<Map.Entry<String, double[]>> entries = vec2.entrySet().iterator();
//        double[] itemCountArray = null;
        while(entries.hasNext()){
//            Map.Entry<String, double[]> entry=entries.next();
//            itemCountArray=entry.getValue();
//            if(vec1.containsKey(entry.getKey())){
//                vec1.get(entry.getKey())[0]+=itemCountArray[0];
//                vec1.get(entry.getKey())[1]+=itemCountArray[1];
//            }
//            else{
//                vec1.put(entry.getKey(),itemCountArray);
//            }
            /*
            �����Ż�
            ��Google,��������д���˷Ѻܶ�ʱ��,Ӧ�û����map��get��ѯ���
            �Լ�û��Ҫʹ��containskey()����(����key��null)
             */
            Map.Entry<String, double[]> entry=entries.next();//vec2�ӵ���������ȡ��һ��map
            double[] vec1value = null;
            double[] vec2value = null;
            String vec2key=entry.getKey();
            vec2value=entry.getValue();
            vec1value=vec1.get(vec2key);
            if(vec1value!=null){
                vec1value[0]+=vec2value[0];
                vec1value[1]+=vec2value[1];
            }else {
                vec1.put(vec2key,vec2value);
            }


        }
        return vec1;
    }
    public Map<String, double[]> mapRemove(Map<String, double[]> vec1,Map<String, double[]> vec2){
        /*
        Ϊʲô��Ҫ��ôһ���ӵ��ķ�������Ϊ�ҷ�����ͬkeyֵ�Ĳ������ĸ��½���map���棬key��value����ָ��һ���ռ�
        ���Ի����һֱ�ۼ�
        ֻ��ÿ�μ�����֮��͸Ͻ��ټ�ȥ��
         */
        Iterator<Map.Entry<String, double[]>> entries = vec2.entrySet().iterator();
        double[] itemCountArray = null;
        while(entries.hasNext()){
//            Map.Entry<String, double[]> entry=entries.next();
//            itemCountArray=entry.getValue();
//            if(vec1.containsKey(entry.getKey())){
//                vec1.get(entry.getKey())[0]-=itemCountArray[0];
//                vec1.get(entry.getKey())[1]-=itemCountArray[1];
//                /*
//                ���������Ϊ0,��remove��
//                 */
//                if(vec1.get(entry.getKey())[0]==0&&vec1.get(entry.getKey())[1]==0){
//                    vec1.remove(entry.getKey());
//                }
//            }
//            else{
//                vec1.put(entry.getKey(),itemCountArray);
//            }
            Map.Entry<String, double[]> entry=entries.next();//vec2�ӵ���������ȡ��һ��map
            double[] vec1value = null;
            double[] vec2value = null;
            String vec2key=entry.getKey();
            vec2value=entry.getValue();
            vec1value=vec1.get(vec2key);
            if(vec1value!=null){
                vec1value[0]-=vec2value[0];
                vec1value[1]-=vec2value[1];
                if(vec1value[0]==0&&vec1value[1]==0){
                    vec1.remove(vec2key);
                }
            }else {
                vec1.put(vec2key,vec2value);
            }
        }
        return vec1;
    }
}
