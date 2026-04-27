package org.example.controller;

import org.example.dao.ArticleDao;
import org.example.service.ArticleService;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController{
    private static Connection conn;
    private static Scanner sc;

    private ArticleService articleService;

    public ArticleController(Scanner sc, Connection conn){
        this.sc = sc;
        this.conn = conn;
        this.articleService = new ArticleService(conn);
    }

    public static void doWrite(){
        System.out.println("== 글쓰기 ==");

        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        int id = ArticleDao.doWrite(title, body);

        System.out.printf("%d번 게시물 작성 완료\n", id);

    }

    public static void showList() {
        System.out.println("== 목록 ==");

        SecSql sql = new SecSql();
        sql.append("SELECT * FROM article;");

        List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

        System.out.println(" 번호 | 작성일 | 수정일 | 제목 | 내용");
        for (Map<String, Object> articleRow : articleRows) {
            int id = (int) articleRow.get("id");
            String regDate = (String) articleRow.get("regDate").toString();
            String updateDate = (String) articleRow.get("updateDate").toString();
            String title = (String) articleRow.get("title").toString();
            String body = (String) articleRow.get("body").toString();

            System.out.printf("%d | %s | %s | %s | %s\n", id, regDate, updateDate, title, body);

        }
    }

    public void doDelete(){


    }

    public void doModify(){


    }

}
