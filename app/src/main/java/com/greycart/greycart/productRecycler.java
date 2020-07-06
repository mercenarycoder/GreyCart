package com.greycart.greycart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class productRecycler extends AppCompatActivity {
String id;
SwipeRefreshLayout swipe_products;
RecyclerView see_products;
TextView menu_product;
ImageView back_jao;
Button add_product;
ProgressDialog progressDialog;
Context context;
ArrayList<list_prod2> list;
ArrayList<sorter_Class2> win_sort;
String staff_id,name;
dashmainadapter2 adapter2;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_recycler);
    Intent intent=getIntent();
    context=productRecycler.this;
    id=intent.getStringExtra("ser_id");
    name=intent.getStringExtra("cat_name");
    swipe_products=(SwipeRefreshLayout)findViewById(R.id.swipe_products);
    see_products=(RecyclerView)findViewById(R.id.see_products);
    menu_product=(TextView)findViewById(R.id.menu_product);
    menu_product.setText("Category : "+name);
    back_jao=(ImageView)findViewById(R.id.back_jao);
    add_product=(Button)findViewById(R.id.add_product);
       SharedPreferences preferences=getApplicationContext().getSharedPreferences("login_details",
                       getApplicationContext().MODE_PRIVATE);

       staff_id=preferences.getString("user_id2","2");

       add_product.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,Add_Services.class);
            intent.putExtra("cat_id",id);
            intent.putExtra("cat_name",name);
            context.startActivity(intent);
        }
    });
    back_jao.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });

       swipe_products.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipe_products.setRefreshing(false);
            new getProducts().execute();
        }
    });
       new getProducts().execute();
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
            String data=new JsonParser().getServices(url,id);

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
                    list=new ArrayList<>();
                    win_sort=new ArrayList<>();
                    for(int j=0;j<products.length();j=j+1)
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
                        win_sort.add(new sorter_Class2(product_name,product_id,barcode,img,status2,rate,old_mrp,tax));
                    }
                    Collections.sort(win_sort, new Comparator<sorter_Class2>() {
                        @Override
                        public int compare(sorter_Class2 o1, sorter_Class2 o2) {
                            return (o1.name.compareTo(o2.name));
                        }
                    });
                   for(int i=0;i<win_sort.size();i=i+2)
                   {
                       if(i+1!=win_sort.size())
                       {
                       sorter_Class2 obj1=win_sort.get(i);
                       sorter_Class2 obj2=win_sort.get(i+1);
    list.add(new list_prod2(obj1.name,obj1.id,obj1.bar_code,obj1.img_link,obj1.status,obj1.rate,obj1.old_mrp,obj1.tax
    ,obj2.name,obj2.id,obj2.bar_code,obj2.img_link,obj2.status,obj2.rate,obj2.old_mrp,obj2.tax));
                       }
                       else
                       {
                           sorter_Class2 obj1=win_sort.get(i);
                           //sorter_Class2 obj2=win_sort.get(i+1);
                           list.add(new list_prod2(obj1.name,obj1.id,obj1.bar_code,obj1.img_link,obj1.status,obj1.rate,obj1.old_mrp,obj1.tax
                                   ,"zmpty","zmpty","zmpty","zmpty","zmpty",
                                   "zmpty","zmpty","zmpty"));
                       }
                   }
           if(list.size()<=0)
           {
               Toast.makeText(context,"No Products in This Category ",Toast.LENGTH_SHORT).show();
           }

           else
           {
               Collections.sort(list, new Comparator<list_prod2>() {
                   @Override
                   public int compare(list_prod2 o1, list_prod2 o2) {
                       return o1.name.compareTo(o2.name);
                   }
               });
               Collections.sort(list, new Comparator<list_prod2>() {
                   @Override
                   public int compare(list_prod2 o1, list_prod2 o2) {
                       return o1.name2.compareTo(o2.name2);
                   }
               });
               see_products.setLayoutManager(new LinearLayoutManager(context));
           see_products.setHasFixedSize(true);
           adapter2=new dashmainadapter2(list,context,staff_id,name);
           see_products.setAdapter(adapter2);
           }
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
