package com.example.antoi.nevent.Front;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.antoi.nevent.DAO.BDHelper;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.Metier.Invitation;
import com.example.antoi.nevent.R;

import java.util.ArrayList;


public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    Button btn;
    TextView tv_code;
    BDHelper db;
    Context context;
    GlobalClass globalVariable;
    Event e;

    private void init(View view){
        context = getActivity().getApplicationContext();
        globalVariable = (GlobalClass) context;
        db = new BDHelper(context);
        e = globalVariable.getE();
        btn = view.findViewById(R.id.invit_btn);
        tv_code = view.findViewById(R.id.invit_code);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
        init(view);

        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = "";
                for (int i=0;i<4;i++){
                        code += Integer.toString( (int)(Math.random() * 10));
                }
                tv_code.setText(code);
                Invitation invit = new Invitation(code,e.getIdevent());
                db.sql(invit.generateInsertRequest());
            }
        });
        return view;
    }
}