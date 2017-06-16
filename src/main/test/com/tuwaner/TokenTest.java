package com.tuwaner;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

public class TokenTest {
    @Test
    public void test() throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        String text = "Lucence中文分词器";
        TokenStream tokenStream = analyzer.tokenStream("", text);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            System.out.println(charTermAttribute);
        }
    }

    @Test
    public void testChineseAnalyzer() throws IOException {
        String text = "Lucence中文分词器";
        Analyzer analyzer = new IKAnalyzer();
        testAnalyzer(analyzer, text);
    }
    public void testAnalyzer(Analyzer analyzer, String text) throws IOException {
        System.out.println("当前使用的分词器：" + analyzer.getClass());
        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
        tokenStream.reset();
        tokenStream.addAttribute(CharTermAttribute.class);
        while (tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(charTermAttribute);
        }
    }

    @Test
    public void testPaoding() throws IOException {
        Analyzer analyzer = new PaodingAnalyzer("classpath:paoding/paoding-analysis.properties");
        String text = "我爱北京天安门，天安门上太阳升";
        TokenStream tokenStream = analyzer.tokenStream("", text);
        tokenStream.reset();
        while (tokenStream.incrementToken()){
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            System.out.println(charTermAttribute);
        }
    }
}
