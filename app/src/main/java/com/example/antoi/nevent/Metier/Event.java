package com.example.antoi.nevent.Metier;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

    private int idevent;

    private String nomEvent;
    private String debutEvent;
    private String finEvent;
    private String descriptionEvent;
    private String lieu;

    private int organisateur;

    public Event(int idevent,int organisateur ,String nomEvent, String debutEvent, String finEvent, String descriptionEvent, String lieu) {
        this.idevent = idevent;
        this.nomEvent = nomEvent;
        this.debutEvent = debutEvent;
        this.finEvent = finEvent;
        this.descriptionEvent = descriptionEvent;
        this.lieu = lieu;
        this.organisateur = organisateur;
    }

    public Event(int organisateur ,String nomEvent, String debutEvent, String finEvent, String descriptionEvent, String lieu) {
        this.nomEvent = nomEvent;
        this.debutEvent = debutEvent;
        this.finEvent = finEvent;
        this.descriptionEvent = descriptionEvent;
        this.lieu = lieu;
        this.organisateur = organisateur;
    }

    public int getIdevent() {
        return idevent;
    }

    public void setIdevent(int idevent) {
        this.idevent = idevent;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }

    public String getDebutEvent() {
        return debutEvent;
    }

    public void setDateEvent(String debutEvent) {
        this.debutEvent = debutEvent;
    }

    public String getFinEvent() {
        return finEvent;
    }

    public void setFinEvent(String finEvent) {
        this.finEvent = finEvent;
    }

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getOrganisateur() {
        return organisateur;
    }

    public void setOrganisateur(int organisateur) {
        this.organisateur = organisateur;
    }

    public String generateInsertRequest(){
        return "INSERT INTO event(iduser,nomevent,descriptionevent,debutevent,finevent,lieuevent) VALUES ("+organisateur+",'"+nomEvent+"','"+descriptionEvent+ "','"+ debutEvent+"','"+finEvent+"','"+lieu+"');";
    }

    public String generateUpdateRequest(){
        return "UPDATE event SET iduser = "+organisateur+",nomevent = '"+nomEvent+"',descriptionevent = '"+descriptionEvent+"',debutevent = '"+debutEvent+"',finevent = '"+finEvent+"',lieuevent = '"+lieu+"' WHERE idevent = "+idevent+";";
    }

    public String generateDeleteRequest(){
        return "DELETE FROM event WHERE idevent = "+idevent+";";
    }

    // Insert pour jeu d'essai
    public String jeu_dessai(){
        return "INSERT INTO event(idevent,iduser,nomevent,descriptionevent,debutevent,finevent,lieuevent) VALUES ("+idevent+","+organisateur+",'"+nomEvent+"','"+descriptionEvent+ "','"+ debutEvent+"','"+finEvent+"','"+lieu+"');";
    }
}