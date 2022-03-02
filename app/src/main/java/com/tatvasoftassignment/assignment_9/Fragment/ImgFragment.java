package com.tatvasoftassignment.assignment_9.Fragment;

import static com.tatvasoftassignment.assignment_9.Activity.MainActivity.actionBar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.tatvasoftassignment.assignment_9.AsycTask.ImgAsyncTask;
import com.tatvasoftassignment.assignment_9.R;
import com.tatvasoftassignment.assignment_9.Utils.Constants;
import com.tatvasoftassignment.assignment_9.databinding.FragmentImgBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ImgFragment extends Fragment {

    List<String> mList = new ArrayList<>();

    public static String folder;
    public static boolean isPermission = false;


    public ImgFragment() {
    }

    public static FragmentImgBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentImgBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.gallery);

        return binding.getRoot();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        setStatusColor(preferences.getString(Constants.STATUS_COLOUR, this.getResources().getString(R.color.purple_700)));
        setActionColor(preferences.getString(Constants.ACTION_COLOUR, this.getResources().getString(R.color.purple_500)));


        folder = preferences.getString(Constants.SELECT_FOLDER, "");

        display();
//        CheckUserPermission();
        mList = new ArrayList<>();
    }

    @Override
    public void onPause() {
        super.onPause();
        mList.clear();
    }
//    final private int REQUEST_CODE_ASK_PERMISSIONS = 84;
//    void CheckUserPermission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) !=
//                    PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{
//                                Manifest.permission.READ_EXTERNAL_STORAGE},
//                        REQUEST_CODE_ASK_PERMISSIONS);
//                return;
//            }
//        }
//
//        display();
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                display();
//            } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
//                Toast.makeText(getContext(), getString(R.string.do_not_ask_permission), Toast.LENGTH_SHORT).show();
//            } else {
//                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(requireContext());
//                alertBuilder.setCancelable(true);
//                alertBuilder.setTitle(R.string.Necessary_permission);
//                alertBuilder.setMessage(R.string.Storage_permission_must_require_to_access_folders);
//                alertBuilder.setPositiveButton(R.string.ok, (dialog, which) -> {
//                    requestPermissions(new String[]{
//                                    Manifest.permission.READ_EXTERNAL_STORAGE},
//                            REQUEST_CODE_ASK_PERMISSIONS);
//                });
//                AlertDialog alert = alertBuilder.create();
//                alert.show();
//            }
//        } else {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//    }
//

    private void display() {


        mList.clear();
        File file = new File(Environment.getExternalStorageDirectory(), "/" + folder + "/");
        if (!folder.isEmpty()) {
            ImgAsyncTask imageAsyncTask = new ImgAsyncTask(getContext());
            imageAsyncTask.execute(file);
        } else {
            binding.dummyText.setText(R.string.dummy_text);
            if (mList.size() == 0) {
                binding.dummyText.setVisibility(View.VISIBLE);
            } else {
                binding.dummyText.setVisibility(View.GONE);

            }
        }


    }

    public static void setActionColor(String color) {
        ColorDrawable drawable = new ColorDrawable(Color.parseColor(color));
        actionBar.setBackgroundDrawable(drawable);
    }

    public void setStatusColor(String color) {
        requireActivity().getWindow().setStatusBarColor(Color.parseColor(color));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.setting_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.miColorSettings) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivity, new ColorSettingsFragment(getContext())).commit();

        } else if (item.getItemId() == R.id.miFolderSettings) {
            checkUserPermissions();
            if (isPermission) {
                Log.i("isPermission", String.valueOf(isPermission));
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainActivity, new FolderSettingFragment(getContext())).commit();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.do_not_ask_permissiion), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    final private int REQUEST_CODE_ASK_PERMISSIONS = 84;

    void checkUserPermissions() {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
        isPermission = true;
        display();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                display();
//                isPermission =true;
            } else if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && !shouldShowRequestPermissionRationale(permissions[0])) {
                Toast.makeText(getContext(), getString(R.string.do_not_ask_permissiion), Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(requireContext());
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(R.string.Necessary_permission);
                alertBuilder.setMessage(R.string.Storage_permission_must_require_to_access_folders);
                alertBuilder.setPositiveButton(R.string.ok, (dialog, which) -> {
                    requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}