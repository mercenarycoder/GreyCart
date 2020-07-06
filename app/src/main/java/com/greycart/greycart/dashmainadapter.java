package com.greycart.greycart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class dashmainadapter extends RecyclerView.Adapter<dashmainadapter.viewholder1>{
    ArrayList<base_dashboard> list;
    Context context;
    SharedPreferences medi_pref;
    SharedPreferences.Editor editor;
    public dashmainadapter(ArrayList<base_dashboard> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.newcategory, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, final int position) {
        final base_dashboard adapter=list.get(position);
      medi_pref=context.getApplicationContext().
              getSharedPreferences("service_id",context.getApplicationContext().MODE_PRIVATE);
      editor=medi_pref.edit();
      if(adapter.getCat_name1().length()>15)
      {
          adapter.cat_name1=adapter.cat_name1.substring(0,14);
      }
      holder.cat_name2.setText(adapter.getCat_name1());
      if(!adapter.getCat_img1().equals("zmpty")) {
          Picasso.with(context).load(adapter.getCat_img1())
                  .placeholder(R.drawable.circlecropped)
                  .into(holder.cat_image);
      }
      holder.edit_me.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(context, Update_category.class);
              intent.putExtra("cat_id",adapter.getCat_id1());
              intent.putExtra("status",adapter.status1);
              intent.putExtra("name",adapter.getCat_name1());
              intent.putExtra("img",adapter.getCat_img1());
              intent.putExtra("position","3");
              context.startActivity(intent);
          }
      });
          //holder.cat_count.setText(adapter.getCat_count1());
      holder.click.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
           Intent intent=new Intent(context,productRecycler.class);
           intent.putExtra("ser_id",adapter.getCat_id1());
           intent.putExtra("cat_name",adapter.getCat_name1());
           context.startActivity(intent);
       //Toast.makeText(context,"Products will be here soon ",Toast.LENGTH_SHORT).show();
          }
      });
      if(!adapter.getCat_name2().equals("zmpty"))
      {
          if(adapter.getCat_name2().length()>15)
          {
              adapter.cat_name2=adapter.cat_name2.substring(0,14);
          }
          holder.cat_name3.setText(adapter.getCat_name2());
          if(!adapter.getCat_img1().equals("zmpty")) {
              Picasso.with(context).load(adapter.getCat_img2())
                      .placeholder(R.drawable.circlecropped)
                      .into(holder.cat_image2);
          }
          //holder.cat_count2.setText(adapter.getCat_count1());
          holder.click2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(context,productRecycler.class);
                  intent.putExtra("ser_id",adapter.getCat_id2());
                  intent.putExtra("cat_name",adapter.getCat_name2());
                  context.startActivity(intent);
              }
          });
          holder.edit_me2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(context, Update_category.class);
                  intent.putExtra("cat_id",adapter.getCat_id2());
                  intent.putExtra("status",adapter.status2);
                  intent.putExtra("name",adapter.getCat_name2());
                  intent.putExtra("img",adapter.getCat_img2());
                  intent.putExtra("position","3");
                  context.startActivity(intent);
              }
          });
      }
      else
      {
          holder.click2.setVisibility(View.INVISIBLE);
          holder.click2.setEnabled(false);
      }
//          holder.category_name2.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
// ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new medicine_fragment()).commit();
//                  editor.putString("id_service",adapter.getCat_id2());
//                  editor.putString("name_service",adapter.getCat_name2());
//                  editor.apply();
//              }
//          });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        ImageView cat_image,cat_image2;
        LinearLayout blank_catch;
        RelativeLayout click,click2;
        //TextView cat_count,cat_count2;
        ImageButton edit_me,edit_me2;
        TextView cat_name2,cat_name3;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            cat_image=(ImageView) itemView.findViewById(R.id.cat_image);
            cat_image2=(ImageView) itemView.findViewById(R.id.cat_image2);
           cat_name2=(TextView)itemView.findViewById(R.id.cat_name2);
           cat_name3=(TextView)itemView.findViewById(R.id.cat_name3);
           click=(RelativeLayout)itemView.findViewById(R.id.click);
           click2=(RelativeLayout)itemView.findViewById(R.id.click2);
           edit_me=(ImageButton)itemView.findViewById(R.id.edit_me);
           edit_me2=(ImageButton)itemView.findViewById(R.id.edit_me2);
           // cat_count2=(TextView)itemView.findViewById(R.id.cat_count2);
           //cat_count=(TextView)itemView.findViewById(R.id.cat_count);
        }
    }
}
