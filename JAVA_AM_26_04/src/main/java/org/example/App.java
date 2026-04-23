package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    static List<Article> articles = new ArrayList<>();
    public void run(){

        System.out.println("== 프로그램 시작 ==");
        Scanner sc = new Scanner(System.in);
        int id;

        while (true) {
            System.out.print("명령어 : ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;
            }

            if (cmd.equals("article write")) {
                System.out.println("== 글쓰기 ==");

                System.out.print("제목 : ");
                String title = sc.nextLine().trim();

                System.out.print("내용 : ");
                String body = sc.nextLine().trim();

                id = insertArticleToDb(title, body);

                System.out.printf("%d번째 게시물 작성 완료\n", id);

            } else if (cmd.equals("article list")) {
                System.out.println("== 목록 ==");
                List<Article> articles = getArticleFromDb();

                if (articles == null) {
                    System.out.println("게시글이 없습니다.");
                    continue;
                }

                System.out.println(" 번호 | 작성일 | 수정일 | 제목 | 내용");
                for (Article article : articles) {
                    System.out.println(article.toString());
                }

            } else if (cmd.startsWith("article delete")) {
                System.out.println("== 삭제 ==");
                id = Integer.parseInt(cmd.split(" ")[2]);

                deleteArticleToDb(id);

            } else if (cmd.startsWith("article modify")) {
                System.out.println("== 게시글 수정 ==");
                id = Integer.parseInt(cmd.split(" ")[2]);

                System.out.print("새로운 제목 : ");
                String newTitle = sc.nextLine().trim();

                System.out.print("새로운 내용 : ");
                String newBody = sc.nextLine().trim();

                updateArticleToDb(id, newTitle, newBody);


            } else if (cmd.startsWith("article detail")) {
                System.out.println("== 게시글 상세보기 ==");
                id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("상세보기할 게시글이 없음");
                    continue;
                }
                System.out.println("아이디 : " + foundArticle.getId());

                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());
            } else {
                System.out.println("이외의 명령어는 사용할 수 없습니다.");
            }
        }
        System.out.println("== 프로그램 종료 ==");
        sc.close();

    }

    private static Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    private static int insertArticleToDb(String title, String body) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int receivedId = -1;

        try {
            String url = "jdbc:mariadb://127.0.0.1:3307/JDBC_AM_26_04?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "");

            System.out.println();
            System.out.println("연결 성공!😎");

            String sql = "INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = ?, `body` = ?";

            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, title);
            pstmt.setString(2, body);
            int affectedRow = pstmt.executeUpdate();
            System.out.println("affectedRow = " + affectedRow);

            rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                receivedId = rs.getInt(1);
            }

            System.out.println("게시물이 DB에 저장 성공 😎");

        } catch (SQLException e) {
            System.out.println("DB연결 실패 또는 에러😳 : " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return receivedId;
    }

    private static List<Article> getArticleFromDb() {
        List<Article> articles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            String url = "jdbc:mariadb://127.0.0.1:3307/JDBC_AM_26_04?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "");
            System.out.println();
            System.out.println("연결 성공!😎");

            String sql = "SELECT *  FROM article ORDER BY id DESC";

            System.out.println(sql);

            pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String regDate = rs.getString("regDate");
                String updateDate = rs.getString("updateDate");
                String title = rs.getString("title");
                String body = rs.getString("body");

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);
            }

            System.out.println("DB에 저장된 게시물 조회 성공!😎");

        } catch (SQLException e) {
            System.out.println("DB연결 실패 또는 에러😳 : " + e.getMessage());
        } finally {

            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return articles;
    }

    private static void updateArticleToDb(int id, String newtitle, String newbody) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            String url = "jdbc:mariadb://127.0.0.1:3307/JDBC_AM_26_04?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "");

            System.out.println();
            System.out.println("연결 성공!");

            String sql = "Update article SET regDate = NOW(), updateDate = NOW(), title = ?, `body` = ? WHERE id = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newtitle);
            pstmt.setString(2, newbody);
            pstmt.setInt(3, id);

            int affectedRow = pstmt.executeUpdate();
            System.out.println("affectedRow = " + affectedRow);

            if(affectedRow > 0){
                System.out.println(id + "번 게시물 수정 완료");
            }else{
                System.out.println("수정할 게시물을 찾을 수 없거나 존재하지 않음");
            }

        } catch (SQLException e) {
            System.out.println("DB연결 실패 또는 에러 : " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteArticleToDb(int id) {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            String url = "jdbc:mariadb://127.0.0.1:3307/JDBC_AM_26_04?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul";
            conn = DriverManager.getConnection(url, "root", "");

            System.out.println();
            System.out.println("연결 성공!");

            String sql = "DELETE FROM article WHERE id = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            System.out.println(sql);

            int affectedRow = pstmt.executeUpdate();
            System.out.println("affectedRow = " + affectedRow);

            if(affectedRow > 0){
                System.out.println(id + "번 게시물 삭제 완료");
            }else{
                System.out.println("삭제할 게시물을 찾을 수 없거나 존재하지 않음");
            }

        } catch (SQLException e) {
            System.out.println("DB연결 실패 또는 에러😳 : " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
