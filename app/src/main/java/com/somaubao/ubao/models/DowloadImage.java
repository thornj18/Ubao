package com.somaubao.ubao.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Tonny on 9/20/2014.
 */
// DownloadImage AsyncTask
class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView image;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create a progressdialog
        //mProgressDialog = new ProgressDialog(MainActivity.this);
        // Set progressdialog title
        //mProgressDialog.setTitle("Download Image Tutorial");
        // Set progressdialog message
        //mProgressDialog.setMessage("Loading...");
        //mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        //mProgressDialog.show();
    }

    @Override
    protected Bitmap doInBackground(String... URL) {

        String imageURL = URL[0];

        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        // Set the bitmap into ImageView
       // image.setImageBitmap(result);
        // Close progressdialog
       // mProgressDialog.dismiss();
    }
}