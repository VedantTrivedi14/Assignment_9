package com.tatvasoftassignment.assignment_9.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.tatvasoftassignment.assignment_9.R;
import com.tatvasoftassignment.assignment_9.Utils.Constants;

import java.util.Objects;


public class FolderSettingFragment extends PreferenceFragmentCompat {

    Context ctx;
    ListPreference folderPreference;

    public FolderSettingFragment(Context ct) {
        ctx = ct;
    }

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        addPreferencesFromResource(R.xml.preferances_folder_setting);
        folderPreference = findPreference(Constants.SELECT_FOLDER);

            assert folderPreference != null;
            folderPreference.setEnabled(true);
            Objects.requireNonNull(folderPreference.getSharedPreferences()).edit().putString(Constants.SELECT_FOLDER, folderPreference.getValue()).apply();

    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.settings);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        setStatusColor(preferences.getString(Constants.STATUS_COLOUR, this.getResources().getString(R.color.purple_700)));
        ImgFragment.setActionColor(preferences.getString(Constants.ACTION_COLOUR, this.getResources().getString(R.color.purple_500)));
    }
    public void setStatusColor(String color) {
        requireActivity().getWindow().setStatusBarColor(Color.parseColor(color));
    }
}