package org.example.dto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Article extends Dto {

        private String title;
        private String body;

        public Article(int id, String regDate, String updateDate, String title, String body) {
            this.id = id;
            this.regDate = regDate;
            this.updateDate = updateDate;
            this.title = title;
            this.body = body;
        }


    public String getUpdateDate() {
        return updateDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", regDate='" + regDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
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
}
