package com.example.listadapterexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

public class LoadingImageTask extends AsyncTask<Void, Void, Bitmap> {
    private String urlString = "";
    private ImageView view;
    private Bitmap bm;
    private final WeakReference<ImageView> imageViewReference;

    public LoadingImageTask(ImageView view, String urlString) {
        this.imageViewReference = new WeakReference<ImageView>(view);
        this.urlString = urlString;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        Bitmap bm = null;
        try {
            URL aURL = new URL(urlString);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Hub","Error getting the image from server : " + e.getMessage().toString());
        }
        return bm;
    }

    @Override
    protected void onPostExecute(final Bitmap bm) {
        super.onPostExecute(bm);

        if (isCancelled()) {
            return;
        }

        if (bm != null){ //if bitmap exists...
            view = imageViewReference.get();
            /*
            // Fade out
            Animation fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.fadeoutimage);
            fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {

                public void onAnimationStart(Animation animation) {

                }

                public void onAnimationRepeat(Animation animation) {

                }

                public void onAnimationEnd(Animation animation) {
                    // Fade in
                    view.setImageBitmap(bm);
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fadeinimage);
                    view.startAnimation(fadeInAnimation);
                }
            });

            // Launch the fadeout
            view.startAnimation(fadeOutAnimation);
            */
            view.setImageBitmap(bm);

        }else{ //if not picture, display the default ressource
            //view.setImageResource(R.drawable.productcarre);
        }

    }

}