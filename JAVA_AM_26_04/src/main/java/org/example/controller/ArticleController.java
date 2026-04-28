package org.example.controller;

import org.example.container.Container;
import org.example.dto.Article;
import org.example.service.ArticleService;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ArticleController{
    private static Scanner sc;
    private static ArticleService articleService;

    public ArticleController(){
        this.sc = Container.sc;
        this.articleService = Container.articleService;

    }

    public static void doWrite(){
        System.out.println("== 글쓰기 ==");

        System.out.print("제목 : ");
        String title = Container.sc.nextLine();
        System.out.print("내용 : ");
        String body = Container.sc.nextLine();

        int id = articleService.doWrite(title, body);

        System.out.printf("%d번 게시물 작성 완료\n", id);

    }

    public static void showList() {
        System.out.println("== 목록 ==");

        SecSql sql = new SecSql();
        sql.append("SELECT * FROM article;");

        List<Map<String, Object>> articleRows = DBUtil.selectRows(Container.conn, sql);

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

    public static void doDelete(String cmd){
        int id = 0;

        try{
            id = Integer.parseInt(cmd.split(" ")[2]);
        }catch(Exception e){
            System.out.println("번호는 정수로");
            return;
        }

        SecSql sql = new SecSql();

        Map<String,Object> articleMap = articleService.getArticleById(id);

        if(articleMap == null){
            System.out.println(id + "번 글은 없음");
            return;
        }

        System.out.println("-- 삭제 --");
        articleService.doDelete(id);

        System.out.printf("%d 번 게시글 삭제 완료\n", id);

    }

    public void doModify(String cmd){
        int id = 0;
        System.out.println("게시글 수정");

        try{
            id = Integer.parseInt(cmd.split(" ")[2]);
        }catch(Exception e){
            System.out.println("번호는 정수로");
            return;
        }

        Map<String,Object> articleMap = articleService.getArticleById(id);

        if(articleMap == null){
            System.out.println(id + "번 글은 없음");
            return;
        }

        System.out.println("== 수정 ==");
        System.out.print("새 제목 : ");
        String title = Container.sc.nextLine().trim();
        System.out.print("새 내용 : ");
        String body = Container.sc.nextLine().trim();

        articleService.doUpdate(id, title, body);

        System.out.printf("%d 번 게시물 수정 완료\n", id);

    }

    public void showDetail(String cmd){
        int id = 0;

        try{
            id = Integer.parseInt(cmd.split(" ")[2]);
        }catch(Exception e){
            System.out.println("번호는 정수로 입력");
            return;
        }

        Map<String,Object> articleMap = articleService.getArticleById(id);

        if(articleMap == null){
            System.out.println(id + "번 글은 없음");
            return;
        }

        System.out.println("== 상세보기 ==");

        Article article = new Article(articleMap);

        System.out.println("번호 : " + article.getId());
        System.out.println("작성날짜 : " + article.getRegDate());
        System.out.println("수정날짜 : " + article.getUpdateDate());
        System.out.println("제목 : " + article.getTitle());
        System.out.println("내용 : " + article.getBody());

    }

}
