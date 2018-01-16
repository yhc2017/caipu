package test.com.sun.rec_engin.handle; 

import com.sun.dao.LikeDAO;
import com.sun.rec_engin.handle.mcThread;
import com.sun.rec_engin.handle.mingcheng;
import com.sun.rec_engin.map_mix;
import com.sun.rec_engin.similarity;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/** 
* mcThread Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 27, 2017</pre> 
* @version 1.0 
*/ 
public class mcThreadTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: call() 
* 
*/ 
@Test
public void testCall() throws Exception { 
//TODO: Test goes here...
    //�����̳߳�,�ҵ�cpuӦ�þ�4�߳������
    ExecutorService pool = Executors.newFixedThreadPool(3);


    System.out.println("��ʼ��������������ƶ�");

    Map<Integer,Double> simList = new HashMap<Integer,Double>();//��ż��������ֵ
    Map<String,Double> userTFIDF=mingcheng.getUserTFIDF(4);//����û��Ĵʵ�,tfidf���.��ʽ{"Ƥ��":1.973,"����":"1.889"....}
    mingcheng.getRecipeTFIDF();
    LikeDAO likedao=new LikeDAO();
    ArrayList<Integer> userLikeList=likedao.queryLike(1);
    map_mix mix=new map_mix();
    similarity sim=new similarity();


    Map<String, double[]> vec1=mingcheng.getVector(userTFIDF,0);//�û��������ռ�

//    List<Future<String>> results = new ArrayList<Future<String>>();

    //������Future,�õ��̵߳ķ��ؽ��
    ArrayList<Future<Map<Integer,Double>>> res=new ArrayList<Future<Map<Integer, Double>>>();

    //�����½�һ��map,��tfidfmap�����ȡ100��������
    Map<Integer,Map<String,Double>> map=new HashMap<Integer, Map<String, Double>>();
    int i=0;
    for (Map.Entry<Integer,Map<String,Double>> entry: mingcheng.tfidfmap.entrySet()){
        map.put(entry.getKey(),entry.getValue());
        i++;
        if(i>100) break;
    }

    long startTime = System.currentTimeMillis();   //��ȡ��ʼʱ��

    for (Map.Entry<Integer,Map<String,Double>> entry: map.entrySet()) {//entry0����ʽΪ{966,{"Ƥ��":1.973,"����":....}}{
        //�����½�һ���߳�
        mcThread c1=new mcThread(userLikeList,vec1,entry);
        //���̷߳��뵽һ����װ�õ�FutureTask����
        FutureTask<Map<Integer,Double>> task = new FutureTask(c1);
        //�����ݴ�
        res.add(task);
        pool.execute(task);
    }

    //Future��get()����������ȡִ�н������������������������һֱ�ȵ�����ִ����ϲŷ��أ�
    for(Future<Map<Integer,Double>> f:res){
        simList.putAll(f.get());
    }
    long endTime=System.currentTimeMillis(); //��ȡ����ʱ��
    System.out.println("��������ʱ�䣺 "+(endTime-startTime)+"ms");

    System.out.println();
    pool.shutdown();

    }
} 
