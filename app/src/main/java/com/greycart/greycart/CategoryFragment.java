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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CategoryFragment extends Fragment
{
    Context context;
    ArrayList<base_dashboard> list;
    ArrayList<sorter_Class> win_sort;
    dashmainadapter adapter;
    RecyclerView recyclerView;
    Button add_category;
    SwipeRefreshLayout refreshLayout;
   comeCategories categories;
   ProgressDialog progressDialog;
   String staff_id_for_switch;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       context=getContext();

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
            new comeCategories().execute();
        }
        super.onResume();
    }
    boolean check_it=false;
    @Override
    public void onPause() {
        super.onPause();
        if(categories.getStatus()== AsyncTask.Status.RUNNING)
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
                    list=new ArrayList<>();
                    win_sort=new ArrayList<>();
 String img_url="zmpty";
 String img_url2="zmpty";
                    for(int i=0;i<jsonArray.length();i=i+1)
                    {
                        JSONObject object1 = jsonArray.getJSONObject(i);
                        String name= String.valueOf(object1.get("category_name"));
                        String id=String.valueOf(object1.get("category_id"));
                       img_url=String.valueOf(object1.get("img"));
                       // String cat_image=String.valueOf(object1.get("category_image"));
                        String status =String.valueOf(object1.get("status"));
                        // JSONArray products=object1.getJSONArray("products");
                        win_sort.add(new sorter_Class(name,id,img_url,status));
                    }

                    Collections.sort(win_sort, new Comparator<sorter_Class>() {
                        @Override
                        public int compare(sorter_Class o1, sorter_Class o2) {
                            return (o1.name.compareTo(o2.name));
                        }
                    });

                    for(int i=0;i<win_sort.size();i=i+2)
                    {
                        if(i+1!=win_sort.size())
                        {
                       sorter_Class obj1=win_sort.get(i);
                       sorter_Class obj2=win_sort.get(i+1);
                       list.add(new base_dashboard(obj1.getId(),obj2.getId(),obj1.getName(),obj2.getName(),obj1.getImg(),
                               obj2.getImg(),"!2 Products","!2 Products",obj1.getStatus(),obj2.getStatus()));
                        }
                        else
                        {
                            sorter_Class obj1=win_sort.get(i);
                            //sorter_Class obj2=win_sort.get(i+1);
                            list.add(new base_dashboard(obj1.getId(),"zmpty",obj1.getName(),"zmpty",obj1.getImg(),
                                    "zmpty","!2 Products","!2 Products",obj1.getStatus(),"zmpty"));
                        }
                    }
                    adapter=new dashmainadapter(list,context);
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
