package com.example.caloriecounter.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.caloriecounter.Constants;
import com.example.caloriecounter.Models.FoodEntity;
import com.example.caloriecounter.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

public class AutocompleteAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<FoodEntity> foodList;

    public AutocompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        foodList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public FoodEntity getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    try {
                        //get data from the web
                        String userRequest = constraint.toString().toLowerCase().trim();
                        foodList = getFoodList(userRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    filterResults.values = foodList;
                    filterResults.count = foodList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return myFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.auto_complete_layout, parent, false);

        //Autocomplete list item initialization
        FoodEntity foodEntity = getItem(position);

        TextView foodName = view.findViewById(R.id.tv_food_title);
        TextView foodCalorie = view.findViewById(R.id.tv_food_calorie);

        foodName.setText(foodEntity.getName());
        foodCalorie.setText(foodEntity.getCalories() + " kcal");

        return view;
    }


    //Build get method
    private URL buildUrl(String userRequest) throws IOException {
        return new URL(Constants.FOOD_DB_PARSE_METHOD +
                "ingr=" + URLEncoder.encode(userRequest, "UTF-8") +
                "&app_id=" + Constants.APP_ID +
                "&app_key=" + Constants.APP_KEY);
    }


    //Return array list of food
    private ArrayList<FoodEntity> getFoodList(String userRequest) throws Exception {
        URL url = buildUrl(userRequest);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        StringBuilder response = new StringBuilder();

        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                response.append(scanner.next());
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
        String myResponse = response.toString();

        return parseFoodJson(myResponse);
    }


    //Json parse
    private ArrayList<FoodEntity> parseFoodJson(String response) throws JSONException {
        ArrayList<FoodEntity> foodEntities = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        for (int i = 0; i < jsonObject.getJSONArray("hints").length(); i++) {
            JSONObject jsonFood = jsonObject.getJSONArray("hints").getJSONObject(i).getJSONObject("food");
            String name = jsonFood.getString("label");


            //Database doesn't guarantee, that we will have every parameter, so we use try catch
            //And if one parameter is missed, we don't need this result
            double fats = 0;
            try {
                fats = jsonFood.getJSONObject("nutrients").getDouble("FAT");
            } catch (Exception e) {
                continue;
            }

            double carbohydrates = 0;
            try {
                carbohydrates = jsonFood.getJSONObject("nutrients").getDouble("CHOCDF");
            } catch (Exception e) {
                continue;
            }

            double proteins = 0;
            try {
                proteins = jsonFood.getJSONObject("nutrients").getDouble("PROCNT");
            } catch (Exception e) {
                continue;
            }

            double calories = 0;
            try {
                calories = jsonFood.getJSONObject("nutrients").getDouble("ENERC_KCAL");
            } catch (Exception e) {
                continue;
            }


            //Numbers can be really big, so we round them to 2 places
            //And add to list
            foodEntities.add(new FoodEntity(name,
                    round(calories, 2),
                    round(fats, 2),
                    round(carbohydrates, 2),
                    round(proteins, 2),
                    null));
        }

        return foodEntities;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
