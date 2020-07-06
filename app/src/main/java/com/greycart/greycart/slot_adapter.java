package com.greycart.greycart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class slot_adapter  extends RecyclerView.Adapter<slot_adapter.viewholder1>{
    ArrayList<slot_container_class> list;
    Context context;
    public slot_adapter(ArrayList<slot_container_class> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.solt_view, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, int position) {
        final slot_container_class adapter=list.get(position);
       // holder.slot_id_no.setText(adapter.getSlot_id());
        holder.start_slot_no.setText(" "+adapter.getStart_time()+" - ");
        String day=adapter.getDay();
        if(!day.equals(""))
        {
            day=day.substring(0,3);
        }

        holder.day_week.setText(day);
        holder.end_slot_no.setText(adapter.getEnd_time());
        holder.max_app_slot_no.setText(adapter.getMax_appointment());
        holder.status_slot_no.setText(adapter.getStatus_get());

        holder.edit_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(context, add_slots_activity.class);
            intent.putExtra("slot_id",adapter.getSlot_id());
            intent.putExtra("start_time",adapter.getStart_time());
            intent.putExtra("end_time",adapter.getEnd_time());
            intent.putExtra("max_miller",adapter.getMax_appointment());
            intent.putExtra("status",adapter.getStatus_get());
            intent.putExtra("day_num",adapter.getDay_num());
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
        ImageButton edit_slot,delete_slot;
        TextView start_slot_no,end_slot_no,slot_id_no,status_slot_no,max_app_slot_no,day_week;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
        start_slot_no=(TextView)itemView.findViewById(R.id.start_slot_no);
        end_slot_no=(TextView)itemView.findViewById(R.id.end_slot_no);
        //slot_id_no=(TextView)itemView.findViewById(R.id.slot_id_no);
        day_week=(TextView)itemView.findViewById(R.id.day_week);
        status_slot_no=(TextView)itemView.findViewById(R.id.status_slot_no);
        max_app_slot_no=(TextView)itemView.findViewById(R.id.max_app_slot_no);
        edit_slot=(ImageButton)itemView.findViewById(R.id.edit_slot);
      //  delete_slot=(ImageButton)itemView.findViewById(R.id.delete_slot);

        }
    }
}
