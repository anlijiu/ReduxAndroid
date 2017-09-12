package com.example.reduxsample;

import com.example.reduxsample.di.DaggerAppComponent;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.yheriatovych.reductor.Store;
import com.yheriatovych.reductor.observable.rxjava2.EpicMiddleware;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import okhttp3.OkHttpClient;
import timber.log.Timber;

public class SampleApplication extends DaggerApplication {

    @Inject
    OkHttpClient okHttpClient;

    @Override
    public AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        initImageLoader();
    }

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    public static final int MAX_DISK_CACHE_SIZE = 50 * ByteConstants.MB;
    public static final int MAX_DISK_CACHE_SIZE_LOW = 20 * ByteConstants.MB;
    public static final int MAX_DISK_CACHE_SIZE_VERY_LOW = 5 * ByteConstants.MB;
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 8;

    private void initImageLoader() {

        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
            .newBuilder(this, okHttpClient)
            .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
            .setBitmapMemoryCacheParamsSupplier(
                    new Supplier<MemoryCacheParams>() {
                        public MemoryCacheParams get() {
                            return bitmapCacheParams;
                        }
                    })
        .setMainDiskCacheConfig(
                DiskCacheConfig.newBuilder(this)
                .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_SIZE_LOW)
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_SIZE_VERY_LOW)
                .build()).build();
        Fresco.initialize(this, config);
    }


}
