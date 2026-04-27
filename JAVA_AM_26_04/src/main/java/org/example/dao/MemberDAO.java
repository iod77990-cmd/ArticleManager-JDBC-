package org.example.dao;

import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.sql.Connection;

public class MemberDAO {
    public static boolean LoginIdDuplicate(Connection conn, String loginId){
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `user` ");
        sql.append("WHERE loginId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(conn, sql);
    }

    public static int RollUpUser(Connection conn, String username, String loginId, String loginPw){

        SecSql sql = new SecSql();
        sql.append("INSERT INTO `user` ");
        sql.append("SET userName = ?, ", username);
        sql.append("loginId = ?, ", loginId);
        sql.append("loginPw = ?, ", loginPw);
        sql.append("joinDate = NOW();");

        return DBUtil.insert(conn, sql);

    }



}
