package org.example.dao;

import org.example.container.Container;
import org.example.dto.Member;
import org.example.util.DBUtil;
import org.example.util.SecSql;

import java.util.Map;

public class MemberDAO {
    public static boolean LoginIdDuplicate(String loginId){
        SecSql sql = new SecSql();

        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `user` ");
        sql.append("WHERE loginId = ?;", loginId);

        return DBUtil.selectRowBooleanValue(Container.conn, sql);
    }

    public static int RollUpUser(String username, String loginId, String loginPw){

        SecSql sql = new SecSql();
        sql.append("INSERT INTO `user` ");
        sql.append("SET userName = ?, ", username);
        sql.append("loginId = ?, ", loginId);
        sql.append("loginPw = ?, ", loginPw);
        sql.append("joinDate = NOW();");

        return DBUtil.insert(Container.conn, sql);

    }

    public Member getMemberByLoginId(String loginId){
        SecSql sql = new SecSql();
        sql.append("SELECT * FROM `user` WHERE loginId = ?;", loginId);

        Map<String,Object> memberMap = DBUtil.selectRow(Container.conn, sql);

        if(memberMap.isEmpty()){
            return null;
        }
        return new Member(memberMap);
    }
}
