package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Dialog_Adapter extends RecyclerView.Adapter<Dialog_Adapter.holder_class>{
ArrayList<list_prod> list;
Context context;
ProgressDialog progressDialog;
String staffer=null;
String service_id_get=null;
String status_get=null;
String see_check="";
boolean hare=false;
    public Dialog_Adapter(ArrayList<list_prod> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public holder_class onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
    Context conn = parent.getContext();
    View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycler_lower,
            parent, false);
    holder_class hold=new holder_class(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull holder_class holder, int position) {
    final list_prod prod=list.get(position);
    holder.prod_name.setText(prod.getName()+"    ");
    holder.prod_price.setText("Rs. "+prod.getRate()+"   ");
    String stat=prod.getStatus();
    if(stat.equals("active"))
    {
        holder.see_apply.setChecked(true);
    }
    holder.use_me_to_update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, update_Service.class);
            intent.putExtra("prod_id",prod.getId());
            intent.putExtra("cat_name",prod.getCat_name());
            context.startActivity(intent);
        }
    });

    holder.see_apply.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            staffer=prod.getStaff_id();
            service_id_get=prod.getId();
            if(isChecked)
            {
                status_get="active";
           // Toast.makeText(context,prod.getStaff_id(),Toast.LENGTH_SHORT).show();
            }
            else
            {
                status_get="deactive";
            //Toast.makeText(context,"I am removed again",Toast.LENGTH_SHORT).show();
            }
            new updaterItem().execute();
        }
    });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class holder_class extends RecyclerView.ViewHolder
    {
   TextView prod_name;
   TextView prod_price;
   Switch see_apply;
   LinearLayout use_me_to_update;
        public holder_class(@NonNull View itemView) {
            super(itemView);
          prod_name=(TextView)itemView.findViewById(R.id.product_name);
          prod_price=(TextView)itemView.findViewById(R.id.product_price);
          see_apply=(Switch)itemView.findViewById(R.id.include_item);
          use_me_to_update=(LinearLayout)itemView.findViewById(R.id.item_product);
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
            String res=new JsonParser().changingProduct(url,staffer,service_id_get,status_get);
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
                               hare=true;
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
                             hare=false;
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
