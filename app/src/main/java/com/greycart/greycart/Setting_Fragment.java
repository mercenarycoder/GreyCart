package com.greycart.greycart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Setting_Fragment extends Fragment {
ScrollView scrollView;
BottomSheetBehavior behavior;
Button add_category;
RecyclerView recyclerView;
category_Adapter adapter;
ArrayList<Category_handler> list1;
ArrayList<list_prod> list2;
ProgressDialog progressDialog;
Context context;
SwipeRefreshLayout refreshLayout;
ImageButton refresh_n_see;
public String staff_id_for_switch;
comeCategories categories;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // list2=new ArrayList<>();
       // list1=new ArrayList<>();
        context=this.getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.support_main, container, false);
        add_category=(Button)view.findViewById(R.id.add_category);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_services);
        refreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_cats);
        //refresh_n_see=(ImageButton)view.findViewById(R.id.refresh_n_see);
        categories=new comeCategories();
        try {
            categories.execute();
        }
        catch (Exception e)
        {
         Toast.makeText(context,"Some error try again",Toast.LENGTH_SHORT).show();
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    new comeCategories().execute();
                }
                catch (Exception e)
                {
                    Toast.makeText(context,"Some error try again",Toast.LENGTH_SHORT).show();
                }
                refreshLayout.setRefreshing(false);
            }
        });
       add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        Intent intent = new Intent(getActivity(), AddCategory.class);
        startActivity(intent);
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        if(check_it) {
           // new comeCategories().execute();
        }
        super.onResume();
    }
    boolean check_it=false;
    @Override
    public void onPause() {
        super.onPause();
        if(categories.getStatus()==AsyncTask.Status.RUNNING)
        {
            categories.cancel(true);
            Toast.makeText(context,"Task cancel successfull",Toast.LENGTH_SHORT).show();

        }
        check_it=true;

    }
   private class comeCategories extends AsyncTask<String,String,String>
   {
       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           progressDialog=new ProgressDialog(context);
           progressDialog.setMessage("Please Wait \n");
           progressDialog.setCanceledOnTouchOutside(false);
           progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
           progressDialog.show();
       }

       @Override
       protected String doInBackground(String... strings) {
           String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofcategory";
           JsonParser jsonParser=new JsonParser();
           SharedPreferences preferences=getActivity().getApplicationContext().
                   getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

           String staff_id=preferences.getString("user_id2","2");
          staff_id_for_switch=staff_id;
           String data=jsonParser.getCategories(url,staff_id);

           return data;
       }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);
           progressDialog.dismiss();
           if(s!=null)
           {
               try {
                    JSONObject object = new JSONObject(s);
               JSONArray jsonArray =object.getJSONArray("data");
               list1=new ArrayList<>();
               for(int i=0;i<jsonArray.length();i++)
               {
                   JSONObject object1 = jsonArray.getJSONObject(i);
                  String name= String.valueOf(object1.get("category_name"));
                  String id=String.valueOf(object1.get("category_id"));
                  String status =String.valueOf(object1.get("status"));
                 // JSONArray products=object1.getJSONArray("products");
                   list2=new ArrayList<>();

                list1.add(new Category_handler(name,id,status,staff_id_for_switch));
               }
                   adapter=new category_Adapter(list1,context);
                   recyclerView.setHasFixedSize(true);
                   recyclerView.setLayoutManager(new LinearLayoutManager(context));
                   recyclerView.setAdapter(adapter);

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
           else
           {
        Toast.makeText(context,"Check your internet connection and refresh",Toast.LENGTH_SHORT).show();
           }
       }
   }
}
