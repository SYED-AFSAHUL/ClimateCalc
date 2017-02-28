package com.example.afsah.climatecalc;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by afsah on 28-Feb-2017.
 */

public class getData extends AsyncTask<String, Integer, String> {

    URL url;

    String result = "";
    StringBuffer reqStr = new StringBuffer();
    String place;
    String temp,pressure,humidity,temp_min,temp_max,speed,sunrise,sunset,description,name,country;

    public getData(String place){
        this.place = place;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

            String urlstr = new String("http://api.openweathermap.org/data/2.5/weather?q="+
                    place + "&appid=d10e623992ad780085517dd010152cb6");
            // get URL content
            url = new URL(urlstr);
            URLConnection conn = url.openConnection();

            InputStream in = conn.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();

            while(data!=-1){
                char current = (char)data;
                result +=current;
                data=reader.read();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        try {

            JSONObject jsonObject = new JSONObject(result);

            JSONObject objMain = new JSONObject(jsonObject.getString("main"));

            temp = objMain.getString("temp");
            pressure = objMain.getString("pressure");
            humidity = objMain.getString("humidity");
            temp_min = objMain.getString("temp_min");
            temp_max = objMain.getString("temp_max");

            JSONObject objWind = new JSONObject(jsonObject.getString("wind"));
            speed = objWind.getString("speed");

            JSONObject objSys = new JSONObject(jsonObject.getString("sys"));
            sunrise = objSys.getString("sunrise");
            sunset = objSys.getString("sunset");

            JSONArray arrayWeather = jsonObject.getJSONArray("weather");
            JSONObject objWeather = arrayWeather.getJSONObject(0);
            description = objWeather.getString("description");

            name = jsonObject.getString("name");
            country = objSys.getString("country");
        }catch(Exception e){
            e.printStackTrace();
        }
        String str = new String(reqStr);
        return str;
    }
}
