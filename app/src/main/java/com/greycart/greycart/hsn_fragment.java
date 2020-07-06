package com.greycart.greycart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class hsn_fragment extends Fragment {
    RecyclerView recycler_hsn;
    hst_adapter adapter;
    ArrayList<hst_class> list;
    ProgressDialog progressDialog;
    Context context;
    Button item_hsn_add;
    SwipeRefreshLayout swipe_hsn;
    String add_code="",add_rate="",add_desc="";
    String staff_id;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hsn_fragment, container, false);
        recycler_hsn=(RecyclerView)view.findViewById(R.id.recycler_hsn);
        item_hsn_add=(Button)view.findViewById(R.id.item_hsn_add);
        swipe_hsn=(SwipeRefreshLayout)view.findViewById(R.id.swipe_hsn);
        swipe_hsn.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_hsn.setRefreshing(false);
                new gethsn().execute();
            }
        });
        item_hsn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.add_hsn_item);
                dialog.show();
                Button done_done=(Button)dialog.findViewById(R.id.final_hsn_add);
                Button done_cancel=(Button)dialog.findViewById(R.id.final_hsn_cancel);
                final EditText code=(EditText)dialog.findViewById(R.id.hsn_name_add);
                final Spinner rate=(Spinner)dialog.findViewById(R.id.hsn_rate_add);
                final ArrayList<SpinnerClass> list3 = new ArrayList<>();
                list3.add(new SpinnerClass("5","5 %"));
                list3.add(new SpinnerClass("12","12 %"));
                list3.add(new SpinnerClass("18","18 %"));
                list3.add(new SpinnerClass("28","28 %"));
                add_rate="5";
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
                done_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_code=code.getText().toString();
                        //add_rate=rate.getText().toString();
                        add_desc=description.getText().toString();
                        if(add_code.isEmpty())
                        {
                Toast.makeText(context,"Please Enter Code to hsn",Toast.LENGTH_SHORT).show();
                code.setError("enter code");
                        }
//                        else if(add_rate.isEmpty())
//                        {
//                            Toast.makeText(context,"Please Enter Code to hsn",Toast.LENGTH_SHORT).show();
//                            rate.setError("enter code");
//                        }
                        else
                        {
                            Toast.makeText(context,"Adding item",Toast.LENGTH_SHORT).show();
                            new addHsn().execute();
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
        new gethsn().execute();
        return view;
    }

    private class addHsn extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/addhsn";
            String data=new JsonParser().addHsnItem(url,staff_id,add_code,add_rate,add_desc);
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
                                    add_code="";
                                    //add_rate="";
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

                }
            }
            else
            {
      Toast.makeText(context,"Internet Issues",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class gethsn extends AsyncTask<String,String,String>
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
                String url="https://express.accountantlalaji.com/newapp/onlinelalaji/Greycartvendor/listofhsn";
                JsonParser jsonParser=new JsonParser();
                SharedPreferences preferences=getActivity().getApplicationContext().
                        getSharedPreferences("login_details",getActivity().getApplicationContext().MODE_PRIVATE);

                staff_id=preferences.getString("user_id2","2");
                String data=jsonParser.getCategories(url,staff_id);

                return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null) {
                try {
                    JSONObject obj=new JSONObject(s);
                    JSONArray array=obj.getJSONArray("data");
                    list=new ArrayList<>();
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject item=array.getJSONObject(i);
                        String hsn_id=String.valueOf(item.get("id"));
                        String hsn_code=String.valueOf(item.get("hsn_code"));
                        String hsn_rate=String.valueOf(item.get("hsn_rate"));
                        String hsn_des=String.valueOf(item.get("hsn_description"));
                        list.add(new hst_class(hsn_id,hsn_code,hsn_rate,hsn_des));
                    }
                    adapter = new hst_adapter(list, context, staff_id);
                    recycler_hsn.setLayoutManager(new LinearLayoutManager(context));
                    recycler_hsn.setHasFixedSize(true);
                    recycler_hsn.setAdapter(adapter);
                }
                catch (Exception e)
                {
            Toast.makeText(context,"Some error",Toast.LENGTH_SHORT).show();
                }
            }
        else
            {
                Toast.makeText(context,"Internet Not Available",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
