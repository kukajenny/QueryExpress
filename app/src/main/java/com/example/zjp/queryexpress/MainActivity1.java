package com.example.zjp.queryexpress;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity1 extends AppCompatActivity implements View.OnClickListener{

    private EditText editText_id;
    private AutoCompleteTextView edittext_name;
    private TextView textView;
    private TextInputLayout textInputLayout_id;
    private final int SHOW_MESSAGE=1;
    private final int GET_COMAPNY=2;
    List list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init(){
        list=new ArrayList();
        textView = (TextView)findViewById(R.id.text);
        editText_id = (EditText)findViewById(R.id.edittext_id);
        edittext_name = (AutoCompleteTextView)findViewById(R.id.edittext_name);
        textInputLayout_id=(TextInputLayout)findViewById(R.id.textinputlayout_id);
        Button button_query=(Button)findViewById(R.id.button_query);
        button_query.setOnClickListener(this);

        editText_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //检测错误输入，当输入错误时，hint会变成红色并提醒
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //检查实际是否匹配，由自己实现
                if (checkType(charSequence.toString())) {
                    textInputLayout_id.setErrorEnabled(true);
                    textInputLayout_id.setError("请检查格式");
                    return;
                } else {
                    textInputLayout_id.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getcompany();

        ArrayAdapter arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        edittext_name.setAdapter(arrayAdapter);
    }

    public boolean checkType(String check){
        for(int i=0;i<check.length();i++){
            if(check.charAt(i)>'9'||check.charAt(i)<'0')return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        String string_editext_name = edittext_name.getText().toString();
        String string_edittext_id= editText_id.getText().toString();
        //sendRestWidthHttpClient(string_editext_name, string_edittext_id);

    }

    private Handler handler =new Handler() {

        public void handleMessage(Message msg){
            Log.d("abc","yes");
            switch (msg.what){
                case SHOW_MESSAGE:
                    String text = (String) msg.obj;
                    try {
                        JSONArray jsonArray=new JSONArray(text);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String message=jsonObject.getString("message");
                            String updatetime= jsonObject.getString("updatetime");
                            JSONObject result=jsonObject.getJSONObject("result");
                            String ftime = result.getString("ftime");
                            String location = result.getString("location");
                            String context = result.getString("context");
                            String time = result.getString("time");
                            textView.append(updatetime);
                            textView.append(ftime);
                            textView.append(location);
                            textView.append(context);
                            textView.append(time);
                        }
                            textView.setText(text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case GET_COMAPNY:


            }
        }
    };
    public void getcompany(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                String res = "";

                //得到资源中的Raw数据流
                InputStream in = getResources().openRawResource(R.raw.company);
                Log.d("abc","yes");
              /* 获取文件的大小(字节数) */
                int length = 0;
                try {
                    length = in.available();
                    byte[] buffer = new byte[length];
                    String result = EncodingUtils.getString(buffer, "UTF-8");
                    String text = new String(buffer);
                    Log.d("abc",text);

                    JSONObject jsonObject= new JSONObject(text);

                    JSONObject showapi_res_body = jsonObject.getJSONObject("showapi_res_body") ;

                    JSONArray expressList = showapi_res_body.getJSONArray("expressList");

                    for(int i=0;i<expressList.length();i++){

                        JSONObject jsonObject1= expressList.getJSONObject(i);
                        String name=jsonObject1.getString("expName");
                        list.add(name);
                        Log.d("abc",name);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    /*private void sendRestWidthHttpClient(final String name, final String id){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                try{
                    Log.d("abc","yes1");
                    //URL url=new URL("http://route.showapi.com/64-20?showapi_appid=14505&showapi_timestamp="++"&showapi_sign=79154B278E98C6279F43B44E41A0F267");
                    connection =(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    OutputStream out=connection.getOutputStream();
                    String post="id=" + id + "&logistics=" + name;
                    out.write(post.toString().getBytes("utf-8"));
                    out.flush();
                    out.close();

                    String responseCode = connection.getResponseMessage();
                    connection =(HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");

                        connection.setConnectTimeout(8000);

                        connection.setReadTimeout(8000);

                        InputStream in=connection.getInputStream();
                    Log.d("abc", "yes2");
                        BufferedReader reader= new BufferedReader(new InputStreamReader(in));
                        StringBuilder response=new StringBuilder();
                        String line;
                        while((line=reader.readLine())!=null){
                            response.append(line);

                        }
                        Message message = new Message();
                        message.what=SHOW_MESSAGE;
                        message.obj=response.toString();
                        handler.handleMessage(message);

                    Log.d("abc",responseCode);


                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
