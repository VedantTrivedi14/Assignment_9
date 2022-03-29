package com.tatvasoftassignment.assignment_9.AsycTask;


import static com.tatvasoftassignment.assignment_9.Fragment.ImgFragment.binding;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.tatvasoftassignment.assignment_9.Activity.SingleImgActivity;
import com.tatvasoftassignment.assignment_9.Adapter.ImageAdapter;
import com.tatvasoftassignment.assignment_9.Fragment.ImgFragment;
import com.tatvasoftassignment.assignment_9.R;
import com.tatvasoftassignment.assignment_9.Utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImgAsyncTask extends BackGroundTask  implements ImageAdapter.OnItemClickListener {


    Context context;
    ProgressDialog progressDialog;

    private ArrayList imageFile;
    ImageAdapter imageAdapter;
    List<String> imagePathList = new ArrayList<>();
    private final ArrayList<File> allImageFile = new ArrayList<>();
    static String[] imgExtension = {".jpg", ".jpeg", ".png"};

    public ImgAsyncTask(Context context) {
        this.context = context;
    }



    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(context.getString(R.string.loading_progressDialog));
        progressDialog.setMessage(context.getString(R.string.pleaseWait_progressDialog));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }



    @Override
    protected ArrayList<File> doInBackground(File... files) {
        File[] fileList = files[0].listFiles();
        if (fileList != null && fileList.length > 0) {
            for (File value : fileList) {
                if (value.isDirectory()) {
                    doInBackground(value);
                } else {
                    String name = value.getName().toLowerCase();
                    for (String extensions : imgExtension) {
                        if (name.endsWith(extensions)) {
                            allImageFile.add(value);
                            break;
                        }
                    }
                }
            }
        }
        imageFile = allImageFile;
        return allImageFile;
    }


    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);

        for (int j = 0; j < arrayList.size(); j++) {
            imagePathList.add(String.valueOf(arrayList.get(j)));
        }
        StringBuilder massage = new StringBuilder();
        massage.append(ImgFragment.folder).append(context.getString(R.string.no_image));

        binding.dummyText.setText(new StringBuilder().append(ImgFragment.folder).append(" ").append(context.getString(R.string.no_image)));
        if (imagePathList.size() == 0) {
            binding.dummyText.setVisibility(View.VISIBLE);
        } else {
            binding.dummyText.setVisibility(View.GONE);
            imageAdapter = new ImageAdapter(imagePathList, this);
            binding.recImg.setAdapter(imageAdapter);
            binding.recImg.setLayoutManager(new GridLayoutManager(context, 3));
        }
        progressDialog.dismiss();

    }

    @Override
    public void onClick(int position) {

        Intent intent = new Intent(context, SingleImgActivity.class);
        intent.putExtra(Constants.IMAGE, String.valueOf(imageFile.get(position)));
        intent.putExtra(Constants.IMAGE_POSITION, position);
        intent.putExtra(Constants.IMAGE_PATH_LIST, imageFile);
        context.startActivity(intent);
    }

    @Override
    public void cancel() {
        super.cancel();
    }
}

