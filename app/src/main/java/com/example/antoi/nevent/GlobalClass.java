package com.example.antoi.nevent;

import android.app.Application;

import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.Metier.User;

public class GlobalClass extends Application {

    User u;
    Event e;

    public void setUset(User u){
        this.u = u;
    }

    public User getU() {
        return u;
    }

    public void setE(Event e){
        this.e = e;
    }

    public Event getE() {
        return e;
    }
}
