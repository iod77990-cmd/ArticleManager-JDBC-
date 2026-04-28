package org.example.service;

import org.example.container.Container;
import org.example.dao.MemberDAO;
import org.example.dto.Member;

public class MemberService {

    private static MemberDAO memberDao;

    public MemberService(){
        this.memberDao = Container.memberDao;
    }

    public boolean LoginIdDuplicate(String loginId){
        return memberDao.LoginIdDuplicate(loginId);
    }

    public static void RollUpUser(String username, String loginId, String loginPw){
        memberDao.RollUpUser(loginId, loginPw, username);
    }

    public Member getMemberByLoginId(String loginId){
        return memberDao.getMemberByLoginId(loginId);
    }

}
