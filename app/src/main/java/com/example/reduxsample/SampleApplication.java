package com.example.reduxsample;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.data.CanBusAgent;
import com.example.reduxsample.di.DaggerAppComponent;
import com.example.ui.ActivityLifecyclesServer;
import com.example.ui.misc.FlexibleToast;
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
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import okhttp3.OkHttpClient;
import timber.log.Timber;

public class SampleApplication extends DaggerApplication {

    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Context context;
    @Inject
    ActivityLifecyclesServer.Proxy proxy;
    @Inject
    CanBusAgent canBusAgent;

    /**
     * 全局的 handler 对象
     */
    private final Handler APPHANDLER = new Handler();

    /**
     * 全局的 Toast 对象
     */
    private FlexibleToast flexibleToast;

    @Override
    public AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Timber.d("  okHttpClient is %s", okHttpClient);
        Timber.d("  context is %s", context);
        registerActivityLifecycleCallbacks(proxy);
        initImageLoader();

//        RxJavaPlugins.setErrorHandler(throwable -> {
//            throwable.printStackTrace();
//            Timber.e("RxJavaPlugins.getInstance() handleError -> %s", throwable);
//        });

        testThread.start();
    }

    public Handler getAppHandler() {
        return APPHANDLER;
    }

    public void showToastByBuilder(final FlexibleToast.Builder builder) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            getAppHandler().post(new Runnable() {
                @Override
                public void run() {
                    Timber.d("flexibleToast show in bg thread");
                    flexibleToast.toastShow(builder);
                }
            });
        } else {
            Timber.d("flexibleToast show in main thread");
            flexibleToast.toastShow(builder);
        }
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

    //TODO  test only,  remove this when real data verify
    Thread testThread = new Thread(new Runnable() {
        int seconds = 0;

        @Override
        public void run() {
            while (true) {
                seconds++;
                canBusAgent.deliver("from can,  " + seconds);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });


}
