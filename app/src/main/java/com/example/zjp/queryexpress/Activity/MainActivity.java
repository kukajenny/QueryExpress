package com.example.zjp.queryexpress.Activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zjp.queryexpress.Express;
import com.example.zjp.queryexpress.GetCompany;
import com.example.zjp.queryexpress.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editText_id;
    private AutoCompleteTextView edittext_name;
    private TextView textView;
    private TextInputLayout textInputLayout_id;

    ImageView imageView;
    Map map;
    List list;
    Intent news_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        textView = (TextView)findViewById(R.id.text);
        editText_id = (EditText)findViewById(R.id.edittext_id);
        edittext_name = (AutoCompleteTextView)findViewById(R.id.edittext_name);
        textInputLayout_id=(TextInputLayout)findViewById(R.id.textinputlayout_id);
        Button button_query=(Button)findViewById(R.id.button_query);
        button_query.setOnClickListener(this);
        imageView = (ImageView)findViewById(R.id.image_main);
        news_intent = new Intent(MainActivity.this,NewsActivity.class);

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

        ArrayAdapter arrayAdapter;//输入提示
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
        Express express = (Express) map.get(string_editext_name);

        if(express!=null){

            news_intent.putExtra("logo",express.getLogo());
            news_intent.putExtra("name",express.getSimpleName());
            news_intent.putExtra("num", string_edittext_id);
            startActivity(news_intent);

        }
        else Toast.makeText(MainActivity.this,"查询不到此快递公司",Toast.LENGTH_SHORT).show();

    }


    public void getcompany(){

        GetCompany getCompany=new GetCompany(MainActivity.this,R.raw.company);
        list =getCompany.getList();
        map=getCompany.getMap();

    }



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
