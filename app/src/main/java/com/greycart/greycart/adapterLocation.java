package com.greycart.greycart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapterLocation  extends RecyclerView.Adapter<adapterLocation.viewholder1>{
    ArrayList<baseOptions> list;
    Context context;
    String place_id,lat2,lng2;
    ProgressDialog progressDialog;
    public adapterLocation(ArrayList<baseOptions> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.locationitem, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, final int position) {
        final baseOptions adapter=list.get(position);
        holder.location_place.setText(adapter.getDescription());
        holder.checky_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.sort_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           place_id=adapter.getPlace_id();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder1 extends RecyclerView.ViewHolder
    {
       TextView location_place;
       CheckBox checky_place;
       LinearLayout sort_place;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            location_place=(TextView)itemView.findViewById(R.id.location_place);
            checky_place=(CheckBox)itemView.findViewById(R.id.checky_place);
            sort_place=(LinearLayout)itemView.findViewById(R.id.sort_place);
        }
    }
    private class updateService extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        }

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null)
            {
                Intent intent=new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        }
    }
}

