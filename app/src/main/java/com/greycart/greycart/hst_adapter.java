package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

public class hst_adapter extends RecyclerView.Adapter<hst_adapter.viewholder1>{
    ArrayList<hst_class> list;
    Context context;
    String staff_id;
    ProgressDialog progressDialog;
    String status_get;
    String service_id_get;
    String add_code="",add_rate="",add_desc="",add_id="";
    public hst_adapter(ArrayList<hst_class> list, Context context,String staff_id)
    {
        this.list=list;
        this.context=context;
        this.staff_id=staff_id;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.hsn_item, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, final int position) {
     final hst_class view=list.get(position);
     holder.item_name_hsn.setText(view.getHsn_name());
     holder.item_rate_hsn.setText(view.getHsn_rate()+" %");
     holder.item_edit_hst.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             add_id=view.getHsn_id();
            // Toast.makeText(context,"Soon edit will be here",Toast.LENGTH_SHORT).show();
             final Dialog dialog = new Dialog(context);
             dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
             dialog.setCancelable(false);
             dialog.setContentView(R.layout.add_hsn_item);
             dialog.show();
             Button done_done=(Button)dialog.findViewById(R.id.final_hsn_add);
             done_done.setText("Update Item");
             Button done_cancel=(Button)dialog.findViewById(R.id.final_hsn_cancel);
             final EditText code=(EditText)dialog.findViewById(R.id.hsn_name_add);
             code.setText(view.getHsn_name());
             TextView hsn_heading=(TextView)dialog.findViewById(R.id.hsn_heading);
             hsn_heading.setText("Update Item");
             final Spinner rate=(Spinner)dialog.findViewById(R.id.hsn_rate_add);
             final ArrayList<SpinnerClass> list3 = new ArrayList<>();
             list3.add(new SpinnerClass("5","5 %"));
             list3.add(new SpinnerClass("12","12 %"));
             list3.add(new SpinnerClass("14","14 %"));
             list3.add(new SpinnerClass("18","18 %"));
             list3.add(new SpinnerClass("28","28 %"));
             add_rate="5";
             if(Integer.parseInt(view.getHsn_rate())>5)
             {
                 int opt=0;
                 for(int i=0;i<list3.size();i++)
                 {
                     SpinnerClass check=list3.get(i);
                     if(Integer.parseInt(check.id)==Integer.parseInt(view.getHsn_rate()))
                     {
                         opt=i;
                     }
                 }
                 rate.setSelection(opt);
             }
             SpinnerAdapter2 ada3=new SpinnerAdapter2(context,list3);
             rate.setAdapter(ada3);
             rate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                 @Override
                 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     SpinnerClass ob=list3.get(position);
                     add_rate=ob.getId();
                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> parent) {

                 }
             });
             final EditText description=(EditText)dialog.findViewById(R.id.hsn_description_add);
             //rate.setText(view.getHsn_rate());

             description.setText(view.getHsn_description());
             done_done.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     add_code=code.getText().toString();
                    // add_rate=rate.getText().toString();
                     add_desc=description.getText().toString();
                     if(add_code.isEmpty())
                     {
                         Toast.makeText(context,"Please Enter Code to hsn",Toast.LENGTH_SHORT).show();
                         code.setError("enter code");
                     }
//                     else if(add_rate.isEmpty())
//                     {
//                         Toast.makeText(context,"Please Enter Code to hsn",Toast.LENGTH_SHORT).show();
//                         rate.setError("enter code");
//                     }
                     else
                     {
                         Toast.makeText(context,"Updating item",Toast.LENGTH_SHORT).show();
                         new updaterItem().execute();
                     }
                     dialog.dismiss();
                 }
             });
             done_cancel.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     dialog.dismiss();
                 }
             });

         }
     });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView item_name_hsn,item_rate_hsn;
        ImageView item_edit_hst;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            item_name_hsn=(TextView)itemView.findViewById(R.id.item_name_hsn);
            item_rate_hsn=(TextView)itemView.findViewById(R.id.item_rate_hsn);
            item_edit_hst=(ImageView)itemView.findViewById(R.id.item_edit_hst);
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
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/edithsn";

            String res=new JsonParser().updateHsnItem(url,add_id,add_code,add_rate,add_desc);
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
                                    add_id="";
                                    add_code="";
                                    add_rate="";
                                    add_desc="";
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
