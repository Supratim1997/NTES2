package com.example.supratim.ntes;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SecondActivity extends AppCompatActivity {
    Spinner sf,sf2,sf3;Button button,button2;
    String[] as;
    public static String did="",did2="",did3="";        String[] ids,cids;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
       // String[] arraySpinner = new String[] {
         //      "1", "2", "3", "4", "5"};
        did="";did2="";did3="";
        sf=(Spinner)findViewById(R.id.spin_Division);
        sf2=(Spinner)findViewById(R.id.spin_Department);
        sf3=(Spinner)findViewById(R.id.spin_TrCentre);
        button = (Button) findViewById(R.id.out);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                next_page(v);
            }
        } );
        button2 = (Button) findViewById(R.id.backmain);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                next_page2(v);
            }
        } );
        getJSON("http://infotechsystems.in/android-api/ser.php?tag=D");
        //did=id[sf.getSelectedItemPosition()];
        fillspin("http://infotechsystems.in/android-api/ser.php?tag=C");
        sf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                did=ids[sf.getSelectedItemPosition()];
                //TextView textView=(TextView)findViewById(R.id.txt);
                //textView.setText(ids[sf.getSelectedItemPosition()]);
                //TextView tx=(TextView)findViewById(R.id.textView);
                //tx.setText(did);

                switch (did){
                    case "1":
                        getJSON("http://infotechsystems.in/android-api/ser.php?tag=P&div_id=1");
                        break;
                    case "2":
                        getJSON("http://infotechsystems.in/android-api/ser.php?tag=P&div_id=2");
                        break;
                    case "3":
                        getJSON("http://infotechsystems.in/android-api/ser.php?tag=P&div_id=3");
                        break;
                    case "4":
                        getJSON("http://infotechsystems.in/android-api/ser.php?tag=P&div_id=4");
                        break;
                    case "500":
                        getJSON("http://infotechsystems.in/android-api/ser.php?tag=P&div_id=500");
                        break;
                    case "6":
                        getJSON("http://infotechsystems.in/android-api/ser.php?tag=P&div_id=6");
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                //sf.setPrompt("select");
            }

        });
        sf2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                did2=as[sf2.getSelectedItemPosition()];
//                TextView tx=(TextView)findViewById(R.id.textView);
  //              tx.setText(did2);
               }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        sf3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                did3=cids[sf3.getSelectedItemPosition()];
    //            TextView tx=(TextView)findViewById(R.id.textView);
      //          tx.setText(did3);

                }


            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


    }
    public static String getValue1(){
        return (did);
    }
    public static String getValue2(){
        return (did2);
    }
    public static String getValue3(){
        return (did3);
    }

    public void next_page(View v) {
        if(did.equals("0")||did3.equals("0")||did2.equals("0"))
             Toast.makeText(getApplicationContext(), "Fields cannot be left empty", Toast.LENGTH_LONG).show();
        else{
            Intent intent = new Intent(this,output_activity.class);
        startActivity(intent);
        }}

    public void next_page2(View v) {
        Intent intent = new Intent(this,training_second.class);
        startActivity(intent);
    }
    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);
               // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s,did);
                } catch (JSONException e) {
                //    e.printStackTrace();

                }
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json,String dd) throws JSONException {
        if(dd.equalsIgnoreCase("")){
        JSONArray jsonArray = new JSONArray(json);
        String[] tag = new String[jsonArray.length()+1];
        ids=new String[jsonArray.length()+1];
        tag[0]="Select A Division";
        ids[0]="0";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            tag[i+1] = obj.getString("division_nm");
            ids[i+1]= obj.getString("div_id");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tag);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sf.setAdapter(arrayAdapter);
    }
    else{
            JSONArray jsonArray = new JSONArray(json);
            String[] tag = new String[jsonArray.length()+1];
            as=new String[jsonArray.length()+1];
            tag[0]="Select A Department";
            as[0]="0";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                tag[i+1] = obj.getString("dept_nm");
                as[i+1]= obj.getString("dept_id");
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tag);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            sf2.setAdapter(arrayAdapter);
        }
    }
    private void fillspin(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);
                 //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView2(s);
                } catch (JSONException e) {
                    //    e.printStackTrace();

                }
            }
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON fillspin = new GetJSON();
        fillspin.execute();
    }


    private void loadIntoListView2(String json) throws JSONException {
            JSONArray jsonArray = new JSONArray(json);
            String[] tag = new String[jsonArray.length()+1];
            cids=new String[jsonArray.length()+1];
            tag[0]="Select a Training Center";
            cids[0]="0";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                tag[i+1] = obj.getString("center_nm");
                cids[i+1]= obj.getString("center_id");
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tag);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            sf3.setAdapter(arrayAdapter);
            check=1;



    }
    }






