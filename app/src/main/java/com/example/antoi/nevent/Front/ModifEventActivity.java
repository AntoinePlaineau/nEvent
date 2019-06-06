package com.example.antoi.nevent.Front;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antoi.nevent.DAO.BDHelper;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.R;
import com.example.antoi.nevent.Util;

public class ModifEventActivity extends AppCompatActivity {

    EditText et_nom;
    EditText et_lieu;
    EditText et_dateDebut;
    EditText et_dateFin;
    EditText et_description;
    Button btn;
    Context context;
    BDHelper db;
    GlobalClass globalVariable;
    Event e;

    private void init(){
        btn = findViewById(R.id.modifbtn);
        et_nom = findViewById(R.id.modif_event_nom);
        et_lieu = findViewById(R.id.modif_event_lieu);
        et_dateDebut = findViewById(R.id.modif_event_debut);
        et_dateFin = findViewById(R.id.modif_event_fin);
        et_description = findViewById(R.id.modif_event_description);
        context = getApplicationContext();
        globalVariable = (GlobalClass) context;
        db = new BDHelper(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_event);
        // Titre
        setTitle("Modification de l'évènement");
        final Intent i = getIntent();
        init();


        e = db.searchEventById(Integer.parseInt(i.getStringExtra("event")));
        et_nom.setText(e.getNomEvent());
        et_lieu.setText(e.getLieu());
        et_dateDebut.setText(e.getDebutEvent());
        et_dateFin.setText(e.getFinEvent());
        et_description.setText(e.getDescriptionEvent());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = Util.apostrophe(et_nom.getText().toString());
                String lieu = Util.apostrophe(et_lieu.getText().toString());
                String dateDebut = Util.apostrophe(et_dateDebut.getText().toString());
                String dateFin = Util.apostrophe(et_dateFin.getText().toString());
                String description = Util.apostrophe(et_description.getText().toString());

                boolean verif = true;
                if(nom.isEmpty()){
                    verif = false;
                } else if (lieu.isEmpty()){
                    verif = false;
                } else if (dateDebut.isEmpty()){
                    verif = false;
                } else if (dateFin.isEmpty()){
                    verif = false;
                } else if (description.isEmpty()){
                    verif = false;
                }

                if(verif ==true){
                    e.setNomEvent(nom);
                    e.setLieu(lieu);
                    e.setDateEvent(dateDebut);
                    e.setFinEvent(dateFin);
                    e.setDescriptionEvent(description);
                    db.sql(e.generateUpdateRequest());
                    Intent home = new Intent(context, com.example.antoi.nevent.Front.HomeActivity.class);
                    startActivity(home);
                    showMessage("Modifications enregistrées");

                } else {
                    showMessage("Remplissez tous les champs");
                }
            }
        });
    }


    private void showMessage(String text) {
        // Affiche un message notifié et personnalisé sur l'écran d'une courte durée
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }
}