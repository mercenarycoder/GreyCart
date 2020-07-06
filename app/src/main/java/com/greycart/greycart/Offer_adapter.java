package com.greycart.greycart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Offer_adapter  extends RecyclerView.Adapter<Offer_adapter.viewholder1>{
    ArrayList<Offer_item_base> list;
    Context context;
    public Offer_adapter(ArrayList<Offer_item_base> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.offer_view_recycler, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, int position) {
        final Offer_item_base adapter=list.get(position);
        holder.offer_name.setText(" "+adapter.getOffer_name());
        holder.offer_date.setText(adapter.getOffer_date());
        holder.offer_mon.setText(adapter.offer_mon);
        holder.offer_code.setText("Offer Code : "+adapter.getOffer_code());
        holder.offer_status.setText(adapter.getOffer_status());
        if(adapter.getOffer_status().equals("Active"))
        {
            holder.offer_status.setBackgroundColor(Color.parseColor("#09C213"));
        }
        else
        {
            holder.offer_status.setBackgroundColor(Color.parseColor("#FFC21E13"));
        }
        holder.offer_use.setText(" Offer Uses : "+adapter.getOffer_uses());
        holder.offer_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(context,UpdateandViewOffer.class);
              intent.putExtra("offer_id",adapter.getOffer_id());
              context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        LinearLayout offer_view;
        TextView offer_code,offer_name,offer_status,offer_date,offer_mon,offer_use;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            offer_view=(LinearLayout)itemView.findViewById(R.id.click_offer);
            offer_code=(TextView)itemView.findViewById(R.id.offer_code);
            offer_name=(TextView)itemView.findViewById(R.id.offer_name);
            offer_status=(TextView)itemView.findViewById(R.id.offer_status);
            offer_date=(TextView)itemView.findViewById(R.id.offer_date);
            offer_mon=(TextView)itemView.findViewById(R.id.offer_mon);
            offer_use=(TextView)itemView.findViewById(R.id.offer_uses);
        }
    }
}
