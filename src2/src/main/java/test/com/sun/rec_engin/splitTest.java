package test.com.sun.rec_engin; 

import com.sun.rec_engin.split;
import org.ansj.recognition.impl.StopRecognition;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashSet;
import java.util.Set;

/** 
* split Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 25, 2017</pre> 
* @version 1.0 
*/ 
public class splitTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: splitWord(String s) 
* 
*/ 
@Test
public void testSplitWord() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: splitWordtoArrDelUseless(String s) 
* 
*/ 
@Test
public void testSplitWordtoArrDelUseless() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: delUseless(String value) 
* 
*/ 
@Test
public void testDelUseless() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getWord(String str) 
* 
*/ 
@Test
public void testGetWord() throws Exception { 
//TODO: Test goes here...
    split sw=new split();
    String content="1.��ţ���Ĥ���������г�0.1-0.2���׺�ı�Ƭ�����������г�5-6���׳���ϸ˿�����۲˵ĸ����Ҷ��ȥ��ϴ�ɾ����г�2-3���׳��ĶΣ����ֵ������������������������ϸ�ࡣ 2.���������ȳ��ף�����ֲ�����յ�6-7���ȣ�����ţ��˿����ɿ�����£������Σ��ٳ����ִ࣬�������أ��ټ��붹����������������ۣ��ٵ߳����¡� 3.��˳�μ�����ǡ��Ͼơ����͡�ζ�����������ȣ��ټ����۲ˡ����⡢��˿���賴���º����׵߷�����ʢ�������������ϻ������ɡ�";
    sw.getWord(content);
} 

/** 
* 
* Method: getFilterWord(String str, Set<String> expectedNature) 
* 
*/ 
@Test
public void testGetFilterWordForStrExpectedNature() throws Exception { 
//TODO: Test goes here...
    split sw=new split();
    Set<String> expectedNature = new HashSet<String>() {{
        add("Ag");add("a");add("ad");add("an");add("Bg");add("b");add("d");add("i");
        add("j");add("n");add("nr");add("ns");add("nt");add("nx");add("nz");add("v");
        add("vd");add("vn");
    }};
    String s="������";
    sw.getFilterWord(s,expectedNature);
    System.out.println(sw.getFilterWord(s,expectedNature));
} 

/** 
* 
* Method: getFilterWord(String str, Set<String> expectedNature, StopRecognition fitler) 
* 
*/ 
@Test
public void testGetFilterWordForStrExpectedNatureFitler() throws Exception { 
//TODO: Test goes here...
    split sw=new split();
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
    String content="1.��ţ���Ĥ���������г�0.1-0.2���׺�ı�Ƭ�����������г�5-6���׳���ϸ˿�����۲˵ĸ����Ҷ��ȥ��ϴ�ɾ����г�2-3���׳��ĶΣ����ֵ������������������������ϸ�ࡣ 2.���������ȳ��ף�����ֲ�����յ�6-7���ȣ�����ţ��˿����ɿ�����£������Σ��ٳ����ִ࣬�������أ��ټ��붹����������������ۣ��ٵ߳����¡� 3.��˳�μ�����ǡ��Ͼơ����͡�ζ�����������ȣ��ټ����۲ˡ����⡢��˿���賴���º����׵߷�����ʢ�������������ϻ������ɡ�";
    sw.getFilterWord(content,expectedNature,filter);
} 


} 
