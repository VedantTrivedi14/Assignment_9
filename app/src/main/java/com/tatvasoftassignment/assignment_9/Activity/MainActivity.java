package com.tatvasoftassignment.assignment_9.Activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.tatvasoftassignment.assignment_9.Fragment.ImgFragment;
import com.tatvasoftassignment.assignment_9.R;


public class MainActivity extends AppCompatActivity {

    public static ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainActivity, new ImgFragment()).commit();
    }
}