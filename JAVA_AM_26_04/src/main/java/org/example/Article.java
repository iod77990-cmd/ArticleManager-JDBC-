package org.example;

public class Article {

        private int id;
        private String regDate;
        private String updateDate;
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
}
