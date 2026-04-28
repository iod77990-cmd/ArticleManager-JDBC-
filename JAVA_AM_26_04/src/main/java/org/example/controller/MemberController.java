package org.example.controller;

import org.example.container.Container;
import org.example.dto.Member;
import org.example.service.ArticleService;
import org.example.service.MemberService;

import java.util.Scanner;

public class MemberController{
 private static Scanner sc;
 private static MemberService memberService;

 public MemberController(){
     this.memberService = Container.memberService;
 }

 public static void doJoin(){
     System.out.println("--회원가입--");
     String loginId ;
     String loginPw ;
     String loginPwConfirm ;
     String username ;

     while (true) {
         System.out.print("로그인 아이디 : ");
         loginId = Container.sc.nextLine().trim();

         if (loginId.length() == 0 || loginId.contains(" ")) {
             System.out.println("아이디 입력");
             continue;
         }

         // 회원가입 하기 전에 입력한 아이디를 등록된 로그인 아이디 찾기
         boolean isLoginIdDuplicate = memberService.LoginIdDuplicate(loginId);

         System.out.println(isLoginIdDuplicate);

         if (isLoginIdDuplicate) {
             System.out.println(loginId + "은(는) 이미 사용중");
             continue;
         }
         break;
     }

     while (true) {
         System.out.print("비밀번호 : ");
         loginPw = Container.sc.nextLine().trim();

         if (loginPw.length() == 0 || loginPw.contains(" ")) {
             System.out.println("비밀번호 입력");
             continue;
         }

         System.out.print("비밀번호 확인 : ");
         loginPwConfirm = Container.sc.nextLine().trim();

         if (!loginPw.equals(loginPwConfirm)) {
             System.out.println("비밀번호 일치하지 않음");
             continue;
         }
         break;
     }

     while (true) {
         System.out.print("유저이름 : ");
         username = Container.sc.nextLine().trim();
         if (username.length() == 0 || username.contains(" ")) {
             System.out.println("유저이름은 공백으로 남길수 없고 나중에 수정 가능");
             continue;
         }
         break;
     }

     MemberService.RollUpUser(username, loginId, loginPw);

     System.out.printf("%s님 가입을 환영합니다\n", username);

 }

 public void Login(){
     String loginId;
     String loginPw;
     System.out.println("------로그인------");

     while (true) {
         System.out.print("로그인 아이디 : ");
         loginId = Container.sc.nextLine().trim();

         if (loginId.length() == 0 || loginId.contains(" ")) {
             System.out.println("아이디 공백 제외하고 다시 작성");
             continue;
         }
         boolean isLoginIdDuplicate = memberService.LoginIdDuplicate(loginId);
         if (isLoginIdDuplicate == false) {
             System.out.println(loginId + "는 없음");
             continue;
         }
         break;
     }

     Member member = memberService.getMemberByLoginId(loginId);

     int tryMaxCount = 3;
     int tryCount = 0;
     while (true) {
         if (tryCount >= tryMaxCount) {
             System.out.println("비밀번호 확인하고 다시 시도!");
             break;
         }
         System.out.print("비밀번호 : ");
         loginPw = Container.sc.nextLine().trim();

         if (loginPw.length() == 0 || loginPw.contains(" ")) {
             tryCount++;
             System.out.printf("비밀번호 공백제외하고 다시 작성(%d/3)\n",tryCount);
             continue;
         }

         Container.session.loginedMemberId = member.getId();

         System.out.println(member.getUsername() + "님 로그인 성공");

         break;
     }

 }
    public void showProfile(){

     if (Container.session.loginedMember == null){

         System.out.println("로그인 상태가 아닙니다.");

        } else {

         MemberService memberService = new MemberService();
         ArticleService articleService = new ArticleService();



         System.out.printf("%s 님의 프로필\n",Member.getUsername());

            System.out.println(Container.session.loginedMember);

            int loginedId = Container.session.loginedMember.getId();

            int foundId = memberService.getMemberByLoginId(String.valueOf(loginedId)).getId();

            if(loginedId == foundId){


            }
            else {

            }

        }
    }

    public void Logout(){
        if (Container.session.loginedMember == null) {
            System.out.println("로그인 상태가 아닙니다.");
            return;
        }
        System.out.println("== 로그아웃 됨 ==");
        Container.session.loginedMember = null;
        Container.session.loginedMemberId = -1;
    }



}
