package com.example.lily.baseframeapplication.base.glide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.FutureTarget;
import com.example.lily.baseframeapplication.R;
import com.example.lily.baseframeapplication.dao.RetrofitDao;
import com.example.lily.baseframeapplication.utils.ToastUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import okhttp3.ResponseBody;

/**
 * Created by lily on 2017/11/10.
 */

public class GlideUtils {
    private static final String TAG = "GlideUtils";


    /**
     * glide加载图片
     *
     * @param view view
     * @param url url
     */
    public static void display(ImageView view, String url) {
        displayUrl(view, url, R.drawable.bg_iv_default);
    }

    /**
     * glide加载图片
     *
     * @param view view
     * @param url url
     * @param defaultImage defaultImage
     */
    public static void displayUrl(final ImageView view, String url,
                                  @DrawableRes int defaultImage) {
        if (view == null) {
            Log.e(TAG, "GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // 判断View是否还活着
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(url)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .placeholder(defaultImage)
                 .crossFade()
                 .into(view)
                 .getSize((width, height) -> {
                     if (!view.isShown()) {
                         view.setVisibility(View.VISIBLE);
                     }
                 });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * glide加载本地图片
     * @param view
     * @param resId
     */
    public static void displayNative(final ImageView view,
                                     @DrawableRes int resId) {
        // 不能崩
        if (view == null) {
            Log.e(TAG, "GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(resId)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .crossFade()
                 .centerCrop()
                 .into(view)
                 .getSize((width, height) -> {
                     if (!view.isShown()) {
                         view.setVisibility(View.VISIBLE);
                     }
                 });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * glide加载圆形图像
     * @param view
     * @param res
     */
    public static void displayCircleHeader(ImageView view,
                                           @DrawableRes int res) {
        // 不能崩
        if (view == null) {
            Log.e(TAG, "GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(res)
                 .centerCrop()
                 .placeholder(R.drawable.default_avatar)
                 .bitmapTransform(new com.example.lily.baseframeapplication.view.widget.glide.GlideCircleTransform(context))
                 .crossFade()
                 .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * glide加载圆形图像
     * @param view
     * @param url
     */
    public static void displayCircleHeader(ImageView view, String url) {
        // 不能崩
        if (view == null) {
            Log.e(TAG, "GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(url)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .centerCrop()
                 .placeholder(R.drawable.default_avatar)
                 .bitmapTransform(new com.example.lily.baseframeapplication.view.widget.glide.GlideCircleTransform(context))
                 .crossFade()
                 .into(view)
                 .getSize((width, height) -> {
                     if (!view.isShown()) {
                         view.setVisibility(View.VISIBLE);
                     }
                 });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * glide加载带有边框的圆形图像
     * @param view
     * @param url
     */
    public static void displayCircleHeaderBorder(ImageView view, String url) {
        // 不能崩
        if (view == null) {
            Log.e(TAG, "GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            Glide.with(context)
                 .load(url)
                 .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                 .centerCrop()
                 .placeholder(R.drawable.default_avatar)
                 .transform(new com.example.lily.baseframeapplication.view.widget.glide.GlideCircleTransform(context, context
                         .getResources().getDimensionPixelOffset(R.dimen.dp_3),
                         context.getResources().getColor(R.color.transparent)))
                 .crossFade()
                 .into(view)
                 .getSize((width, height) -> {
                     if (!view.isShown()) {
                         view.setVisibility(View.VISIBLE);
                     }
                 });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * glide加载矩形图像
     * @param view
     * @param url
     */
    public static void displayRound(ImageView view, String url,
                                    @DrawableRes int defaultImage) {
        // 不能崩
        if (view == null) {
            Log.e(TAG, "GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(url)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 //.centerCrop()
                 .placeholder(defaultImage)
                 .transform(new CenterCrop(context),
                         new com.example.lily.baseframeapplication.view.widget.glide.GlideRoundTransform(context, 4))
                 .crossFade()
                 .into(view)
                 .getSize((width, height) -> {
                     if (!view.isShown()) {
                         view.setVisibility(View.VISIBLE);
                     }
                 });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayUrl(final ImageView view, String url, int width, int height) {
        // 不能崩
        if (view == null) {
            Log.e(TAG, "GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }

        try {
            Glide.with(context)
                 .load(url)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .override(width, height)
                 .crossFade()
                 .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 下载图片
     * @param url
     * @param destFileDir
     * @param context
     */
    public static void downLoadImage(final String url, final String
            destFileDir, final Context context) {
        RetrofitDao.getInstance().getApiService()
                   .downLoadImage(url)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ResponseBody>() {
            @Override public void onSubscribe(@NonNull Disposable d) {

            }


            @Override public void onNext(@NonNull ResponseBody responseBody) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = responseBody.byteStream();
                    File file = new File(destFileDir);
                    //如果file不存在,就创建这个file
                    if (!file.exists()) {
                        file.mkdir();
                    }

                    final File imageFile = new File(destFileDir,
                            new Date().getTime() + ".jpg");
                    fos = new FileOutputStream(imageFile);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    ToastUtils.showLongToast("已成功保存到相册!");
                    //更新相册
                    Intent intent = new Intent(
                            Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    context.sendBroadcast(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtils.showLongToast("保存图片失败!");
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }


            }


            @Override public void onError(@NonNull Throwable e) {
                ToastUtils.showLongToast("保存图片失败!");


            }


            @Override public void onComplete() {

            }
        });

    }


    /**
     * 从缓存中得到图片路径
     * @param context
     * @param url
     * @return
     */
    public static String getImgPathFromCache(Context context, String url) {
        FutureTarget<File> future = Glide.with(context)
                                         .load(url)
                                         .downloadOnly(100, 100);
        try {
            File cacheFile = future.get();
            String path = cacheFile.getAbsolutePath();
            return path;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


}
