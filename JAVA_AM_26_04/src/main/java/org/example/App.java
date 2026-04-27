package org.example;

import org.example.dto.Article;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    static List<Article> articles = new ArrayList<>();
    protected static Connection conn;

    Scanner sc = new Scanner(System.in);
    public void run() {

        System.out.println("== 프로그램 시작 ==");

        int id;

        try {
            conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3307/JDBC_AM_26_04", "user", "password");

            while (true) {
                System.out.print("명령어 : ");
                String cmd = sc.nextLine().trim();

                if (cmd.equals("exit")) {break;}

                else if (cmd.equals("members join")) {

                    System.out.println("--회원가입--");
                    String loginId ;
                    String loginPw ;
                    String loginPwConfirm ;
                    String username ;

                    while (true) {
                        System.out.print("로그인 아이디 : ");
                        loginId = sc.nextLine().trim();

                        if (loginId.length() == 0 || loginId.contains(" ")) {
                            System.out.println("아이디 입력");
                            continue;
                        }

                        // 회원가입 하기 전에 입력한 아이디를 등록된 로그인 아이디 찾기
                        boolean isLoginIdDuplicate = LoginIdDuplicate(loginId);

                        System.out.println(isLoginIdDuplicate);

                        if (isLoginIdDuplicate) {
                            System.out.println(loginId + "은(는) 이미 사용중");
                            break;
                        }
                    }

                    while (true) {
                        System.out.print("비밀번호 : ");
                        loginPw = sc.nextLine().trim();

                        if (loginPw.length() == 0 || loginPw.contains(" ")) {
                            System.out.println("비밀번호 입력");
                            continue;
                        }

                        System.out.print("비밀번호 확인 : ");
                        loginPwConfirm = sc.nextLine().trim();

                        if (!loginPw.equals(loginPwConfirm)) {
                            System.out.println("비밀번호 일치하지 않음");
                            continue;
                        }
                        break;
                    }

                    while (true) {
                        System.out.print("유저이름 : ");
                        username = sc.nextLine().trim();
                        if (username.length() == 0 || username.contains(" ")) {
                            System.out.println("유저이름은 공백으로 남길수 없고 나중에 수정 가능");
                            continue;
                        }
                        break;
                    }
                    RollUpUser(username, loginId, loginPw);
                    break;
                }

                else if (cmd.equals("article write")){
                    System.out.println("== 글쓰기 ==");

                    utilSecWriteToDB();
                }
                else if (cmd.equals("article list")) {
                    System.out.println("== 목록 ==");
                    List<Article> articles = getArticleFromDb();

                    if (articles == null) {
                        System.out.println("게시글이 없습니다.");
                        continue;
                    }

                    utilSecListDB();

                } else if (cmd.startsWith("article delete")) {
                    System.out.println("== 삭제 ==");
                    id = Integer.parseInt(cmd.split(" ")[2]);

                    utilSecDeleteDB(id);

                    // deleteArticleToDb(id);

                } else if (cmd.startsWith("article modify")) {
                    System.out.println("== 게시글 수정 ==");
                    id = Integer.parseInt(cmd.split(" ")[2]);

                    System.out.print("새로운 제목 : ");
                    String newTitle = sc.nextLine().trim();

                    System.out.print("새로운 내용 : ");
                    String newBody = sc.nextLine().trim();

                    // updateArticleToDb(id, newTitle, newBody);

                    utilSecUpdateDB(id, newTitle, newBody);

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
        } catch (SQLException e){
            System.out.println("에러 : " + e.getMessage());
        }finally {
            try{
                if(conn != null) conn.close(); }catch (SQLException e){sc.close();}
        }
        System.out.println("== 프로그램 종료 ==");
    }

    private void RollUpUser(String username, String loginId, String loginPw){

        SecSql sql = new SecSql();
        sql.append("INSERT INTO `user` ");
        sql.append("SET userName = ?, ", username);
        sql.append("loginId = ?, ", loginId);
        sql.append("loginPw = ?, ", loginPw);
        sql.append("joinDate = NOW();");

        DBUtil.insert(conn, sql);

    }

    public boolean LoginIdDuplicate(String loginId){
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `user` ");
        sql.append("WHERE loginId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    private static Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    private void utilSecWriteToDB(){
        System.out.print("제목 : ");
        String title = sc.nextLine();
        System.out.print("내용 : ");
        String body = sc.nextLine();

        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW(), updateDate = NOW()");
        sql.append(", title = ?", title);
        sql.append(", `body` = ?", body);

        int id = DBUtil.insert(conn, sql);
        System.out.printf("%d번 게시물 작성 완료\n",id);
    }

    private void utilSecListDB(){

        SecSql sql = new SecSql();
        sql.append("SELECT * FROM article;");

        List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

        System.out.println(" 번호 | 작성일 | 수정일 | 제목 | 내용");
        for (Map<String, Object> articleRow : articleRows) {
            int id = (int) articleRow.get("id");
            String regDate = (String)articleRow.get("regDate").toString();
            String updateDate = (String)articleRow.get("updateDate").toString();
            String title = (String)articleRow.get("title").toString();
            String body = (String)articleRow.get("body").toString();

            System.out.printf("%d | %s | %s | %s | %s\n", id, regDate, updateDate, title, body);

        }
    }

    private void utilSecDeleteDB(int id){
        System.out.println("게시글 삭제");

        SecSql sql = new SecSql();
        sql.append("DELETE FROM article WHERE id = ?", id);

        DBUtil.delete(conn, sql);

        System.out.printf("%d 번 게시글 삭제 완료\n", id);

    }

    private void utilSecUpdateDB(int id, String title, String body){

        System.out.println("게시글 수정");

        SecSql sql = new SecSql();

        sql.append("UPDATE article ");
        sql.append("SET title = ?, ", title);
        sql.append("`body` = ? ,", body);
        sql.append("updateDate = NOW() ");
        sql.append("WHERE id = ?", id);

        DBUtil.update(conn, sql);

        System.out.printf("%d 번 게시물 수정 완료\n", id);

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