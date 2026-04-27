package org.example.service;

import org.example.dao.MemberDAO;

import java.sql.Connection;

public class MemberService {

    private static MemberDAO memberDao;

    public MemberService(){
        this.memberDao = new MemberDAO();
    }

    public boolean LoginIdDuplicate(Connection conn, String loginId){
        return memberDao.LoginIdDuplicate(conn, loginId);
    }

    public static int RollUpUser(Connection conn, String username, String loginId, String loginPw){
        return memberDao.RollUpUser(conn, loginId, loginPw, username);
    }

}
