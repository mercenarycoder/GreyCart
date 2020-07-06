package com.greycart.greycart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(@NonNull Context context, ArrayList<String> list) {
        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }
    public View initView(int position,View convertview,ViewGroup parent)
    {
    if(convertview==null)
    {
    convertview= LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);
    }
    TextView item_name=(TextView)convertview.findViewById(R.id.spinner_item_name);
    String name=getItem(position);
    if(name!=null)
    {
        item_name.setText(name);
    }
    return convertview;
    }
}
