package com.example.app.foodoncampus;

/**
 * Created by joseppmoreira on 02/10/2017.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Canteen implements Serializable {
    private static final long serialVersionUID = -1213949467658913456L;
    private String canteenName;
    private String meals;
    private static ArrayList<Canteen> canteens = new ArrayList<>();

    public Canteen(String canteenName, String meals) {
        this.canteenName = canteenName;
        this.meals = meals;
    }

    public String getCanteen() {
        return canteenName;
    }

    public static ArrayList<Canteen> getCanteens() {
        return canteens;
    }

    public static void addItem(String canteenName, String meals) {
        canteens.add(new Canteen(canteenName, meals));
    }

    public String getMeals() {
        return meals;
    }

    @Override
    public String toString() {
        return getCanteen();
    }

}
