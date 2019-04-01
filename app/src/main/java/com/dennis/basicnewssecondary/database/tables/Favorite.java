package com.dennis.basicnewssecondary.database.tables;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favorite implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int localId;
    private String id;
    private String title;
    private String main;
    private String teaser;
    private String date;
    private String base_url;
    private String base_filename;

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getBase_filename() {
        return base_filename;
    }

    public void setBase_filename(String base_filename) {
        this.base_filename = base_filename;
    }
}
