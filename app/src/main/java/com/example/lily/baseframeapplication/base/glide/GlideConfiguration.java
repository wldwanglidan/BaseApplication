package com.example.lily.baseframeapplication.base.glide;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.GlideModule;
import com.example.lily.baseframeapplication.app.Constant;
import com.example.lily.baseframeapplication.app.DirContext;
import java.io.File;

/**
 * Created by lily on 2017/11/10.
 */

public class GlideConfiguration implements GlideModule {

    @Override public void applyOptions(Context context, GlideBuilder builder) {
        //如果你对默认的RGB_565效果还比较满意，可以不做任何事，但是如果你觉得难以接受，可以创建一个新的GlideModule将Bitmap格式转换到ARGB_8888：
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置Glide的图片磁盘缓存路径
        builder.setDiskCache(() -> {
            File cacheLocation = DirContext.getInstance()
                                           .getDir(DirContext.DirEnum.IMAGE);
            return DiskLruCacheWrapper.get(cacheLocation,
                    Constant.IMAGE_CACHE_SIZE);
        });
        //配置内存缓存大小
        //        builder.setMemoryCache(new LruResourceCache(yourSizeInBytes));

    }


    @Override public void registerComponents(Context context, Glide glide) {

    }
}
