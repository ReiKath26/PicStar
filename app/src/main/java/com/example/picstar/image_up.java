package com.example.picstar;

import com.google.firebase.database.Exclude;

public class image_up {

    private String name;

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }

    private String file_extension;

    private String mKey;

    @Exclude
    public String getKey()
    {
        return mKey;
    }

    @Exclude
    public void setKey(String key)
    {
        mKey = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    private String user;

    private String category;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;

    public image_up()
    {

    }

    public image_up(String name, String status, String category, String user, String imageUrl, String file_extension)
    {
        if(name.trim().equals(""))
        {
            name = "Untitled image";
        }

        else
        {
            this.name = name;
        }
        this.category = category;
        this.user = user;
        this.imageUrl = imageUrl;
        this.status = status;
        this.file_extension = file_extension;
    }
}
