package org.example.controller;

import org.example.dto.Member;

public class Controller {
    protected static Member loginedMember = null;

    public void action(String cmd, String actionMethodName){

    }

    public static boolean isLogined(){
        return loginedMember != null;
    }
}


