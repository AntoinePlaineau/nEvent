package com.example.antoi.nevent.Front;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.R;

import java.util.List;

public class ListAdapterHomeEvent extends ArrayAdapter<Event> {

    private int layout;

    public ListAdapterHomeEvent(@NonNull Context context, int layout, List<Event> liste) {
        super(context, layout,liste);
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,parent,false);

        TextView nomEvent = (TextView) view.findViewById(R.id.nomEvent);
        TextView dateEvenDebut = (TextView) view.findViewById(R.id.dateEventDebut);
        TextView dateEventFin = (TextView) view.findViewById(R.id.dateEventFin);
        /*TextView lieuEvent = (TextView) view.findViewById(R.id.lieuEvent);
        TextView descriptionEvent = (TextView) view.findViewById(R.id.descriptionEvent);
        TextView organisateur = (TextView) view.findViewById(R.id.Organisateur);*/


        Event e = getItem(position);

        nomEvent.setText(e.getNomEvent());
        dateEvenDebut.setText("Début : "+e.getDebutEvent());
        dateEventFin.setText("Fin : "+e.getFinEvent());
        /*dateEvent.setText(e.getDebutEvent() + " - " + e.getFinEvent());
        lieuEvent.setText(e.getLieu());
        descriptionEvent.setText(e.getDescriptionEvent());
        BDHelper db = new BDHelper(getContext());
        User u = db.searchUserById(e.getOrganisateur());
        if (u.getPrenom() == null || u.getNom() == null) {
            organisateur.setText(u.getPseudo());
        } else {
            organisateur.setText(u.getPrenom() + ' ' + u.getNom());
        }*/


        return view;
    }




}
