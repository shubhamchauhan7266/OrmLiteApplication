package com.example.user.ormliteapplication;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by user on 9/6/17.
 */

class Person {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstname) {
        this.name = firstname;
    }

}
