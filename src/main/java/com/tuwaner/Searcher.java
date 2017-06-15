package com.tuwaner;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Searcher {
    public static void main(String args[]) throws Exception {
        // 搜索条件(不区分大小写)
        String queryString = "l";
        Directory directory = FSDirectory.open(new File("./indexDir/"));
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser queryParser = new QueryParser("title", analyzer);
        Query query = queryParser.parse(queryString);
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        TopDocs topDocs = indexSearcher.search(query, 100);
        int count = topDocs.totalHits;
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<Article> articleList = new ArrayList<Article>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            float score = scoreDoc.score;
            int docId = scoreDoc.doc;
            Document doc = indexSearcher.doc(docId);
            Article article = new Article(Integer.parseInt(doc.getField("id").stringValue()),//需要转为int型
                    doc.getField("title").stringValue(),
                    null,
                    doc.getField("author").stringValue());
            articleList.add(article);
        }
        indexReader.close();

        System.out.println("总结果数量为:" + articleList.size());
        for (Article article : articleList) {
            System.out.println("id="+article.getId());
            System.out.println("title="+article.getTitle());
            System.out.println("content="+article.getContent());
        }
    }
}
