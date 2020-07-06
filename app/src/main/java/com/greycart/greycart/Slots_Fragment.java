package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Slots_Fragment extends Fragment {
slot_adapter adapter;
RecyclerView slots;
ImageButton refresh_n_see_slots;
Button addslots;
ArrayList<slot_container_class> list;
SwipeRefreshLayout swipe_slots_refresh;
Context context;
TextView showwhen_no_slots;
ProgressDialog  progressDialog;
HashMap<String,String> day_number;
getSlotsInformation async1,async2;
int z=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context=this.getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
   View view=inflater.inflate(R.layout.slot_fragment,container,false);
    slots=(RecyclerView)view.findViewById(R.id.recycler_slot_details);
    showwhen_no_slots=(TextView)view.findViewById(R.id.showwhen_no_slots);
    swipe_slots_refresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe_slots_refresh);
    swipe_slots_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipe_slots_refresh.setRefreshing(false);
            async2=new getSlotsInformation();
            async2.execute();
        }
    });

    addslots=(Button)view.findViewById(R.id.add_slot_in);
    addslots.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent=new Intent(context, Real_Addslots.class);
        context.startActivity(intent);
        }
    });
    async1=new getSlotsInformation();
    async1.execute();
    return view;
    }

    @Override
    public void onPause() {
       /* if(async1.getStatus()==AsyncTask.Status.RUNNING)
        {
            async1.cancel(true);
        }
        if(async2.getStatus()==AsyncTask.Status.RUNNING)
        {
            async2.cancel(true);
        }*/
        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();
        if(z>0)
        new getSlotsInformation().execute();
    }

    public class getSlotsInformation extends AsyncTask<String,String,String>
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
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofslot";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
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
                    day_number=new HashMap<>();
                    day_number.put("Monday","1");
                    day_number.put("Tuesday","2");
                    day_number.put("Wednesday","3");
                    day_number.put("Thursday","4");
                    day_number.put("Friday","5");
                    day_number.put("Saturday","6");
                    day_number.put("Sunday","0");

                    JSONObject object=new JSONObject(s);
                    JSONArray array=object.getJSONArray("data");
                    list=new ArrayList<>();
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject object1=array.getJSONObject(i);
                        String slot_id=String.valueOf(object1.get("slot_id"));
                        String day_name=String.valueOf(object1.get("day_name"));
                        String start_time=String.valueOf(object1.get("start_time"));
                        String end_time=String.valueOf(object1.get("end_time"));
                        String status=String.valueOf(object1.get("status"));
                        String max_appointment=String.valueOf(object1.get("max_appointment"));
                        String day_repeate=String.valueOf(object1.get("day_repeate"));
                        String day_num=day_number.get(day_name);
                        list.add(new slot_container_class(day_name,start_time,end_time,max_appointment,
                                status,slot_id,day_num));
                    }
                    z=1;
                    adapter=new slot_adapter(list,context);
                    slots.setHasFixedSize(true);
                    slots.setLayoutManager(new LinearLayoutManager(context));
                    slots.setAdapter(adapter);
               showwhen_no_slots.setVisibility(View.INVISIBLE);
               showwhen_no_slots.setEnabled(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        JSONObject object = new JSONObject(s);
                        String responce=String.valueOf(object.get("msg"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update")
                                .setMessage(responce)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        showwhen_no_slots.setVisibility(View.VISIBLE);
                                        showwhen_no_slots.setEnabled(true);
                                    }
                                });
                        builder.create();
                        builder.show();
                    } catch (Exception e2)
                    {
                        Toast.makeText(context, "Some networking error try again later", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else
            {
          Toast.makeText(context,"No internet plz try again later",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
