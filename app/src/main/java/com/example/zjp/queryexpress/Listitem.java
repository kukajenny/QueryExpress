package com.example.zjp.queryexpress;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by zjp on 16-1-4.
 */
public class Listitem {
    private int imageId;
    private String comtext;
    private String time;
    int textcolor;

    public Listitem(int imageId,String comtext,String time,int textcolor){
        this.imageId=imageId;
        this.comtext=comtext;
        this.time=time;
        this.textcolor=textcolor;
    }

    public String getTime() {
        return time;
    }


    public int getImageId() {
        return imageId;
    }


    public String getComtext() {
        return comtext;
    }

    public int getTextcolor() {
        return textcolor;
    }
}
