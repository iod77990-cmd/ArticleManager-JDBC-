package org.example.service;

import org.example.dao.ArticleDao;
import org.example.dto.Article;

import java.sql.Connection;
import java.util.List;

public class ArticleService {
    private ArticleDao articleDao;

    public ArticleService(Connection conn){
        this.articleDao = new ArticleDao(conn);
    }

    public int doWrite(String title, String body){
        return articleDao.doWrite(title, body);
    }
    public List<Article> getArticles(){
        return articleDao.getArticles();
    }
    public void doUpdate(int id, String title, String body){
        articleDao.doUpdate(id, title, body);
    }
    public void doDelete(int id){
        articleDao.doDelete(id);
    }
}
