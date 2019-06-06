package com.example.antoi.nevent.Front;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.antoi.nevent.DAO.BDHelper;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.Metier.User;
import com.example.antoi.nevent.R;

import java.util.ArrayList;



public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    BDHelper db;
    Context context;
    GlobalClass globalVariable;
    Event e;
    ArrayList<String> lesUsersV1 = new ArrayList<>();
    ListView liste;

    private void init(View view){
        context = getActivity().getApplicationContext();
        globalVariable = (GlobalClass) context;
        db = new BDHelper(context);
        e = globalVariable.getE();
        liste = view.findViewById(R.id.lv_participants_event);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        init(view);

        ArrayList<User> lesUsersV2 = db.searchUsersByEvent(e.getIdevent());
        //Ajout de l'organisateur dans les participants
        lesUsersV2.add(0,db.searchUserById(e.getOrganisateur()));
        for (User user: lesUsersV2) {
            lesUsersV1.add(user.getPseudo());
        }
        String[] lesUserV1 = new String[lesUsersV1.size()];
        for (int j=0; j<lesUsersV1.size();j++){
            lesUserV1[j] = lesUsersV1.get(j);
        }
        ListAdapter adapter = new com.example.antoi.nevent.Front.ListAdapter(context, lesUserV1, R.layout.adapter_list, R.id.tv_adapter_list);
        liste.setAdapter(adapter);
        return view;
    }
}