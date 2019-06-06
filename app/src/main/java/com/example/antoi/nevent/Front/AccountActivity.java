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
import com.example.antoi.nevent.Metier.User;
import com.example.antoi.nevent.R;
import com.example.antoi.nevent.Util;

public class AccountActivity extends AppCompatActivity {

    EditText et_nom;
    EditText et_prenom;
    EditText et_pseudo;
    EditText et_mail;
    EditText et_password;
    Button btn;
    Context context;
    BDHelper db;
    GlobalClass globalVariable;

    private void init(){
        btn = findViewById(R.id.validate_account);
        et_nom = findViewById(R.id.account_nom);
        et_prenom = findViewById(R.id.account_prenom);
        et_pseudo = findViewById(R.id.account_pseudo);
        et_mail = findViewById(R.id.account_mail);
        et_password = findViewById(R.id.account_password);
        context = getApplicationContext();
        globalVariable = (GlobalClass) getApplicationContext();
        db = new BDHelper(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        init();

        // Titre
        setTitle("Votre compte");

        User u = globalVariable.getU();
        et_nom.setText(u.getNom());
        et_prenom.setText(u.getPrenom());
        et_pseudo.setText(u.getPseudo());
        et_mail.setText(u.getMail());
        et_password.setText(u.getPassword());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = Util.apostrophe(et_nom.getText().toString());
                String prenom = Util.apostrophe(et_prenom.getText().toString());
                String pseudo = Util.apostrophe(et_pseudo.getText().toString());
                String mail = Util.apostrophe(et_mail.getText().toString());
                String password = Util.apostrophe(et_password.getText().toString());

                boolean verif = true;
                if(nom.isEmpty()){
                    verif = false;
                } else if (prenom.isEmpty()){
                    verif = false;
                } else if (mail.isEmpty()){
                    verif = false;
                } else if (password.isEmpty()){
                    verif = false;
                } else if (pseudo.isEmpty()){
                    verif = false;
                }

                if(verif){
                    User u = new User(globalVariable.getU().getIduser(),nom,prenom,mail,pseudo,password);
                    db.sql(u.generateUpdateRequest());
                    globalVariable.setUset(u);
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
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }
}