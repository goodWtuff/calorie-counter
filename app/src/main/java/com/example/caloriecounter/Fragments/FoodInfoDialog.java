package com.example.caloriecounter.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.caloriecounter.Models.FoodEntity;

public class FoodInfoDialog extends DialogFragment {
    private FoodEntity entity;

    public FoodInfoDialog(FoodEntity entity) {
        this.entity = entity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder alertDialog =  new AlertDialog.Builder(getActivity())
                .setTitle(entity.getName())
                .setMessage("\n Calories: " + entity.getCalories() +
                        "\n \n Fats: " + entity.getFats() +
                        "\n \n Proteins: " + entity.getProteins() +
                        "\n \n Carbohydrates: " + entity.getCarbohydrates())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return alertDialog.create();
    }
}
