package com.example.antoi.nevent.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.Metier.Guest;
import com.example.antoi.nevent.Metier.Invitation;
import com.example.antoi.nevent.Metier.User;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    // propriétés
    private final static String USER = "CREATE TABLE IF NOT EXISTS `user` (\n" +
            "  `iduser` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  `nom` TEXT,\n" +
            "  `prenom` TEXT,\n" +
            "  `mail` TEXT,\n" +
            "  `pseudo` TEXT,\n" +
            "  `password` TEXT\n" +
            ");";

    private final static String EVENT = "CREATE TABLE IF NOT EXISTS `event` (\n" +
            "  `idevent` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  `iduser` INTEGER,\n" +
            "  `nomevent` TEXT,\n" +
            "  `descriptionevent` TEXT,\n" +
            "  `debutevent` TEXT,\n" +
            "  `finevent` TEXT,\n" +
            "  `lieuevent` TEXT,\n" +
            "  FOREIGN KEY(iduser) REFERENCES user(iduser)\n" +
            ");";

    private final static String INVITATION = "CREATE TABLE IF NOT EXISTS `invitation` (\n" +
            "  `idinvitation` INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  `code` TEXT,\n" +
            "  `idevent` INTEGER,\n" +
            "  FOREIGN KEY(idevent) REFERENCES event(idevent)\n" +
            ");";

    private final static String GUEST = "CREATE TABLE IF NOT EXISTS `guest` (\n" +
            "  `iduser` INTEGER,\n" +
            "  `idevent` INTEGER,\n" +
            "  PRIMARY KEY(iduser,idevent),\n" +
            "  FOREIGN KEY(iduser) REFERENCES user(iduser),\n" +
            "  FOREIGN KEY(idevent) REFERENCES event(idevent) \n" +
            ");";

    private final static String CONSTRAINT = "PRAGMA foreign_keys=ON;";


    // Constructeur
    public BDHelper(Context context) {
        super(context, "BDDnEvent.sqlite", null, 1);
        // context de l'activité
        // nom de la BDD, permet de créer un dossier sur Android
        // un outil pour traiter les requêtes
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER);
        db.execSQL(EVENT);
        db.execSQL(INVITATION);
        db.execSQL(GUEST);

        //Composition du jeu d'essai
        ArrayList<Event> events = new ArrayList<>();
        Event e1 = new Event(1,1,"Anglais","24/05/2019","25/05/2019","Il s''agit d''une épreuve pour le BTS SIO","Lycée Merleau-Ponty");events.add(e1);
        Event e2 = new Event(2,2,"Epreuve E6","11/06/2019","12/06/2019","Il s''agit d''une épreuve pour le BTS SIO","Lycée Merleau-Ponty");events.add(e2);
        Event e3 = new Event(3,3,"Epreuve E4","08/06/2019","09/06/2019","Il s''agit d''une épreuve pour le BTS SIO","Lycée Merleau-Ponty");events.add(e3);
        Event e4 = new Event(4,4,"Epreuve E5","10/06/2019","11/06/2019","Il s''agit d''une épreuve pour le BTS SIO","Lycée Merleau-Ponty");events.add(e4);
        Event e5 = new Event(5,5,"Mathématiques","05/06/2019","06/06/2019","Il s''agit d''une épreuve pour le BTS SIO","Lycée Merleau-Ponty");events.add(e5);

        ArrayList<User> users = new ArrayList<>();
        User u1 = new User(1,"Plaineau","Antoine","antoine.plaineau@sio.fr","PLAINEA","password");users.add(u1);
        User u2 = new User(2,"Maroquesne","Nolan","nolan.maroquesne@sio.fr","MAROQU","password");users.add(u2);
        User u3 = new User(3,"Ardon","Jean-Maxime","jean-maxime.ardon@sio.fr","ARDONJ","password");users.add(u3);
        User u4 = new User(4,"Cessinas","Alex","alex.cessinas@sio.fr","CESSINA","password");users.add(u4);
        User u5 = new User(5,"Mignon","Alexis","alexis.mignon@sio.fr","MIGNON","password");users.add(u5);

        ArrayList<Guest> guests = new ArrayList<>();
        Guest g1 = new Guest(1,2);guests.add(g1);
        Guest g2 = new Guest(1,3);guests.add(g2);
        Guest g3 = new Guest(1,4);guests.add(g3);
        Guest g4 = new Guest(1,5);guests.add(g4);
        Guest g5 = new Guest(2,1);guests.add(g5);
        Guest g6 = new Guest(5,4);guests.add(g6);
        Guest g7 = new Guest(2,3);guests.add(g7);
        Guest g8 = new Guest(2,4);guests.add(g8);
        Guest g9 = new Guest(2,5);guests.add(g9);
        Guest g10 = new Guest(3,1);guests.add(g10);
        Guest g11 = new Guest(3,2);guests.add(g11);
        Guest g12 = new Guest(3,4);guests.add(g12);
        Guest g13 = new Guest(3,5);guests.add(g13);
        Guest g14 = new Guest(4,1);guests.add(g14);
        Guest g15 = new Guest(4,2);guests.add(g15);
        Guest g16 = new Guest(4,3);guests.add(g16);
        Guest g17 = new Guest(4,5);guests.add(g17);
        Guest g18 = new Guest(5,1);guests.add(g18);
        Guest g19 = new Guest(5,2);guests.add(g19);
        Guest g20 = new Guest(5,3);guests.add(g20);

        //Insertion du jeu d'essai
        for (Event e : events){
            db.execSQL(e.jeu_dessai());
        }
        for (User u : users){
            db.execSQL(u.jeu_dessai());
        }
        for (Guest g : guests){
            db.execSQL(g.generateInsertRequest());
        }

        db.execSQL(CONSTRAINT);
        Log.i("BDD","BDD créée");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // Inscription d'un user (inscription)
    public boolean insert(String pseudo, String mail, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("pseudo",pseudo);
        contentValues.put("mail",mail);
        contentValues.put("password",password);
        long ins = db.insert("user",null,contentValues);
        if(ins==1) {
            return false;
        } else {
            return true;
        }
    }

    // Verification si le mail existe (inscription)
    public Boolean verifmail (String mail){
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM user WHERE mail=?";
        Cursor cursor = db.rawQuery(req,new String[]{mail});
        if (cursor.getCount()>0) {
            return false;
        } else {
            return true;
        }
    }

    // Verfication si mail et mdp valide (connexion)
    public int verifConnexion (String mail, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM user WHERE mail=? AND password=?";
        Cursor cursor = db.rawQuery(req, new String[]{mail, password});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                int id = cursor.getInt(0);
                cursor.close();
                return id;
            }
        } else {
            return -1;
        }
        return -2;
    }

    // Verfication si mail et mdp valide (connexion)
    public User searchUserById (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM user WHERE iduser =?";
        Cursor cursor = db.rawQuery(req,new String[]{Integer.toString(id)});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false)
            {
                int iduser = cursor.getInt(0);
                String nom = cursor.getString(1);
                String prenom = cursor.getString(2);
                String mail = cursor.getString(3);
                String pseudo = cursor.getString(4);
                String pwd = cursor.getString(5);
                cursor.close();
                return new User(iduser,nom,prenom,mail,pseudo,pwd);
            }
        }
        return null;
    }

    // Verfication si mail et mdp valide (connexion)
    public ArrayList<Event> searchEventByUser (int id) {
        ArrayList<Event> listeEvent = new ArrayList<Event>();
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM event WHERE iduser = "+ id;
        Cursor cursor = db.rawQuery(req,new String[]{});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false)
            {
                int idevent = cursor.getInt(0);
                int iduser = cursor.getInt(1);
                String nom = cursor.getString(2);
                String description = cursor.getString(3);
                String datedebut = cursor.getString(4);
                String datefin = cursor.getString(5);
                String lieu = cursor.getString(6);

                listeEvent.add(new Event(idevent,iduser,nom,datedebut,datefin,description,lieu));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listeEvent;
    }

    public void sql(String req){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(req);
        Log.i("BDD","Requête : "+ req);
        db.close();
    }

    public Event searchEventById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM event WHERE idevent = "+id;
        Cursor cursor = db.rawQuery(req,new String[]{});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false)
            {
                int idevent = cursor.getInt(0);
                int iduser = cursor.getInt(1);
                String nom = cursor.getString(2);
                String description = cursor.getString(3);
                String datedebut = cursor.getString(4);
                String datefin = cursor.getString(5);
                String lieu = cursor.getString(6);

                Event e = new Event(idevent,iduser,nom, datedebut,datefin,description,lieu);
                return e;
            }
            cursor.close();
        }
        Log.i("Avtivity","Event non trouvé");
        return null;
    }

    /**
     * Suppression de l'invitation avant l'envoie de l'event
     * @param code
     * @return
     */
    public Event searchEventByCodeInvitation(String code){
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM invitation WHERE code =? ";
        Cursor cursor = db.rawQuery(req,new String[]{code});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false)
            {
                int idinvitation = cursor.getInt(0);
                String codeinvit = cursor.getString(1);
                int idevent = cursor.getInt(2);
                Invitation i = new Invitation(idinvitation,codeinvit,idevent);
                sql(i.generateDeleteRequest());
                Event e = searchEventById(idevent);
                return e;
            }
            cursor.close();
        }
        return null;
    }

    /**
     * Return true si le user est déjà dans l'event
     * @param iduser
     * @param idevent
     * @return
     */
    public boolean verifUserGuested(int iduser, int idevent){
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM guest WHERE idevent = "+ idevent + " AND iduser ="+iduser ;
        Cursor cursor = db.rawQuery(req,new String[]{});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Event> searchEventByParticipant(int iduser){
        ArrayList<Integer> idsEvent = new ArrayList<>();
        ArrayList<Event> listeEvent = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM guest WHERE iduser = "+iduser;
        Cursor cursor = db.rawQuery(req,new String[]{});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false)
            {
                idsEvent.add(cursor.getInt(1));
                cursor.moveToNext();
            }
            cursor.close();
        }
        for (Integer i: idsEvent) {
            listeEvent.add(searchEventById(i));
        }
        return listeEvent;
    }

    public ArrayList<User> searchUsersByEvent (int id) {
        ArrayList<User> listeUsers = new ArrayList<User>();
        ArrayList<Integer> listeIdUser = new ArrayList<Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        String req = "SELECT * FROM guest WHERE idevent = "+ id;
        Cursor cursor = db.rawQuery(req,new String[]{});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false)
            {
                int iduser = cursor.getInt(0);
                listeIdUser.add(iduser);
                cursor.moveToNext();
            }
            cursor.close();
        }
        for (Integer idU: listeIdUser) {
            listeUsers.add(searchUserById(idU));
        }
        return listeUsers;
    }
}