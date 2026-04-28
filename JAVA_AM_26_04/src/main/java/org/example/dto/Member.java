package org.example.dto;

import java.util.Map;

public class Member extends Dto{

    private String loginId;
    private String loginPw;
    private String username;

    public Member(int id, String regDate, String updateDate, String loginId, String loginPw, String username){

        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.username = username;

    }

    public Member(Map<String,Object> memberMap){
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

    public void setName(String name) {
        this.username = username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getLoginPw() {
        return loginPw;
    }

    public static String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public String getRegDate() {
        return regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
