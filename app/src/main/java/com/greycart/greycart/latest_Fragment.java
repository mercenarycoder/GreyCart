package com.greycart.greycart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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

//import static com.agri.agrimart.R.drawable.custom_tab4;

public class latest_Fragment  extends Fragment {
    RecyclerView accepted_recycler;
    RecyclerView finished_recycler;
    ArrayList<appointments_data_maker> list;
    ArrayList<appointments_data_maker> list2;
    latest_adapter adapter;
    finished_adapter adapter2;
    Context recyclerContext;
    Button latest,previous;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog2;
    ProgressBar p_accepted,p_ongoing;
    TextView t_accepted,t_ongoing;
    fragmentAsync async1;
    SwipeRefreshLayout change_all,change_all2;
    fragmentAsync2 async2;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //this is accepted list
        //list=new ArrayList<>();
        //list2=new ArrayList<>();
        //finished list will come here
        recyclerContext=this.getActivity();
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.latest_appointment,
                container, false);
        latest=getActivity().findViewById(R.id.latest);
        previous=getActivity().findViewById(R.id.previous);
        p_accepted=(ProgressBar)view.findViewById(R.id.loader_accepted);
        p_ongoing=(ProgressBar)view.findViewById(R.id.loader_ongoing);
        p_accepted.setVisibility(View.VISIBLE);
        p_ongoing.setVisibility(View.VISIBLE);
        t_accepted=(TextView)view.findViewById(R.id.showwhen_not);
        t_ongoing=(TextView)view.findViewById(R.id.showwhen_not_ongoing);
        change_all=(SwipeRefreshLayout)view.findViewById(R.id.chenge_all);
        change_all2=(SwipeRefreshLayout)view.findViewById(R.id.chenge_all2);
        accepted_recycler=(RecyclerView)view.findViewById(R.id.accepted_recycler);
        //new fragmentAsync().execute();
        //recycler 2 over here
        finished_recycler=(RecyclerView)view.findViewById(R.id.ongoing_recycler);
        //new fragmentAsync2().execute();
        change_all.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                change_all.setRefreshing(false);
                new fragmentAsync().execute();
                new fragmentAsync2().execute();
            }
        });
        change_all2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                change_all2.setRefreshing(false);
                new fragmentAsync().execute();
                new fragmentAsync2().execute();
            }
        });
        async1=new fragmentAsync();
        async2=new fragmentAsync2();
        async1.execute();
        async2.execute();
        return view;

    }
    public void executeInanother()
    {
        async1.execute();
        async2.execute();
    }

    @Override
    public void onPause() {
        if(async1.getStatus()==AsyncTask.Status.RUNNING)
        {
            async1.cancel(true);
        }
        if(async2.getStatus()==AsyncTask.Status.RUNNING)
        {
            async2.cancel(true);
        }
        super.onPause();

    }

    public   class fragmentAsync extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
            progressDialog=new ProgressDialog(recyclerContext);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            latest.setBackgroundResource(R.drawable.custom_tab4);
            previous.setBackgroundResource(R.drawable.cutom_tab2);
            //previous.setPressed(false);
            latest.setEnabled(false);
            previous.setEnabled(true);
        }

        @Override
        protected String doInBackground(String... strings) {

            String url="https://express.accountantlalaji.com/newapp/webapi/Orchidvendor/listofappointment";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.dashboardData(url,staff_id,"2");

            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressDialog.dismiss();
            p_accepted.setVisibility(View.INVISIBLE);
            if(s!=null)
            {
                try {
                    HashMap<String,String> month_teller=new HashMap<>();
                    month_teller.put("01","Jan");
                    month_teller.put("02","Feb");
                    month_teller.put("03","Mar");
                    month_teller.put("04","Apr");
                    month_teller.put("05","May");
                    month_teller.put("06","Jun");
                    month_teller.put("07","Jul");
                    month_teller.put("08","Aug");
                    month_teller.put("09","Sep");
                    month_teller.put("10","Oct");
                    month_teller.put("11","Nov");
                    month_teller.put("12","Dec");
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList name = new ArrayList<String>();
                    if(jsonArray.length()==0)
                    {
                        t_accepted.setVisibility(View.VISIBLE);
                        return;
                    }
                    list=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String cus_name=String.valueOf(object.get("customer_name"));
                        String cus_mobile=String.valueOf(object.get("customer_mobile"));
                        String appointment_id=String.valueOf(object.get("appointment_id"));
                        String s_id=String.valueOf(object.get("s_id"));
                        String payment_mode=String.valueOf(object.get("payment_mode"));
                        String order_no=String.valueOf(object.get("order_no"));
                        String grand_total=String.valueOf(object.get("grand_total"));
                        String book_date=String.valueOf(object.get("book_date"));
                        String time=String.valueOf(object.get("start_time"))+" to "+
                                String.valueOf(object.get("end_time"));
                        String slice[]=String.valueOf(object.get("appointment_date")).split("-");
                        String app_date=slice[0];
                        String app_date_mon=month_teller.get(slice[1]);
                        String day=String.valueOf(object.get("day"));
                        String pay_status=String.valueOf(object.get("payment_status"));
                        String book_time=String.valueOf(object.get("book_time"));
                        System.out.println(cus_name);
                        list.add(new appointments_data_maker(payment_mode,appointment_id,s_id,book_time,cus_name,cus_mobile,
                                book_date,app_date,app_date_mon,pay_status,order_no,grand_total,day,time));
                    }
                    adapter=new latest_adapter(list,recyclerContext);
                    accepted_recycler.setHasFixedSize(true);
                    accepted_recycler.setLayoutManager(new LinearLayoutManager(recyclerContext));
                    accepted_recycler.setAdapter(adapter);
                    t_accepted.setVisibility(View.INVISIBLE);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    t_accepted.setVisibility(View.VISIBLE);
                    Toast.makeText(recyclerContext,"No Orders",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                //list.add(new dashboard_Adapter("Client Name","Time:10:00 to 12:00",
                //      "23","jan"));
                //t_accepted.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"No Orders",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public  class fragmentAsync2 extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
          /*  progressDialog2=new ProgressDialog(recyclerContext);
            progressDialog2.setMessage("Data Loading");
            progressDialog2.setCanceledOnTouchOutside(false);
            progressDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog2.show();
           */
        }

        @Override
        protected String doInBackground(String... strings) {

            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofappointment";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.dashboardData(url,staff_id,"3");

            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            p_ongoing.setVisibility(View.INVISIBLE);
            if(s!=null)
            {
                try {
                    HashMap<String,String> month_teller=new HashMap<>();
                    month_teller.put("01","Jan");
                    month_teller.put("02","Feb");
                    month_teller.put("03","Mar");
                    month_teller.put("04","Apr");
                    month_teller.put("05","May");
                    month_teller.put("06","Jun");
                    month_teller.put("07","Jul");
                    month_teller.put("08","Aug");
                    month_teller.put("09","Sep");
                    month_teller.put("10","Oct");
                    month_teller.put("11","Nov");
                    month_teller.put("12","Dec");
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    ArrayList name = new ArrayList<String>();
                    if(jsonArray.length()==0)
                    {
                        t_ongoing.setVisibility(View.VISIBLE);
                        return;
                    }
                    list2=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String cus_name=String.valueOf(object.get("customer_name"));
                        String cus_mobile=String.valueOf(object.get("customer_mobile"));
                        String appointment_id=String.valueOf(object.get("appointment_id"));
                        String s_id=String.valueOf(object.get("s_id"));
                        String payment_mode=String.valueOf(object.get("payment_mode"));
                        String order_no=String.valueOf(object.get("order_no"));
                        String grand_total=String.valueOf(object.get("grand_total"));
                        String book_date=String.valueOf(object.get("book_date"));
                        String time=String.valueOf(object.get("start_time"))+" to "+
                                String.valueOf(object.get("end_time"));
                        String slice[]=String.valueOf(object.get("appointment_date")).split("-");
                        String app_date=slice[0];
                        String app_date_mon=month_teller.get(slice[1]);
                        String day=String.valueOf(object.get("day"));
                        String pay_status=String.valueOf(object.get("payment_status"));
                        String book_time=String.valueOf(object.get("book_time"));
                        System.out.println(cus_name);
                        list2.add(new appointments_data_maker(payment_mode,appointment_id,s_id,book_time,cus_name,cus_mobile,
                                book_date,app_date,app_date_mon,pay_status,order_no,grand_total,day,time));
                    }
                    finished_recycler.setHasFixedSize(true);
                    adapter2=new finished_adapter(list2,recyclerContext);
                    finished_recycler.setLayoutManager(new LinearLayoutManager(recyclerContext));
                    finished_recycler.setAdapter(adapter2);
                    t_ongoing.setVisibility(View.INVISIBLE);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    t_ongoing.setVisibility(View.VISIBLE);
                    Toast.makeText(recyclerContext,"No Orders",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                //list.add(new dashboard_Adapter("Client Name","Time:10:00 to 12:00",
                //      "23","jan"));
                //t_ongoing.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"No Orders",Toast.LENGTH_SHORT).show();
            }
            //latest.setPressed(true);
            //previous.setPressed(false);
            latest.setEnabled(true);
            previous.setEnabled(true);
        }
    }
}
