package com.greycart.greycart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class previous_adapter  extends RecyclerView.Adapter<previous_adapter.viewholder1>{
    ArrayList<appointments_data_maker> list;
    Context context;
    String appointment_id=null;
    public previous_adapter(ArrayList<appointments_data_maker> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public previous_adapter.viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator= LayoutInflater.from(context).inflate(R.layout.recycler_basic_3, parent,
                false);
        previous_adapter.viewholder1 viewhold=new previous_adapter.viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull previous_adapter.viewholder1 holder, int position) {
        final appointments_data_maker adapter=list.get(position);
        holder.clientname.setText(" "+adapter.getCus_name().toUpperCase());
        holder.time.setText("Time : "+adapter.getTime());
        holder.date_num.setText(adapter.getApp_date());
        holder.date_mon.setText(adapter.getApp_date_mon());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointment_id=adapter.getAppointment_id();
                Intent intent = new Intent(context,order_view.class);
                intent.putExtra("appointment_id",appointment_id);
                intent.putExtra("buttons","allnotfine2");
                intent.putExtra("0","status");
                context.startActivity(intent);

             //   Toast.makeText(context,"Accept is clicked",Toast.LENGTH_SHORT).show();
            }
        });
        holder.see_all_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointment_id=adapter.getAppointment_id();
                Intent intent = new Intent(context,order_view.class);
                intent.putExtra("appointment_id",appointment_id);
                intent.putExtra("buttons","allnotfine2");
                intent.putExtra("0","status");
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
        TextView date_mon,date_num;
        TextView time;
        TextView clientname;
        Button accept,view,cancel;
        LinearLayout see_all_me;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            date_mon=(TextView)itemView.findViewById(R.id.customer_date_mon_3);
            date_num=(TextView)itemView.findViewById(R.id.customer_date_no_3);
            time=(TextView)itemView.findViewById(R.id.customer_time_3);
            clientname=(TextView)itemView.findViewById(R.id.customer_name_3);
            //
            see_all_me=(LinearLayout)itemView.findViewById(R.id.see_all_me);
            view=(Button)itemView.findViewById(R.id.view_3);
            //cancel=(Button)itemView.findViewById(R.id.cancel);
        }
    }


    }
