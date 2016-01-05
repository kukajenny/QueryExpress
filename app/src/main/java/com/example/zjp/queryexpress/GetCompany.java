package com.example.zjp.queryexpress;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjp on 16-1-5.
 */
public class GetCompany {

    private List list;
    private Map map;


    public List getList() {
        return list;
    }

    public Map getMap() {
        return map;
    }

    public GetCompany(Context context,int id){

        list=new ArrayList();
        map = new HashMap();

        //得到资源中的Raw数据流
        InputStream in = context.getResources().openRawResource(id);

        /* 获取文件的大小(字节数) */
        int length = 0;
        JSONArray expressList = null;
        try {
            length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            //String result = EncodingUtils.getString(buffer, "UTF-8");
            String text = new String(buffer,"utf-8");
            Log.d("abc", text);
            Log.d("abc","Yes");

            JSONObject jsonObject= new JSONObject(text);

            JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body") ;

            expressList= showapi_res_body.getJSONArray("expressList");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for(int i=0;i<expressList.length();i++){

            JSONObject jsonObject1= null;
            try {
                jsonObject1 = expressList.getJSONObject(i);
                String name=jsonObject1.getString("expName");
                String simpleName=jsonObject1.getString("simpleName");
                String logo=jsonObject1.getString("imgUrl");
                list.add(name);
                Express express=new Express();
                express.setLogo(logo);
                express.setSimpleName(simpleName);
                map.put(name, express);
                Log.d("abc",name+"   "+simpleName+"   "+logo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
