package org.example.dao;

import org.example.dto.Article;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
    static Connection conn;

    public ArticleDao(Connection conn){
        this.conn = conn;
    }

    public static int doWrite(String title, String body){
        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(), updateDate = NOW()");
        sql.append(", title = ?", title);
        sql.append(", `body` = ?", body);

        return DBUtil.insert(conn, sql);
    }

    public List<Article> getArticles() {
        SecSql sql = new SecSql();
        sql.append("SELECT *  FROM article ORDER BY id DESC");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);

        List<Article> articles = new ArrayList<>();

        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }
        return articles;
    }

    public Map<String, Object> getArticleById(int id){
        SecSql sql = new SecSql();
        sql.append("SELECT * ");
        sql.append("FROM article ");
        sql.append("WHERE id = ? ", id);

        return DBUtil.selectRow(conn, sql);
    }

    public void doUpdate(int id, String title, String body){
        SecSql sql = new SecSql();

        sql.append("UPDATE article ");
        sql.append("SET title = ?, ", title);
        sql.append("`body` = ? ,", body);
        sql.append("updateDate = NOW() ");
        sql.append("WHERE id = ?", id);

        DBUtil.update(conn, sql);
    }

    public void doDelete(int id){
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article WHERE id = ?", id);

        DBUtil.delete(conn, sql);
    }
}
