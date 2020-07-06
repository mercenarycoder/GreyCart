package com.greycart.greycart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Dashboard_Fragment  extends Fragment {

    ArrayList<appointments_data_maker> list;
    RecyclerView recyclerView;
    Context recyclerContext;
    dashboardmainadapter adapter_view;
    ProgressDialog progressDialog;
    TextView viewmore,showwhen_no_appointment;
    ImageButton services_dash,appointment_dash,head_dash;
    TextView toolname;
    SwipeRefreshLayout refreshLayout;
    private NotificationManagerCompat notificationManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        recyclerContext=this.getActivity();
        //list=new ArrayList<>();

       // notificationManager = NotificationManagerCompat.from(this.getActivity());
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.dashboard_fragment,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler);
        new fragmentAsync().execute();
        new updateNotification().execute();
      services_dash=(ImageButton)view.findViewById(R.id.services_dash);
      appointment_dash=(ImageButton)view.findViewById(R.id.appointment_dash);
      head_dash=(ImageButton)view.findViewById(R.id.head_dash);
      toolname=(TextView)getActivity().findViewById(R.id.menu);
      viewmore=(TextView)view.findViewById(R.id.view_more);
      refreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_to_get_appointment);
      showwhen_no_appointment=(TextView)view.findViewById(R.id.showwhen_no_appointment);
      refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
          @Override
          public void onRefresh() {
              refreshLayout.setRefreshing(false);
              //list.clear();
              new fragmentAsync().execute();
              new updateNotification().execute();
          }
      });
     toolname.setText("Dashboard");
      services_dash.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
       getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Services_Fragement()).commit();
         toolname.setText("Setting");
              new Dashboard().count=0;
          }
      });
      appointment_dash.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
  getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new CategoryFragment()).commit();
  toolname.setText("Categories");
  new Dashboard().count=0;
          }
      });
      head_dash.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
 getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Support_fragment()).commit();
 toolname.setText("Support");
              new Dashboard().count=0;
          }
      });
      viewmore.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Appointment_fragment()).commit();
              toolname.setText("Orders");
              new Dashboard().count=0;
          }
      });
        return view;
    }

    @Override
    public void onResume() {
        if(check_it) {
            new fragmentAsync().execute();
            new updateNotification().execute();
        }
            super.onResume();
    }
   boolean check_it=false;
    @Override
    public void onPause() {
        super.onPause();
        check_it=true;
    }

    private  class fragmentAsync extends AsyncTask<String,String,String>
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
        }

        @Override
        protected String doInBackground(String... strings) {

            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofappointment";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.dashboardData(url,staff_id,"1");

            return data;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

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
                   //here these 2 lines of code was altered by me because from server itsel
                   //appointment_date was book_date and book_date was appointment_date
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
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerContext));
                    //preparing adapter
                    adapter_view=new dashboardmainadapter(list,recyclerContext);
                    //assigning adapter
                    recyclerView.setAdapter(adapter_view);
                    showwhen_no_appointment.setVisibility(View.INVISIBLE);
                    showwhen_no_appointment.setEnabled(false);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                     showwhen_no_appointment.setVisibility(View.VISIBLE);
                     showwhen_no_appointment.setEnabled(true);
                    Toast.makeText(recyclerContext, "No New Orders",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                //list.add(new dashboard_Adapter("Client Name","Time:10:00 to 12:00",
                  //      "23","jan"));
                Toast.makeText(getContext(),"No New Orders",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class updateNotification extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/webapi/orchidvendor/notificationupdate";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.notifications(url,staff_id);

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
                    String msg=String.valueOf(object.get("msg"));
                   // Toast.makeText(recyclerContext,msg,Toast.LENGTH_SHORT).show();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
