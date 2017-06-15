package com.tuwaner;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

public class Indexer {
    public static void main(String args[]) throws IOException {
        Article article = new Article(1, "Lucene全文检索框架",
                "Lucene如果信息检索系统在用户发出了检索请求后再去网上找答案","hulk");
        // 建立索引
        // 1、把Article转换为Document对象
        Document doc = new Document();
        //根据实际情况，使用不同的Field来对原始内容建立索引， Store.YES表示是否存储字段原始内容
        doc.add(new LongField("id", article.getId(), Field.Store.YES));
        doc.add(new StringField("author", article.getAuthor(), Field.Store.YES));
        doc.add(new TextField("title", article.getTitle(), Field.Store.YES));
        doc.add(new TextField("content", article.getContent(), Field.Store.YES));
        // 指定索引库的位置，本例为项目根目录下indexDir
        Directory directory = FSDirectory.open(new File("./indexDir"));
        // 分词器，不同的分词器有不同的规则
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.addDocument(doc);
        indexWriter.close();
    }
}
