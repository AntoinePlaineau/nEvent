package com.example.antoi.nevent.Front;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.antoi.nevent.DAO.BDHelper;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.User;
import com.example.antoi.nevent.R;
import com.example.antoi.nevent.Util;

public class RegisterActivity extends AppCompatActivity {

    private EditText regName, regMail, regPassword, regPassword2;
    private ProgressBar regProgress;
    private Button regBtn;
    BDHelper db;
    Context context;
    GlobalClass globalVariable;

    private void init() {
        regName = (EditText) findViewById(R.id.regName);
        regMail = (EditText) findViewById(R.id.regMail);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regPassword2 = (EditText) findViewById(R.id.regPassword2);
        regBtn = (Button) findViewById(R.id.regBtn);
        regProgress = (ProgressBar) findViewById(R.id.regProgress);
        context = getApplicationContext();
        db = new BDHelper(context);
        globalVariable = (GlobalClass) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Titre
        setTitle("Inscription");
        init();
        showMessage("Inscrivez-vous");
        inscription();
    }

    /**
     * evenement sur bouton s'inscrire
     */
    private void inscription(){
        // Bouton S'inscrire vers LoginActivity (page de connexion)
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Recuperation des données saisies
                regBtn.setVisibility(View.INVISIBLE);
                regProgress.setVisibility(View.VISIBLE);
                final String registerName = Util.apostrophe(regName.getText().toString());
                final String registerMail = Util.apostrophe(regMail.getText().toString());
                final String registerPassword = Util.apostrophe(regPassword.getText().toString());
                final String registerPassword2 = Util.apostrophe(regPassword2.getText().toString());

                // Instance d'un User
                User u = new User(0,null,null,registerMail,registerName,registerPassword);


                // Vérification des données saisies
                if(registerName.isEmpty() || registerMail.isEmpty() || registerPassword.isEmpty()){

                    showMessage("Veuillez vérifier tous les champs");
                    boutonVisible();

                } else if (!u.isValidEmail()) {

                    showMessage("Veuillez saisir un e-mail valide \n(Exemple : prenom.nom@gmail.com)");
                    boutonVisible();

                } else {

                    // Verifie si les mots de passe sont corrects
                    if(registerPassword.equals(registerPassword2)) {
                        Boolean verifMail = db.verifmail(registerMail);

                        if (verifMail == true) {
                            // Création du compte dans la BDD
                            Boolean insert = db.insert(registerName, registerMail, registerPassword);

                            if (insert == true) {
                                // Affichage validation
                                showMessage("Inscription valide");
                                boutonVisible();
                                Log.i("ValideLogin", registerName + registerMail + registerPassword + registerPassword2);

                                // Bascule vers la page d'accueil
                                Log.i("Bascule", "Bascule de RegisterActivity vers Login");
                                Intent loginActivity = new Intent(getBaseContext(), com.example.antoi.nevent.Front.LoginActivity.class);
                                startActivity(loginActivity);
                            }

                        } else {
                            showMessage("Cette e-mail existe déjà");
                            boutonVisible();
                        }

                    } else {
                        showMessage("Les mots de passe sont différents,\nconfirmer avec le même mot de passe");
                        boutonVisible();
                    }
                }
            }
        });
    }

    // Nofication
    private void showMessage(String message) {
        // Affiche un message notifié et personnalisé sur l'écran d'une courte durée
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    // Visibilité bouton / barre de chargement
    private void boutonVisible() {
        regBtn.setVisibility(View.VISIBLE);
        regProgress.setVisibility(View.INVISIBLE);
    }


}
