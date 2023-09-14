package com.aliIoT.demo.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;


/**
 * Created by hjt on 2020/4/28
 */
public class GlideUtils {
    public static String TAG = GlideUtils.class.getSimpleName();

    public static boolean loadImage(View context, Object model, RequestOptions requestOptions, ImageView imageView) {
        try {
            Glide.with(context).load(model).apply(requestOptions).into(imageView);
        } catch (Exception e) {

            return false;
        }
        return true;
    }

    public static boolean loadImage(Context context, Object model, RequestOptions requestOptions, ImageView imageView) {
        try {
            Glide.with(context).load(model).apply(requestOptions).into(imageView);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean loadImageCache(Context context, Object model, RequestOptions requestOptions, ImageView imageView) {
        try {
            Glide.with(context).load(model).apply(requestOptions.skipMemoryCache(false).dontAnimate()).into(imageView);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean loadImage(FragmentActivity context, Object model, RequestOptions requestOptions, ImageView imageView) {
        try {
            Glide.with(context).load(model).apply(requestOptions).into(imageView);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean loadImage(Fragment context, Object model, RequestOptions requestOptions, ImageView imageView) {
        try {
            Glide.with(context).load(model).apply(requestOptions).into(imageView);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean loadImageCache(Fragment context, Object model, RequestOptions requestOptions, ImageView imageView) {
        try {
            Glide.with(context).load(model).apply(requestOptions.skipMemoryCache(false)).into(imageView);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static RequestOptions creatRequestOptions() {
        RequestOptions requestOptions = new RequestOptions().disallowHardwareConfig();
        return requestOptions;
    }

    /**
     * 禁止硬位图，，处理cannot create a mutable bitmap with config hardware问题
     *
     * @return
     */
    public static RequestOptions creatRequestOptionsDisallowHardwareConfig() {
        RequestOptions requestOptions = new RequestOptions().disallowHardwareConfig();
        return requestOptions;
    }

    public static RequestOptions creatRequestCircleCropOptions() {
        RequestOptions requestOptions = RequestOptions.circleCropTransform();
        return requestOptions;
    }

    public static RequestOptions creatRequestfitCenterOptions() {
        RequestOptions requestOptions = RequestOptions.fitCenterTransform().disallowHardwareConfig();
        return requestOptions;
    }

    public static RequestOptions optionsAddDefultErrorImage(RequestOptions mRequestOptions, int errorImage, int defultImage) {
        mRequestOptions = optionsAddDefultImage(mRequestOptions, defultImage);
        mRequestOptions = optionsAddErrorImage(mRequestOptions, errorImage);
        return mRequestOptions;
    }

    public static RequestOptions optionsAddDefultImage(RequestOptions mRequestOptions, int image) {
        mRequestOptions = mRequestOptions.placeholder(image);
        return mRequestOptions;
    }

    public static RequestOptions optionsAddCircleCropImage(RequestOptions mRequestOptions) {
        mRequestOptions = mRequestOptions.bitmapTransform(new CircleCrop());
        return mRequestOptions;
    }

    public static RequestOptions optionsAddErrorImage(RequestOptions mRequestOptions, int image) {
        mRequestOptions = mRequestOptions.error(image);
        return mRequestOptions;
    }
}
