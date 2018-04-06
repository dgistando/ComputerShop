package com.computer.shop.computershop;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductWrapper implements Parcelable{

    @SerializedName("title")
    @Expose
    private String title;


    private ProductWrapper(Parcel in) {
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    /**
     *
     * @return
     * The name
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setTitle(String name) {
        this.title = name;
    }


    public static final Creator<ProductWrapper> CREATOR = new Creator<ProductWrapper>() {
        @Override
        public ProductWrapper createFromParcel(Parcel in) {
            return new ProductWrapper(in);
        }

        @Override
        public ProductWrapper[] newArray(int size) {
            return new ProductWrapper[size];
        }
    };

}
