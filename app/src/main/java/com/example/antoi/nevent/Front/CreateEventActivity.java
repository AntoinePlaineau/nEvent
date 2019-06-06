package com.example.antoi.nevent.Front;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antoi.nevent.DAO.BDHelper;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.R;
import com.example.antoi.nevent.Util;

public class CreateEventActivity extends AppCompatActivity {

    EditText et_nom;
    EditText et_lieu;
    EditText et_debut;
    EditText et_fin;
    EditText et_description;
    Button createBtn;
    Context context;
    BDHelper db;
    GlobalClass globalVariable;

    private void init(){
        et_nom = (EditText) findViewById(R.id.create_event_nom);
        et_lieu = (EditText) findViewById(R.id.create_event_lieu);
        et_debut = (EditText) findViewById(R.id.create_event_debut);
        et_fin = (EditText) findViewById(R.id.create_event_fin);
        et_description = (EditText) findViewById(R.id.create_event_description);
        createBtn = (Button) findViewById(R.id.createbtn);
        context = getApplicationContext();
        db = new BDHelper(context);
        globalVariable = (GlobalClass) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        init();

        // Titre
        setTitle("Création d'un évènement");

        showMessage("Création d'un nouvel événement");
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = Util.apostrophe(et_nom.getText().toString());
                String lieu = Util.apostrophe(et_lieu.getText().toString());
                String debut = Util.apostrophe(et_debut.getText().toString());
                String fin = Util.apostrophe(et_fin.getText().toString());
                String description = Util.apostrophe(et_description.getText().toString());
                boolean verif = true;
                if(nom.isEmpty() || lieu.isEmpty() || debut.isEmpty() || fin.isEmpty() || description.isEmpty()) {
                    verif = false;
                }

                if(verif){
                    Event e = new Event(globalVariable.getU().getIduser(),nom, debut,fin,description,lieu);
                    db.sql(e.generateInsertRequest());
                    Intent Home = new Intent(context, com.example.antoi.nevent.Front.HomeActivity.class);
                    startActivity(Home);

                } else {
                    showMessage("Remplissez tous les champs");
                }
            }
        });
    }

    private void showMessage(String text) {
        // Affiche un message notifié et personnalisé sur l'écran d'une courte durée
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}