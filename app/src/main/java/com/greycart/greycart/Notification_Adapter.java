package com.greycart.greycart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Notification_Adapter  extends RecyclerView.Adapter<Notification_Adapter.viewholder1>{
    ArrayList<Notification_baseclass> list;
    Context context;
    public Notification_Adapter(ArrayList<Notification_baseclass> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.notification_item, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, final int position) {
        final Notification_baseclass adapter=list.get(position);
        holder.msg_notification.setText(adapter.getMessage());
        holder.date_notification.setText(adapter.getDate());
        holder.time_notification.setText(adapter.getTime());
        holder.click_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("!!Notification!!")
                        .setMessage(adapter.getMessage())
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        LinearLayout click_notification;
        TextView date_notification,msg_notification,time_notification;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            date_notification=(TextView)itemView.findViewById(R.id.date_notification);
            msg_notification=(TextView)itemView.findViewById(R.id.msg_notification);
            time_notification=(TextView)itemView.findViewById(R.id.time_notification);
            click_notification=(LinearLayout)itemView.findViewById(R.id.click_notification);
        }
    }
}
