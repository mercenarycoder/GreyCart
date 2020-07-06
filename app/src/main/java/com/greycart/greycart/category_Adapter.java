package com.greycart.greycart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class category_Adapter extends RecyclerView.Adapter<category_Adapter.viewholder> {
    ArrayList<Category_handler> list;
    ArrayList<Integer> checker;
    Context context;
    ProgressDialog progressDialog;
    String product_id_get[];
    ArrayList<list_prod> list5;
    String staff_id;
    Dialog_Adapter[] dialogAdapter;
    viewholder viewholder34;
    Category_handler handle_it,handle_it2;
    int captcha=0;
    boolean babu=false;
    public category_Adapter(ArrayList<Category_handler> list, Context context) {
        this.list = list;
        this.context=context;
        checker=new ArrayList<>();
        product_id_get=new String[list.size()];
        for(int i=0;i<list.size();i++)
        {
            handle_it2=list.get(i);
            product_id_get[i]=handle_it2.getId();
            checker.add(0);
        }
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context con=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.category_recycler, parent, false);
        viewholder holder=new viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, final int position) {
        final Category_handler handler=list.get(position);
        handle_it=handler;

        final int[] check = {checker.get(position)};

        dialogAdapter = new Dialog_Adapter[1];

        holder.cat.setText(handler.getCat_name());

        staff_id=handler.getStaff_id();
        if(position==0 && !babu)
        {
            babu=true;
            viewholder34=holder;
            captcha=position;
            new getProducts().execute();
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
      if(check[0] %2==0){
          viewholder34=holder;
          captcha=position;
          new getProducts().execute();
          check[0] += 1;
          checker.add(position,check[0]);
      }
      else {
          ArrayList<list_prod> list3=new ArrayList<>();
          RecyclerView recycler_choices=holder.recyclerView;
          recycler_choices.setLayoutManager(new LinearLayoutManager(context));
          recycler_choices.setHasFixedSize(true);
          recycler_choices.setVisibility(View.VISIBLE);
          dialogAdapter[0] =new Dialog_Adapter(list3,context);
          recycler_choices.setAdapter(dialogAdapter[0]);
          check[0]+=1;
          checker.add(position,check[0]);
      }
        }
    });
    holder.add_things.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         Intent intent = new Intent(context,Add_Services.class);
         intent.putExtra("cat_id",handler.getId());
         intent.putExtra("cat_name",handler.getCat_name());
         context.startActivity(intent);
        }
    });
    holder.update_things.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(context, Update_category.class);
            intent.putExtra("cat_id",handler.getId());
            intent.putExtra("status",handler.getStatus());
            intent.putExtra("name",handler.getCat_name());
            intent.putExtra("position","3");
            context.startActivity(intent);
        }
    });
     }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{
TextView cat;
ImageView add_things,update_things;
RecyclerView recyclerView;
LinearLayout linearLayout;
    public viewholder(@NonNull View itemView) {
        super(itemView);
    cat=(TextView)itemView.findViewById(R.id.cat_name);
    linearLayout=(LinearLayout)itemView.findViewById(R.id.getcategory);
    add_things=(ImageView)itemView.findViewById(R.id.add_things);
    recyclerView=(RecyclerView)itemView.findViewById(R.id.recycler_attached);
    update_things=(ImageView)itemView.findViewById(R.id.update_things);
    }
}
public class getProducts extends AsyncTask<String,String,String>
{
    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Data Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String url="https://express.accountantlalaji.com/newapp/onlinelalaji/onlinelalajivendor/categoryview";
        String data=new JsonParser().getServices(url,product_id_get[captcha]);

        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(s!=null)
        {
            try {
                JSONObject object=new JSONObject(s);
                JSONObject object1=object.getJSONObject("data");
                JSONArray products=object1.getJSONArray("products");
            list5=new ArrayList<>();
                for(int j=0;j<products.length();j++)
            {
                JSONObject object2=products.getJSONObject(j);
                String product_id=String.valueOf(object2.get("product_id"));
                System.out.println("we are getting till here..."+product_id);

                String rate=String.valueOf(object2.get("rate"));
                String category_name=String.valueOf(object2.get("category_name"));
                String product_name=String.valueOf(object2.get("product_name"));
                String barcode=String.valueOf(object2.get("barcode"));
                String tax=String.valueOf(object2.get("tax"));
                String old_mrp=String.valueOf(object2.get("old_mrp"));
                String img=String.valueOf(object2.get("img"));
                String status2=String.valueOf(object2.get("status"));
                System.out.println(product_name);
                list5.add(new list_prod(product_name,product_id,barcode,img,status2,category_name,rate,
                        old_mrp,tax,staff_id));
            }
                if(list5.size()<=0)
                {
                    Toast.makeText(context,"No Items In These Category",Toast.LENGTH_SHORT).show();
                    return;
                }
                RecyclerView recycler_choices=viewholder34.recyclerView;
                recycler_choices.setLayoutManager(new LinearLayoutManager(context));
                recycler_choices.setHasFixedSize(true);
                recycler_choices.setVisibility(View.VISIBLE);
                dialogAdapter[0] =new Dialog_Adapter(list5,context);
                recycler_choices.setAdapter(dialogAdapter[0]);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"Some error in loading try again later",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(context,"No internet",Toast.LENGTH_SHORT).show();
        }
    }
}
}
