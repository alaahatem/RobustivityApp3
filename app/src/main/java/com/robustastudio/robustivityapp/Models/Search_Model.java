package com.robustastudio.robustivityapp.Models;

import java.util.List;

/**
 * Created by MALAK SHAKER on 5/2/2018.
 */

public class Search_Model {

    public String name;
    public List<String> list;

    public Search_Model(String name, List<String> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
