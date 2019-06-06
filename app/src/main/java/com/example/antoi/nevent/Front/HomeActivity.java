package com.example.antoi.nevent.Front;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.antoi.nevent.DAO.BDHelper;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.Metier.Guest;
import com.example.antoi.nevent.Metier.User;
import com.example.antoi.nevent.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BDHelper db;
    private FloatingActionButton fabRejoindre;
    private FloatingActionButton fabCreate;
    private FloatingActionButton fabMenu;
    private boolean isFABOpen = false;
    private SwipeMenuListView listeEvent;
    private ArrayList<Event> allEvents;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private GlobalClass globalVariable;
    private Context context;
    private Button btn;

    private void init(){
        db = new BDHelper(this);
        fabMenu = (FloatingActionButton) findViewById(R.id.fab);
        fabRejoindre = (FloatingActionButton) findViewById(R.id.fab1);
        fabCreate = (FloatingActionButton) findViewById(R.id.fab2);
        listeEvent = (SwipeMenuListView) findViewById(R.id.lv_event_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        context = getApplicationContext();
        globalVariable = (GlobalClass) context;
        allEvents = new ArrayList<>();
    }

    // implémentation du menu sur la page d'accueil

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        // Titre
        setTitle("Vos évènements");

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // Mise à jour des données utilisateur dans le menu gauche
        updateNavHeader();

        //Gestion du menu qui gère les évènements (nouveau et rejoindre)
        fabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });
        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CreateEvent = new Intent(context, com.example.antoi.nevent.Front.CreateEventActivity.class);
                startActivity(CreateEvent);
            }
        });
        fabRejoindre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rejoindreEvent = new Intent(context, JoinEventActivity.class);
                startActivity(rejoindreEvent);
            }
        });

        // Recherche des évènements où l'utilisateur est organisateur.
        ArrayList<Event> org = db.searchEventByUser(globalVariable.getU().getIduser());
        // Recherche des évènements où l'utilisateur est participant
        ArrayList<Event> part = db.searchEventByParticipant(globalVariable.getU().getIduser());
        //Concaténation de tous les events
        allEvents.addAll(org);
        allEvents.addAll(part);

        // Remplissage de la listview avec les events
        ListAdapterHomeEvent adapter = new ListAdapterHomeEvent(this, R.layout.adapter_list_event, allEvents);
        listeEvent.setAdapter(adapter);

        // Quand on clique sur un event, on appel TabActivity en lui faisant passer l'id de l'event pour qu'il puisse traiter le bon event
        listeEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Event e = (Event) adapter.getItemAtPosition(position);
                Log.i("Activity", "Clique sur " + e.getNomEvent());
                Intent tabActivity = new Intent(context, com.example.antoi.nevent.Front.TabActivity.class);
                tabActivity.putExtra("event", Integer.toString(e.getIdevent()));
                startActivity(tabActivity);
            }
        });

        // Paramétrage du SwipeMenu
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "modify" item
                SwipeMenuItem modifyItem = new SwipeMenuItem(context);
                // set item background
                modifyItem.setBackground(new ColorDrawable(Color.rgb(250, 150, 50)));
                // set item width
                modifyItem.setWidth(200);
                // set a icon
                modifyItem.setIcon(R.mipmap.ic_edit);
                // add to menu
                menu.addMenuItem(modifyItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(context);
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(200, 20, 20)));
                // set item width
                deleteItem.setWidth(200);
                // set a icon
                deleteItem.setIcon(R.mipmap.ic_corbeille);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listeEvent.setMenuCreator(creator);

        // Définition des actions à réaliser lors d'un clic sur un item du SwipeMenu
        listeEvent.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                // Définition de l'évènement concerné
                Event e = allEvents.get(position);
                switch (index) {
                    case 0:
                        // Modifier un event
                        Intent modifEvent = new Intent(context, ModifEventActivity.class);
                        modifEvent.putExtra("event",Integer.toString(e.getIdevent()));
                        startActivity(modifEvent);
                        break;
                    case 1:
                        // Supprimer un event
                        //Pour chaque user on le supprime de l'event pour ensuite supprimer l'event
                        ArrayList<User> users = db.searchUsersByEvent(e.getIdevent());
                        for (User u : users){
                            Guest g = new Guest(u.getIduser(),e.getIdevent());
                            db.sql(g.generateDeleteRequest());
                        }
                        db.sql(e.generateDeleteRequest());
                        showMessage(e.getNomEvent() + " supprimé");
                        //On recharge la page pour actualiser la liste
                        Intent reload = new Intent(context,HomeActivity.class);
                        startActivity(reload);
                        break;
                }
                return false;
            }
        });
    }

    private void showMessage(String text) {
        // Affiche un message notifié et personnalisé sur l'écran d'une courte durée
        Toast.makeText(context,text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.nav_compte){
            Intent accountMenu = new Intent(context, com.example.antoi.nevent.Front.AccountActivity.class);
            startActivity(accountMenu);
        } else if (id == R.id.nav_deconnexion){
            Intent login = new Intent(context, com.example.antoi.nevent.Front.LoginActivity.class);
            startActivity(login);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navUserMail = headerView.findViewById(R.id.nav_user_mail);
        //récupération du user
        User u = globalVariable.getU();
        if( u.getNom() != null && u.getPrenom() != null){
            navUsername.setText(u.getPrenom() + " " + u.getNom());
        } else {
            navUsername.setText(u.getPseudo());
        }
        navUserMail.setText(u.getMail());
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabCreate.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabRejoindre.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        fabMenu.setImageResource(R.drawable.ic_menu_close_clear_cancel);
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabCreate.animate().translationY(0);
        fabRejoindre.animate().translationY(0);
        fabMenu.setImageResource(R.drawable.ic_menu_add);
    }
}
