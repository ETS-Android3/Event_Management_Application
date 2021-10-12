package com.example.eventmanagementapplication.Models;

import androidx.recyclerview.widget.RecyclerView;

public class serviceCategoryModel {
    private String  cat_name;

    public serviceCategoryModel(String cat_name) {
        this.cat_name = cat_name;

    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }


}
