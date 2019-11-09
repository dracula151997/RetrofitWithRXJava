package com.project.semicolon.rxjava.services.model.ticktes;

import com.google.gson.annotations.SerializedName;

public class Airline {

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logo;

    @SerializedName("id")
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "Airline{" +
                        "name = '" + name + '\'' +
                        ",logo = '" + logo + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}