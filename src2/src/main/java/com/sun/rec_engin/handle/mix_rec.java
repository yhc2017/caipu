package com.sun.rec_engin.handle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sunyang on 2017/5/19.
 * ��һ�������ǻ���Ƽ�,ʹ��[��������:0.1,����:0.3,ԭ��:0.6]
 * ��Ȩ�ؼ�Ȩ,���Ϊ��,��ô���ƶȶ���Ϊ0.1
 */
public class mix_rec {
    public static List<Map.Entry<Integer,Double>> countSim(int id){
        mingcheng mc=new mingcheng();
        zuofa zf=new zuofa();
        yuanliao yl=new yuanliao();

        Map<Integer,Double> simList1=mc.getSimList(id);
        Map<Integer,Double> simList2=zf.getSimList(id);
        Map<Integer,Double> simList3=yl.getSimList(id);

        Map<Integer,Double> simList = new HashMap<Integer,Double>();//��ż��������ֵ
        Iterator<Map.Entry<Integer, Double>> entries = simList1.entrySet().iterator();
        while (entries.hasNext()){
            Double res=0.0;
            Map.Entry<Integer, Double> entry = entries.next();
            int i=entry.getKey();
            res=0.1*entry.getValue()+0.3*simList2.get(i)+0.6*simList3.get(i);
            simList.put(i,res);
        }
        return sort.sortRes(simList);
    }
}
