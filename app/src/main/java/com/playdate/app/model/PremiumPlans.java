package com.playdate.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PremiumPlans {

    @SerializedName("packageDescription")
    private ArrayList<PackageDescription> lst_packageDescription;

    private String name;

    private String packageId;

    private String packageType;


    public ArrayList<PackageDescription> getLst_packageDescription() {
        return lst_packageDescription;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
}
