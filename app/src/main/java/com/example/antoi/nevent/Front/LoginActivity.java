package com.example.antoi.nevent.Front;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.antoi.nevent.DAO.BDHelper;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.User;
import com.example.antoi.nevent.R;
import com.example.antoi.nevent.Util;

public class LoginActivity extends AppCompatActivity {

    ImageView login_logo;
    EditText login_mail,login_password;
    Button loginBtn, registerBtn;
    ProgressBar login_progress;
    BDHelper db;
    Context context;
    GlobalClass globalVariable;

    private void init() {
        login_mail = (EditText) findViewById(R.id.login_mail);
        login_password = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        login_progress = (ProgressBar) findViewById(R.id.login_progress);
        login_logo = (ImageView) findViewById(R.id.login_logo);
        context = getApplicationContext();
        db = new BDHelper(context);
        globalVariable = (GlobalClass) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Titre
        setTitle("Connexion");
        init();
        connexion();
    }

    /**
     * evenement sur bouton connexion
     */
    private void connexion() {
    // Bouton connexion vers Home (page d'accueil)
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bascule vers la page d'accueil
                Log.i("Bascule","Bascule de Login vers Home");
                Intent home = new Intent(context, com.example.antoi.nevent.Front.HomeActivity.class);
                startActivity(home);
            }
        });

        // Bouton inscriptiion vers RegisterActivity (page d'inscription)
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bascule vers la page d'accueil
                Log.i("Bascule","Bascule de Login vers RegisterActivity");
                Intent registerActivity = new Intent(getBaseContext(), com.example.antoi.nevent.Front.RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

        login_progress.setVisibility(View.INVISIBLE);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_progress.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.INVISIBLE);

                // Récupération du mail et du password
                final String mail = Util.apostrophe(login_mail.getText().toString());
                final String password = Util.apostrophe(login_password.getText().toString());
                int verifConnexion = db.verifConnexion(mail,password);
                // Instance d'un User
                User u = new User(0,null,null,mail,null,password);

                // Vérification des données saisies
                if (mail.isEmpty() || password.isEmpty()){
                    showMessage("Veuillez vérifier tous les champs");
                    boutonVisible();
                } else if(!u.isValidEmail()){
                    showMessage("Veuillez saisir un e-mail valide \n(Exemple : prenom.nom@gmail.com)");
                    boutonVisible();
                } else if (verifConnexion >= 0) {
                    globalVariable.setUset(db.searchUserById(verifConnexion));
                    boutonVisible();
                    // Validation du login
                    showMessage("Connexion valide");
                    Log.i("ValideLogin",mail+password);

                    // Bascule vers la page d'accueil
                    Log.i("Bascule","Bascule de Login vers Home");
                    Intent activity_home = new Intent(context,HomeActivity.class);
                    startActivity(activity_home);
                } else {
                    showMessage("E-mail et/ou mot de passe invalide");
                    boutonVisible();
                }
            }
        });
    }

    // Nofication
    private void showMessage(String text) {
        // Affiche un message notifié et personnalisé sur l'écran d'une courte durée
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();
    }

    // Visibilité bouton / barre de chargement
    private void boutonVisible() {
        loginBtn.setVisibility(View.VISIBLE);
        login_progress.setVisibility(View.INVISIBLE);
    }
}
