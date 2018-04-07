package com.computer.shop.computershop;

import android.content.Context;
import android.widget.Filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//DO NOT TOUCH THIS CLASS

//This is supposed to hold you search history
//These 2 will pop up when you get close to the word.
//Realized this would take much more work than its worth. maybe later :(
public class DataHelper {
    private static final String Product_data_file = "products.json";

    private static List<ProductWrapper> sProductWrapper = new ArrayList<>();

    //This is supposed to hold you search history
    //These 2 will pop up when you get close to the word.
    private static List<Product> sProductSuggest =
            new ArrayList<>(Arrays.asList(
                    new Product("Computer1"),
                    new Product("Computer2")
            ));

    public interface OnFindProductListener {
        void onResults(List<ProductWrapper> results);
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<Product> results);
    }

    public static List<Product> getHistory(Context context, int count) {

        List<Product> suggestionList = new ArrayList<>();
        Product productSugg;
        for (int i = 0; i < sProductSuggest.size(); i++) {
            productSugg = sProductSuggest.get(i);
            productSugg.setIsHistory(true);
            suggestionList.add(productSugg);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (Product colorSuggestion : sProductSuggest) {
            colorSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DataHelper.resetSuggestionsHistory();
                List<Product> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (Product suggestion : sProductSuggest) {
                        if (suggestion.getBody().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(suggestion);
                            if (limit != -1 && suggestionList.size() == limit) {
                                break;
                            }
                        }
                    }
                }

                FilterResults results = new FilterResults();
                Collections.sort(suggestionList, new Comparator<Product>() {
                    @Override
                    public int compare(Product lhs, Product rhs) {
                        return lhs.getIsHistory() ? -1 : 0;
                    }
                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<Product>) results.values);
                }
            }
        }.filter(query);

    }


    public static void findProducts(Context context, String query, final OnFindProductListener listener) {
        initColorWrapperList(context);

        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<ProductWrapper> suggestionList = new ArrayList<>();

                if (!(constraint == null || constraint.length() == 0)) {

                    for (ProductWrapper p : sProductWrapper) {
                        if (p.getTitle().toUpperCase()
                                .startsWith(constraint.toString().toUpperCase())) {

                            suggestionList.add(p);
                        }
                    }

                }

                FilterResults results = new FilterResults();
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<ProductWrapper>) results.values);
                }
            }
        }.filter(query);

    }


    private static void initColorWrapperList(Context context) {

        if (sProductWrapper.isEmpty()) {
            String jsonString = loadJson(context);
            sProductWrapper = deserializeProducts(jsonString);
        }
    }

    private static String loadJson(Context context) {
        //TODO MAKE THIS LOAD FROM API
        String jsonString=
                "[" +
                        "{\n\"title\": \"Computer1\"\n}," +
                        "{\n\"title\": \"Computer2\"\n}," +
                        "{\n\"title\": \"Computer3\"\n}," +
                "]";

        /*try {
            InputStream is = context.getAssets().open(COLORS_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }*/

        return jsonString;
    }

    private static List<ProductWrapper> deserializeProducts(String jsonString) {

        Gson gson = new Gson();

        Type collectionType = new TypeToken<List<ProductWrapper>>() {
        }.getType();
        return gson.fromJson(jsonString, collectionType);
    }

}
