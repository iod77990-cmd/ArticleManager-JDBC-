package org.example.controller;

import org.example.dao.MemberDAO;
import org.example.service.MemberService;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController{
private Connection conn;
 private Scanner sc;

 private MemberService memberService;

 public MemberController(Connection conn, Scanner sc){
     this.sc = sc;
     this.conn = conn;
     this.memberService = new MemberService();
 }

 public static void doJoin(){
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
         boolean isLoginIdDuplicate = MemberDAO.LoginIdDuplicate(conn, loginId);

         System.out.println(isLoginIdDuplicate);

         if (isLoginIdDuplicate) {
             System.out.println(loginId + "은(는) 이미 사용중");
             continue;
         }
         break;
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

     MemberService.RollUpUser(conn, username, loginId, loginPw);

     System.out.printf("%s님 가입을 환영합니다\n", username);

 }

 private void Login(){
     System.out.println("------로그인------");


 }

 private void Logout(){
     System.out.println("------로그아웃------");


 }

}
