package org.example.container;

import org.example.controller.ArticleController;
import org.example.controller.MemberController;
import org.example.dao.ArticleDao;
import org.example.dao.MemberDAO;
import org.example.service.ArticleService;
import org.example.service.MemberService;
import org.example.session.Session;

import java.sql.Connection;
import java.util.Scanner;

public class Container {
    public static ArticleController articleController;
    public static MemberController memberController;

    public static ArticleService articleService;
    public static MemberService memberService;

    public static ArticleDao articleDao;
    public static MemberDAO memberDao;

    public static Scanner sc;

    public static Connection conn;

    public static Session session;

    public static void init() {
        sc = new Scanner(System.in);
        session = new Session();

        articleDao = new ArticleDao();
        memberDao = new MemberDAO();

        articleService = new ArticleService();
        memberService = new MemberService();

        articleController = new ArticleController();
        memberController = new MemberController();

    }
}