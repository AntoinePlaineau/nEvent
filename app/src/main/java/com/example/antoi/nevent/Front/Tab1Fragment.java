package com.example.antoi.nevent.Front;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.R;


public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    // Déclaration des attributs pour le Tab1 "Détails"
    TextView tv_description_event;
    TextView tv_date_debut_event;
    TextView tv_date_fin_event;
    TextView tv_lieu_event;
    Event e;
    GlobalClass globalVariable;
    Context context;

    private void init(View view){
        context = getActivity().getApplicationContext();
        globalVariable = (GlobalClass) context;
        e = globalVariable.getE();
        tv_description_event = view.findViewById(R.id.tv_description_event);
        tv_date_debut_event = view.findViewById(R.id.tv_date_debut_event);
        tv_date_fin_event = view.findViewById(R.id.tv_date_fin_event);
        tv_lieu_event = view.findViewById(R.id.tv_lieu_event);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        init(view);

        // TextView Description de l'event
        tv_description_event.setText(e.getDescriptionEvent());
        // TextView Date de début de l'event
        tv_date_debut_event.setText(e.getDebutEvent().toString());
        // TextView Date de fin de l'event
        tv_date_fin_event.setText(e.getFinEvent().toString());
        // TextView Lieu de l'event
        tv_lieu_event.setText(e.getLieu());

        return view;
    }
}
