package com.example.zjp.queryexpress.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zjp.queryexpress.GetContext;
import com.example.zjp.queryexpress.GetImage;
import com.example.zjp.queryexpress.Listitem;
import com.example.zjp.queryexpress.News_Adapter;
import com.example.zjp.queryexpress.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zjp on 16-1-4.
 */
public class NewsActivity extends AppCompatActivity {

    private final int SHOW_MESSAGE=1;
    private final int ShOW_IMAGE=2;

    private List<Listitem> list = new ArrayList<Listitem>();

    ImageView imageView;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsshow);
        init();
    }
    public void init(){
        imageView=(ImageView)findViewById(R.id.image_newsshow);
        TextView textView = (TextView)findViewById(R.id.text_newsshow);
         listView= (ListView) findViewById(R.id.list_newsshow);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        final String logo=intent.getStringExtra("logo");
        String num=intent.getStringExtra("num");
        textView.setText("快递单号:" + num );

        sendRestWidthHttpClient(name,num);

        new Thread(new Runnable() {
            @Override
            public void run() {
                GetImage getImage=new GetImage(logo);
                Message message = new Message();
                message.obj=getImage.getBitmap();
                message.what=ShOW_IMAGE;
                handler.sendMessage(message);
            }
        }).start();

    }


    private void sendRestWidthHttpClient(final String name, final String id){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                GetContext getContext = new GetContext("http://route.showapi.com/64-19?showapi_appid=14505&showapi_timestamp=" + df.format(new Date()) + "&showapi_sign=ff614c047dc8480ebabecc35d06178bd&com=" + name + "&nu=" + id);
                String response = getContext.getResponse();

                Message message = new Message();
                message.obj=response;
                message.what=SHOW_MESSAGE;
                handler.sendMessage(message);
            }
        }).start();
    }

    private Handler handler =new Handler() {

        public void handleMessage(Message msg){
            Log.d("abc","yes");
            switch (msg.what){
                case SHOW_MESSAGE:
                    String text = (String) msg.obj;
                    Log.d("abctext",text);
                    try {
                        JSONObject jsonObject= new JSONObject(text);

                        JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body") ;

                        String flag=showapi_res_body.getString("flag");
                        Log.d("abcflag", flag);

                        if(flag.equals("true")){
                            JSONArray data = showapi_res_body.getJSONArray("data");
                            for(int i=0;i<data.length();i++){
                                JSONObject jsonObject1 = data.getJSONObject(i);
                                String context=jsonObject1.getString("context");
                                String time= jsonObject1.getString("time");
                                int imageId,textcolor;
                                if(i==0) {
                                    imageId = R.drawable.dian2;
                                    textcolor = getResources().getColor(R.color.main_text);
                                }
                                else {
                                    imageId=R.drawable.dian1;
                                    textcolor=getResources().getColor(R.color.black);
                                }
                                list.add(new Listitem(imageId, context, time,textcolor));
                                Log.d("abc111",context+"  "+time);
                            }
                            News_Adapter adapter = new News_Adapter(NewsActivity.this,R.layout.listitem,list);
                            listView.setAdapter(adapter);
                        }
                        else {
                            Toast.makeText(NewsActivity.this,"查询不到",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case ShOW_IMAGE:Bitmap bitmap=(Bitmap)msg.obj;
                    imageView.setImageBitmap(bitmap);


            }
        }
    };
}
