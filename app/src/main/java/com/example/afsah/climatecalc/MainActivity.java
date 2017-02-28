package com.example.afsah.climatecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button1;

    TextView textView2;
    TextView textView4;
    TextView textView6;
    TextView textView8;
    TextView textView10;
    TextView textView12;
    TextView textView14;
    TextView textView16;
    TextView textView18;
    TextView textView20;

    EditText editText;

    URL url;

    String place;
    String result = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acti();
    }
    public void acti(){

        button1 = (Button)findViewById(R.id.button1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView4 = (TextView)findViewById(R.id.textView4);
        textView6 = (TextView)findViewById(R.id.textView6);
        textView8 = (TextView)findViewById(R.id.textView8);
        textView10 = (TextView)findViewById(R.id.textView10);
        textView12 = (TextView)findViewById(R.id.textView12);
        textView14 = (TextView)findViewById(R.id.textView14);
        textView16 = (TextView)findViewById(R.id.textView16);
        textView18 = (TextView)findViewById(R.id.textView18);
        textView20 = (TextView)findViewById(R.id.textView20);
        editText  = (EditText)findViewById(R.id.editText);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                textView2.setText("Loading...");
                try {
                    place = editText.getText().toString();
                    getData objclass = new getData(place);
                    objclass.execute(place).get();

                    double temp = Double.parseDouble(objclass.temp);
                    temp = temp - 273.15;
                    textView2.setText(Double.toString(temp));

                    double pressure = Double.parseDouble(objclass.pressure);
                    pressure *=0.1;
                    textView4.setText(Double.toString(pressure));

                    textView6.setText(objclass.humidity);

                    double temp_min = Double.parseDouble(objclass.temp_min);
                    temp_min = temp_min - 273.15;
                    textView8.setText(Double.toString(temp_min));

                    double temp_max = Double.parseDouble(objclass.temp_max);
                    temp_max = temp_max - 273.15;
                    textView10.setText(Double.toString(temp_max));

                    textView12.setText(objclass.speed);
                    textView14.setText(objclass.sunrise);
                    textView16.setText(objclass.sunset);
                    textView18.setText(objclass.description);
                    textView20.setText(objclass.country);

                    editText.setText(objclass.name);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
