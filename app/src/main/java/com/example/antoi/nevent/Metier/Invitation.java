package com.example.antoi.nevent.Metier;

import java.io.Serializable;

public class Invitation implements Serializable {

    private int idinvitation;

    private String code;

    private int idevent;

    public Invitation(int idinvitation, String code, int idevent) {
        this.idinvitation = idinvitation;
        this.code = code;
        this.idevent = idevent;
    }

    public Invitation( String code, int idevent) {
        this.code = code;
        this.idevent = idevent;
    }

    public int getIdinvitation() {
        return idinvitation;
    }

    public void setIdinvitation(int idinvitation) {
        this.idinvitation = idinvitation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public String generateInsertRequest(){
        return "INSERT INTO invitation (code,idevent) VALUES ('"+code+"',"+idevent+");";
    }

    public String generateDeleteRequest(){
        return "DELETE FROM invitation WHERE idinvitation = "+idinvitation+";";
    }
}
