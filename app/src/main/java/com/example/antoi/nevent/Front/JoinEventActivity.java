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
import com.example.antoi.nevent.Metier.Guest;
import com.example.antoi.nevent.R;
import com.example.antoi.nevent.Util;

public class JoinEventActivity extends AppCompatActivity {

    EditText et_code;
    Button btn;
    Context context;
    BDHelper db;
    GlobalClass globalVariable;

    private void init(){
        et_code = (EditText) findViewById(R.id.code_event);
        btn = (Button) findViewById(R.id.join_btn);
        context = getApplicationContext();
        db = new BDHelper(context);
        globalVariable = (GlobalClass) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_event);
        init();

        // Titre
        setTitle("Rejoindre un évènement");

        showMessage("Rejoindre un événement.");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Activity","Code entré : " + et_code.getText().toString());
                Event e = db.searchEventByCodeInvitation(Util.apostrophe(et_code.getText().toString()));
                if( e != null){
                    if( e.getOrganisateur() != globalVariable.getU().getIduser()) {
                        if(!db.verifUserGuested(globalVariable.getU().getIduser(),e.getIdevent())) {
                            Log.i("Acvivity", "Nom de l'évènement à rejoindre : " + e.getNomEvent());
                            Guest g = new Guest(globalVariable.getU().getIduser(), e.getIdevent());
                            db.sql(g.generateInsertRequest());
                            showMessage("Vous participez maintenant à l'évènement : " + e.getNomEvent() + " !");
                            Intent home = new Intent(context, com.example.antoi.nevent.Front.HomeActivity.class);
                            startActivity(home);
                        } else {
                            showMessage("Vous participez déjà à cet évènement");
                        }
                    } else {
                        showMessage("En tant qu'organisateur, vous participez par défaut à l'évènement");
                    }
                } else {
                    showMessage("Code d'invitation introuvable.");
                }
            }
        });

    }

    private void showMessage(String text) {
        // Affiche un message notifié et personnalisé sur l'écran d'une courte durée
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}
