package com.tatvasoftassignment.assignment_9.Activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.tatvasoftassignment.assignment_9.Fragment.ImgFragment;
import com.tatvasoftassignment.assignment_9.R;
import com.tatvasoftassignment.assignment_9.Utils.Constants;
import com.tatvasoftassignment.assignment_9.databinding.ActivitySingleImgBinding;

import java.util.ArrayList;

public class SingleImgActivity extends AppCompatActivity {

    private int position;
    private String imageLink;
    private ArrayList myImage;
    private ActivitySingleImgBinding binding;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_img);
        binding = ActivitySingleImgBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MainActivity.actionBar = getSupportActionBar();
        setTitle(R.string.gallery);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myImage = bundle.getParcelableArrayList(Constants.IMAGE_PATH_LIST);
            position = bundle.getInt(Constants.IMAGE_POSITION, 0);
            imageLink = bundle.getString(Constants.IMAGE);
        }

        binding.fullImageViewId.setImageURI(Uri.parse(imageLink));

        binding.previousFab.setOnClickListener(v -> {
            position = ((position - 1) < 0) ? (myImage.size() - 1) : (position - 1);
            binding.fullImageViewId.setImageURI(Uri.parse(myImage.get(position).toString()));
        });

        binding.nextFab.setOnClickListener(v -> {
            position = ((position + 1) % myImage.size());
            binding.fullImageViewId.setImageURI(Uri.parse(myImage.get(position).toString()));
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setStatusColor(preferences.getString(Constants.STATUS_COLOUR, this.getResources().getString(R.color.purple_700)));
        ImgFragment.setActionColor(preferences.getString(Constants.ACTION_COLOUR, this.getResources().getString(R.color.purple_500)));
    }

    public void setStatusColor(String color) {
        this.getWindow().setStatusBarColor(Color.parseColor(color));
    }
}