package com.example.supratim.ntes;
import android.annotation.SuppressLint;
import com.example.supratim.ntes.SecondActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.graphics.Color.WHITE;


public class output_activity extends AppCompatActivity {
    TableLayout tabLay;
    TableRow tabRow;
    Button button;
    String S="http://infotechsystems.in/android-api/ser.php?tag=T";
    SecondActivity sc=new SecondActivity();
    /*String id1=sc.getValue1();
    String id2=sc.getValue2();
    String id3=sc.getValue3();
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // Toast.makeText(getApplicationContext(), ((id1.concat(id2)).concat(id3)), Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_activity);
        tabLay=findViewById(R.id.tablay);
        button = findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                next_page(v);
            }
        } );
        fillspin();


       /* Toast.makeText(getApplicationContext(), S, Toast.LENGTH_SHORT).show();
*/
    }
    public void next_page(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    private void fillspin() {

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
                String id1=sc.getValue1();
                String id2=sc.getValue2();
                String id3=sc.getValue3();
                try {
                    if ((!id1.equals("0"))&&(!id1.equals(""))){
                        S=S.concat("&div_id=");
                        S=S.concat(id1);}
                    if (!id2.equals("0")&&(!id2.equals(""))){
                        S=S.concat("&dept_id=");
                        S=S.concat(id2);}
                    if (!id3.equals("0")&&(!id3.equals(""))){
                        S=S.concat("&center_id=");
                        S=S.concat(id3);}
                    //Toast.makeText(getApplicationContext(), S, Toast.LENGTH_SHORT).show();
                    URL url = new URL(S);
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


    @SuppressLint("NewApi")
    private void loadIntoListView2(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        if (jsonArray.length()==0)
            Toast.makeText(getApplicationContext(), "NO DATA AVAILABLE", Toast.LENGTH_LONG).show();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String a=obj.getString("division_nm")+"\n"+(obj.getString("dept_nm"));
            TableRow tbrow = new TableRow(this);
            tbrow.setBackground(getResources().getDrawable(R.drawable.borders));

            //tbrow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, 200));


            TextView t1v = new TextView(this);
            t1v.setText(a);
            t1v.setGravity(Gravity.CENTER);
            t1v.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
            t1v.setGravity(Gravity.CENTER_HORIZONTAL);
            t1v.setTextColor(WHITE);
            t1v.setHeight(130);
           //t1v.setBackground(getResources().getDrawable(
             //       R.drawable.borders));
            tbrow.addView(t1v);
            String b=obj.getString("center_nm")+"\n"+(obj.getString("date_range"));
            TextView t2v = new TextView(this);
            t2v.setText(b);
            t2v.setTextColor(WHITE);
            t2v.setGravity(Gravity.CENTER);
            t2v.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
            t2v.setGravity(Gravity.CENTER_HORIZONTAL);

            t2v.setHeight(130);
            //t2v.setBackground(getResources().getDrawable(
               //     R.drawable.borders));
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(obj.getString("train_nm"));
            t3v.setGravity(Gravity.CENTER_HORIZONTAL);
            t3v.setTextColor(WHITE);
            t3v.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
            t3v.setHeight(130);
            t3v.setPadding(0,0,10,0);
            //t3v.setBackground(getResources().getDrawable(
                 //   R.drawable.borders));
            tbrow.addView(t3v);
            tabLay.addView(tbrow);

/*            peopleArray.add(obj.getString("division_nm"));
            peopleArray.add(obj.getString("center_nm"));
            peopleArray.add(obj.getString("train_nm"));
            peopleArray.add(obj.getString("dept_nm"));
            peopleArray.add(obj.getString("date_range"));

            TextView txt1=(TextView)findViewById(R.id.div);
            TextView txt2=(TextView)findViewById(R.id.pl);
            TextView txt3=(TextView)findViewById(R.id.trname);
            TextView txt4=(TextView)findViewById(R.id.dept);
            TextView txt5=(TextView)findViewById(R.id.per);
            txt1.setText(obj.getString("division_nm"));
            //txt1.setText("\n");
            txt2.setText(obj.getString("center_nm"));
            //txt1.setText("\n");
            txt3.setText(obj.getString("train_nm"));
            txt4.setText(obj.getString("dept_nm"));
            txt5.setText(obj.getString("date_range"));


        }
    return peopleArray;}
    public void table() {


        String array = getPerson().toString();

        label1 = new TextView(this);

        label1.setText(array);

        tabRow = new TableRow(this);

        tabRow.addView(label1);

        tabLay.addView(tabRow);

*/

        }

 //       Toast.makeText(getApplicationContext(), S, Toast.LENGTH_SHORT).show();
}
}