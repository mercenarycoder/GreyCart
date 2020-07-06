package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class dashmainadapter2 extends RecyclerView.Adapter<dashmainadapter2.viewholder1>{
    ArrayList<list_prod2> list;
    Context context;
    SharedPreferences medi_pref;
    String staff_id;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;
    String status_get;
    String service_id_get;
    String cat_name;
    public dashmainadapter2(ArrayList<list_prod2> list, Context context,String staff_id,String name)
    {
        this.list=list;
        this.context=context;
        this.staff_id=staff_id;
       this.cat_name=name;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.productnew, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, final int position) {
        final list_prod2 adapter=list.get(position);
        medi_pref=context.getApplicationContext().
                getSharedPreferences("service_id",context.getApplicationContext().MODE_PRIVATE);
        editor=medi_pref.edit();

        holder.cat_name4.setText(adapter.getName());
        //String pp[]=adapter.getCat_count1().split(",");
        holder.on_off1.setText("₹ "+adapter.getRate());
        if(adapter.getStatus().equals("active"))
        {
            holder.on_off1.setChecked(true);
        }
        holder.on_off1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               service_id_get=adapter.getId();
                if(isChecked)
                {
                    status_get="active";
                }
                else
                {
                    status_get="deactive";
                }
                new updaterItem().execute();
            }
        });
        Picasso.with(context).load(adapter.getImg_link())
                .placeholder(R.drawable.circlecropped)
                .into(holder.cat_image3);
        //holder.cat_count.setText(adapter.getCat_count1());
        holder.click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, update_Service.class);
                intent.putExtra("prod_id",adapter.getId());
                intent.putExtra("cat_name",cat_name);
                context.startActivity(intent);
            }
        });
        if(!adapter.getName2().equals("zmpty"))
        {
            holder.cat_name5.setText(adapter.getName2());
            Picasso.with(context).load(adapter.getImg_link2())
                    .placeholder(R.drawable.circlecropped)
                    .into(holder.cat_image4);
            //holder.cat_count2.setText(adapter.getCat_count1());
            //String pp2[]=adapter.getCat_count2().split(",");
            holder.on_off2.setText("₹ "+adapter.getRate2());
            if(adapter.getStatus2().equals("active"))
            {
                holder.on_off2.setChecked(true);
            }
            holder.on_off2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    service_id_get=adapter.getId2();
                    if(isChecked)
                    {
                        status_get="active";
                    }
                    else
                    {
                        status_get="deactive";
                    }
                    new updaterItem().execute();
                }
            });
            holder.click4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, update_Service.class);
                    intent.putExtra("prod_id",adapter.getId2());
                    intent.putExtra("cat_name",cat_name);
                    //intent.putExtra("old_price",adapter.getOld_mrp2());
                    //intent.putExtra("",adapter)
                    context.startActivity(intent);
                }
            });
        }
        else
        {
            holder.click4.setVisibility(View.INVISIBLE);
            holder.click4.setEnabled(false);
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
        ImageView cat_image3,cat_image4;
        LinearLayout blank_catch;
        RelativeLayout click3,click4;
        //TextView cat_count,cat_count2;
        Switch on_off1,on_off2;
        TextView cat_name4,cat_name5;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            cat_image3=(ImageView) itemView.findViewById(R.id.cat_image3);
            cat_image4=(ImageView) itemView.findViewById(R.id.cat_image4);
            cat_name4=(TextView)itemView.findViewById(R.id.cat_name4);
            cat_name5=(TextView)itemView.findViewById(R.id.cat_name5);
            click3=(RelativeLayout)itemView.findViewById(R.id.click3);
            click4=(RelativeLayout)itemView.findViewById(R.id.click4);
            on_off1=(Switch)itemView.findViewById(R.id.on_off1);
            on_off2=(Switch)itemView.findViewById(R.id.on_off2);
            // cat_count2=(TextView)itemView.findViewById(R.id.cat_count2);
            //cat_count=(TextView)itemView.findViewById(R.id.cat_count);
        }
    }
    public class updaterItem extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/servicestatus";

            String res=new JsonParser().changingProduct(url,staff_id,service_id_get,status_get);
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object = new JSONObject(s);
                    String object1 = String.valueOf(object.get("msg"));
                    //  Toast.makeText(context,object1,Toast.LENGTH_SHORT).show();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage(object1)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // new latest_Fragment().executeInanother();
                                    //hare=true;
                                }
                            });
                    builder.create();
                    builder.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Update")
                            .setMessage("Some networking problem try again later")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                //    hare=false;
                                }
                            });
                    builder.create();
                    builder.show();

                    //Toast.makeText(context,"Data inconsistent check please",Toast.LENGTH_SHORT).show();
                }

            }
            else
            {

                Toast.makeText(context,"No internet plz try agaim later",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
