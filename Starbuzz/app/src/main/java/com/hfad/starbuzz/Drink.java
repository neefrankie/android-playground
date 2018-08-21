package com.hfad.starbuzz;

public class Drink {
    private String name;
    private String description;
    private int imageResourceId;

    public static final Drink[] drinks = {
        new Drink("Latte", "A couple of esprsso shots with steamed milk", R.drawable.latte),
        new Drink("Capuccino", "Espresso, hot milk, and a steam milk foam", R.drawable.cappuccino),
        new Drink("Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter)
    };

    private Drink(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return this.imageResourceId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
