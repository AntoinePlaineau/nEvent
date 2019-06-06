package com.example.antoi.nevent.Metier;

import java.io.Serializable;

public class Guest implements Serializable {


    private int iduser;

    private int idevent;


    public Guest(int iduser, int idevent) {
        this.iduser = iduser;
        this.idevent = idevent;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public String generateInsertRequest(){
        return "INSERT INTO guest(iduser,idevent) VALUES ("+iduser+","+idevent+");";
    }

    public String generateDeleteRequest(){
        return "DELETE FROM guest WHERE iduser = "+iduser+" AND idevent = "+idevent+";";
    }
}
