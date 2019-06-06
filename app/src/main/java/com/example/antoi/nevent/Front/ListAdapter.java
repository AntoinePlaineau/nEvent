package com.example.antoi.nevent.Front;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.antoi.nevent.R;

import java.util.ArrayList;


public class ListAdapter extends ArrayAdapter<String> {

    private ArrayList<String> texts = new ArrayList<>();
    private int layout;
    private int text;

    public ListAdapter(Context context, String[] values, int layout, int text ) {
        super(context, R.layout.adapter_list, values);
        for(String pseudo:values){
            texts.add(pseudo);
        }
        this.layout = layout;
        this.text = text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.layout,parent,false);
        TextView pseudo = view.findViewById(this.text);
        if(position == 0){
            pseudo.setText("Organisateur : " +texts.get(position));
        } else {
            pseudo.setText(texts.get(position));
        }
        return view;
    }
}
