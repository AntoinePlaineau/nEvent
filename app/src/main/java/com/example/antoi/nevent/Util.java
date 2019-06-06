package com.example.antoi.nevent;

public class Util {

    //Gère les quote pour éviter les problèmes dans la BDD
    public static String apostrophe(String str){
        return str.replaceAll("'", "''");
    }
}
