package com.project.semicolon.rxjava;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

public class GlideAdapter {
    @BindingAdapter("loadImage")
    public static void displayImage(ImageView imageView, String imageRes){
        Context context = imageView.getContext();
        Glide.with(context)
                .load(imageRes)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);


    }
}
