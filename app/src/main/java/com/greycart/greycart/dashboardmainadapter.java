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

public class dashboardmainadapter  extends RecyclerView.Adapter<dashboardmainadapter.viewholder1>{
    ArrayList<appointments_data_maker> list;
    Context context;
    public dashboardmainadapter(ArrayList<appointments_data_maker> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context=parent.getContext();
    View inflator=LayoutInflater.from(context).inflate(R.layout.dashboard_final_recycler, parent,
        false);
    viewholder1 viewhold=new viewholder1(inflator);
    return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, final int position) {
        final appointments_data_maker adapter=list.get(position);
        holder.clientname.setText(" "+ adapter.getCus_name().toUpperCase());
        holder.date_num.setText(adapter.getApp_date());
        holder.date_mon.setText(adapter.getApp_date_mon());
        holder.order_no.setText("Order No : "+adapter.getOrder_no());
        holder.date_see.setText("Date : "+adapter.getBook_date());
        holder.customer_time_final.setText("Slot time : "+adapter.getTime());
        holder.amount.setText("Amount : "+adapter.getGrand_total());
        holder.click_krke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context,"soon u will view product",Toast.LENGTH_SHORT).show();
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());
           Intent intent=new Intent(context,order_view.class);
           intent.putExtra("appointment_id",adapter.getAppointment_id());
           intent.putExtra("buttons","allfine");
           intent.putExtra("2","status");
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
    TextView order_no,amount,date_see,customer_time_final;
    LinearLayout click_krke;
    public viewholder1(@NonNull View itemView) {
        super(itemView);
        date_mon=(TextView)itemView.findViewById(R.id.customer_date_mon_final2);
        date_num=(TextView)itemView.findViewById(R.id.customer_date_no_final2);
        clientname=(TextView)itemView.findViewById(R.id.customer_name_final2);
        order_no=(TextView)itemView.findViewById(R.id.order_no2);
        amount=(TextView)itemView.findViewById(R.id.amount2);
        date_see=(TextView)itemView.findViewById(R.id.date_see2);
        customer_time_final=(TextView)itemView.findViewById(R.id.customer_time_final2);
        click_krke=(LinearLayout)itemView.findViewById(R.id.click_krke);
    }
}
}
