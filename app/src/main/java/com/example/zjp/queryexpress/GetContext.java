package com.example.zjp.queryexpress;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zjp on 16-1-4.
 */
public class GetContext {

    private StringBuilder response;

    public String getResponse() {
        return response.toString();
    }

    public GetContext(String url){//给定访问的网址,返回网址内容
                HttpURLConnection connection=null;
                try{
                    URL myurl = new URL(url);
                    connection =(HttpURLConnection) myurl.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    String responseCode = connection.getResponseMessage();
                    connection =(HttpURLConnection) myurl.openConnection();
                    connection.setRequestMethod("GET");

                    connection.setConnectTimeout(8000);

                    connection.setReadTimeout(8000);

                    InputStream in=connection.getInputStream();
                    BufferedReader reader= new BufferedReader(new InputStreamReader(in));
                    response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);

                    }

                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
}
