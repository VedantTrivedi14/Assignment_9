package com.tatvasoftassignment.assignment_9.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.tatvasoftassignment.assignment_9.Activity.MainActivity;
import com.tatvasoftassignment.assignment_9.R;
import com.tatvasoftassignment.assignment_9.Utils.Constants;

import java.util.Objects;


public class ColorSettingsFragment extends PreferenceFragmentCompat {


    Context ctx;

//    ListPreference folderPreference;

    public ColorSettingsFragment(Context ct) {
        ctx = ct;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.settings);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @SuppressLint("ResourceType")
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferances_color_setting);
        Preference statusPreference = findPreference(Constants.STATUS_COLOUR);
        Preference actionPreference = findPreference(Constants.ACTION_COLOUR);



        assert statusPreference != null;
        statusPreference.setOnPreferenceClickListener(preference -> {
            ColorPickerDialogBuilder.with(ctx)
                    .setTitle(getString(R.string.title))
                    .initialColor(R.color.purple_700)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setOnColorSelectedListener(selectedColor -> Toast.makeText(ctx, getString(R.string.toast_color_select) + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show())
                    .setPositiveButton(getString(R.string.ok), (dialog, selectedColor, allColors) -> {
                        requireActivity().getWindow().setStatusBarColor(selectedColor);
                        Objects.requireNonNull(statusPreference.getSharedPreferences()).edit().putString(Constants.STATUS_COLOUR, getString(R.string.hash) + Integer.toHexString(selectedColor)).apply();
                    })
                    .setNegativeButton(getString(R.string.Cancel), (dialogInterface, i) -> {

                    })
                    .build()
                    .show();
            return true;
        });

        assert actionPreference != null;
        actionPreference.setOnPreferenceClickListener(preference -> {
            ColorPickerDialogBuilder.with(ctx)
                    .setTitle(getString(R.string.title))
                    .initialColor(R.color.purple_500)
                    .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                    .density(12)
                    .setOnColorSelectedListener(selectedColor -> Toast.makeText(ctx, getString(R.string.toast_color_select) + Integer.toHexString(selectedColor), Toast.LENGTH_SHORT).show())
                    .setPositiveButton(getString(R.string.ok), (dialog, selectedColor, allColors) -> {
                        MainActivity.actionBar.setBackgroundDrawable(new ColorDrawable(selectedColor));
                        Objects.requireNonNull(actionPreference.getSharedPreferences()).edit().putString(Constants.ACTION_COLOUR, getString(R.string.hash) + Integer.toHexString(selectedColor)).apply();
                    })
                    .setNegativeButton(getString(R.string.Cancel), (dialogInterface, i) -> {

                    })
                    .build()
                    .show();
            return true;
        });
    }



}
