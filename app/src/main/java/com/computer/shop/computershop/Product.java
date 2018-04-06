package com.computer.shop.computershop;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

public class Product implements SearchSuggestion {

    private int id;
    private String title;
    private String desc;
    private double rating;
    private double price;
    private int image;

    private boolean mIsHistory;

    public Product(int id, String title, String desc, double rating, double price, int image) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.rating = rating;
        this.price = price;
        this.image = image;
    }

    public Product(Parcel source) {
        this.title = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public Product(String str){
        this.title = str;
        this.desc = "";
        this.rating = 0.0;
        this.price = 0.0;
        this.image = 0;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    @Override
    public String getBody() {
        return title;
    }

    public void setIsHistory(boolean in){
        mIsHistory = in;
    }

    public boolean getIsHistory(){
        return mIsHistory;
    }


    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(mIsHistory ? 1 : 0);
    }

}
