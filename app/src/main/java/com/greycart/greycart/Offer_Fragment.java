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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class Offer_Fragment extends Fragment {
    RecyclerView offer_offer;
    EditText offer_code_search;
    Button add_offer;
    ImageButton do_code_search;
    TextView no_offer_case;
    Spinner offer_spinner;
    ProgressDialog progressDialog;
    Context context;
    SpinnerAdapter2 adapter2;
    ArrayList<SpinnerClass> list2;
    ArrayList<Offer_item_base> list;
    Offer_adapter adapter;
    SwipeRefreshLayout refreshLayout;
    String code="",status_get="all";
    private boolean center=false,check_it=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context=this.getActivity();
        super.onCreate(savedInstanceState);
    }
    public void formList2()
    {
        list2=new ArrayList<>();
        list2.add(new SpinnerClass("all","All"));
        list2.add(new SpinnerClass("1","Active"));
        list2.add(new SpinnerClass("0","Deactive"));
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.offer_fragment, container, false);
      offer_offer=(RecyclerView)view.findViewById(R.id.offer_offers);
      no_offer_case=(TextView)view.findViewById(R.id.no_offer_case);
      do_code_search=(ImageButton) view.findViewById(R.id.do_code_search);
      add_offer=(Button)view.findViewById(R.id.add_offer);
      refreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_offer);
      offer_code_search=(EditText)view.findViewById(R.id.offer_code_search);
      offer_spinner=(Spinner)view.findViewById(R.id.offer_spinner);
      formList2();
        new getSlotsInformation().execute();
        adapter2=new SpinnerAdapter2(context,list2);
      offer_spinner.setAdapter(adapter2);
      offer_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              SpinnerClass spinnerClass=adapter2.getItem(position);
              status_get=spinnerClass.getId();
              if(status_get.equals("all")&&!center)
              {

              }
              else if(status_get.equals("all")&&center)
              {

                  new getSlotsInformation().execute();
              }
              else
              {
                  center=true;
                  new getSlotsInformation().execute();
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
      do_code_search.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             code=offer_code_search.getText().toString();
             new getSlotsInformation().execute();
          }
      });
      offer_code_search.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              offer_code_search.setText("");
          }
      });
      add_offer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(context,Add_Offer.class);
              context.startActivity(intent);
          }
      });
      refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
          @Override
          public void onRefresh() {
              refreshLayout.setRefreshing(false);
              new getSlotsInformation().execute();
          }
      });

      return view;
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
//offer_type->percentage,fixed , offer_fixed ="amount"
        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listoffer";
            JsonParser jsonParser=new JsonParser();
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

            String staff_id=preferences.getString("user_id2","2");
            String data=jsonParser.getOffers(url,staff_id,code,status_get);
            code="";
            //status_get="";
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
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
                    JSONObject object = new JSONObject(s);
                    JSONArray jsonArray = object.getJSONArray("data");
                    list=new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject object1 = jsonArray.getJSONObject(i);
                        String slice[]=String.valueOf(object1.get("offer_date")).split("-");
                        String month=month_teller.get(slice[1]);
                        String code=String.valueOf(object1.get("offer_code"));
                        String name=String.valueOf(object1.get("offer_name"));
                        String id=String.valueOf(object1.get("offer_id"));
                        String date=slice[0];
                        String uses=String.valueOf(object1.get("offer_uses"));
                        String status=String.valueOf(object1.get("offer_status"));
                        list.add(new Offer_item_base(id,code,name,date,month,uses,status));
                    }
                    if(list.size()==0)
                    {
                        no_offer_case.setVisibility(View.VISIBLE);
                        no_offer_case.setEnabled(true);
                    }
                    else
                    {
                        no_offer_case.setVisibility(View.INVISIBLE);
                        no_offer_case.setEnabled(false);
                    }
                    adapter=new Offer_adapter(list,context);
                    offer_offer.setLayoutManager(new LinearLayoutManager(context));
                    offer_offer.setHasFixedSize(true);
                    offer_offer.setAdapter(adapter);
                    check_it=true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        JSONObject object = new JSONObject(s);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert !!")
                                .setMessage(String.valueOf(object.get("msg")))
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        no_offer_case.setVisibility(View.VISIBLE);
                                        no_offer_case.setEnabled(true);
                                   if(check_it)
                                   {
                                       clearRecycler();
                                   }
                                    }
                                });
                        builder.create();
                        builder.show();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else
            {
                Toast.makeText(context,"No internet plz try again later",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void clearRecycler()
    {
        list.clear();
        adapter.notifyDataSetChanged();
    }
}
