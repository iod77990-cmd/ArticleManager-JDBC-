package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<Article> articles = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);
        int id = 0;
        int lastArticleId = 0;

        while (true) {
            System.out.print("명령어 : ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;
            }

            if (cmd.equals("article write")) {
                System.out.println("== 글쓰기 ==");
                id = ++lastArticleId;

                System.out.print("제목 : ");
                String title = sc.nextLine().trim();

                System.out.print("내용 : ");
                String body = sc.nextLine().trim();
                Article article = new Article(id, title, body);
                articles.add(article);

                lastArticleId++;
                System.out.println(article);
            } else if (cmd.equals("article list")) {
                System.out.println("== 목록 ==");
                if (articles.size() == 0) {
                    System.out.println("게시글 없음");
                    continue;
                }
                System.out.println("번호    /    제목    ");
                for (Article article : articles) {
                    System.out.printf("%d    /    %s    \n", article.getId(), article.getTitle());
                }
            } else if (cmd.startsWith("article delete")) {
                System.out.println("== 삭제 ==");
                id = Integer.parseInt(cmd.split(" ")[2]);
                Article foundArticle = getArticleById(id);
                if (foundArticle == null){
                    System.out.println("삭제할 게시물 없다.");
                    continue;
                }
                articles.remove(foundArticle);

                System.out.println("%d번 게시글 삭제 완료");

            }
            else if(cmd.startsWith("article modify")){
                System.out.println("== 게시글 수정 ==");
                id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if(foundArticle == null){
                    System.out.println("수정할 게시판이 없습니다.");
                    continue;
                }
                System.out.print("새로운 제목 : ");
                String newTitle = sc.nextLine().trim();

                System.out.print("새로운 내용 : ");
                String newBody = sc.nextLine().trim();

                foundArticle.setTitle(newTitle);
                foundArticle.setBody(newBody);


                System.out.printf("%d번 게시글 수정 완료\n", id);

            }
            else if(cmd.startsWith("article detail")){
                System.out.println("== 게시글 상세보기 ==");
                id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if(foundArticle == null){
                    System.out.println("상세보기할 게시글이 없음");
                    continue;
                }
                System.out.println("아이디 : " + foundArticle.getId());

                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());
            }
            else {
                System.out.println("이외의 명령어는 사용할 수 없습니다.");
            }
        }

        System.out.println("== 프로그램 종료 ==");
        sc.close();
    }

    private static Article getArticleById(int id){
        for(Article article : articles){
            if(article.getId() == id){
                return article;
            }
        }
        return null;
    }

}