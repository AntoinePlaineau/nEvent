package com.example.antoi.nevent.Metier;

import android.text.TextUtils;

import java.io.Serializable;

public class User implements Serializable {


    // propriété
    private int iduser;
    private String nom;
    private String prenom;
    private String mail;
    private String pseudo;
    private String password;


    // constructeurs

    public User(int iduser, String nom, String prenom, String mail, String pseudo, String password) {
        this.iduser = iduser;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.pseudo = pseudo;
        this.password = password;
    }
    public User(String pseudo, String mail, String password) {
        this.pseudo = pseudo;
        this.mail = mail;
        this.password = password;
    }
    public User(String mail) {
        this.mail = mail;
    }




    // getter et setter
    public int getIduser() {
        return iduser;
    }
    public void setIduser(int iduser) {
        this.iduser = iduser;
    }


    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }



    // Ajout d'un User (inscription d'un compte)
    public String generateInsertRequest(){
        return "INSERT INTO user(nom,prenom,mail,pseudo,password) VALUES ('"+nom+"','"+prenom+"','"+mail+"','"+pseudo+"','"+password+"');";
    }

    public String generateUpdateRequest(){
        return "UPDATE user SET nom = '"+nom+"', prenom = '"+prenom+"', mail = '"+mail+"', pseudo = '"+pseudo+"', password = '"+password+"' WHERE iduser= "+iduser+";";
    }

    // Supprime un user (Supprime un compte)
    public String generateDeleteRequest(){
        return "DELETE FROM user WHERE iduser = "+iduser+";";
    }

    // Insert pour jeu d'essai
    public String jeu_dessai(){
        return "INSERT INTO user(iduser,nom,prenom,mail,pseudo,password) VALUES ("+iduser+",'"+nom+"','"+prenom+"','"+mail+"','"+pseudo+"','"+password+"');";
    }


    //Saisie valide d'un email
    public boolean isValidEmail() {
        if (TextUtils.isEmpty(mail)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        }
    }

}
