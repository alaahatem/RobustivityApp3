package com.robustastudio.robustivityapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by hp on 02/05/2018.
 */
@Entity
public class Activities {
    @PrimaryKey
    @ColumnInfo(name = "activity_id")
    public int id;



    @ColumnInfo(name = "activity_type")
    public String type;


    @ColumnInfo(name = "activity_content")
    public String content;

    @ColumnInfo(name = "image_uri")
    private String image;

    @ColumnInfo(name = "activity_cont")
    private String cont;
    @ColumnInfo(name = "activity_date")
    public String date;

    public Activities(int id, String type, String content, String cont , String date) {
        this.id = id;
        this.type = type;
        this.content = content;

        this.cont = cont;
        this.date= date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

