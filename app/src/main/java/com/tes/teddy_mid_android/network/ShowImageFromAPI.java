package com.tes.teddy_mid_android.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.tes.teddy_mid_android.myinterface.ShowImageInterface;

import java.io.InputStream;

public class ShowImageFromAPI extends AsyncTask<String, Void, Bitmap> {


    ImageView bmImage;
    ShowImageInterface showImageInterface;

    public ShowImageFromAPI(ShowImageInterface showImageInterface) {
        this.showImageInterface = showImageInterface;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        showImageInterface.showImageSuccess(result);

    }
}