package com.example.reduxsample.data.api;



import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import timber.log.Timber;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Module(includes = ApiModule.class)
public final class DebugApiModule {

    @Provides
    @Singleton
    HttpUrl provideHttpUrl() {
        return HttpUrl.parse("https://api.github.com");
    }

    @Provides
    @Singleton
    CurlLoggingInterceptor provideCurlLoggingInterceptor() {
        return new CurlLoggingInterceptor(message -> Timber.tag("Curl").v(message));
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").v(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    @Provides
    @Singleton
    @Named("Api")
    OkHttpClient provideApiClient(OkHttpClient client,
                                  HttpLoggingInterceptor loggingInterceptor,
                                  CurlLoggingInterceptor curlLoggingInterceptor) {

        return client.newBuilder()
                // .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(curlLoggingInterceptor)
                .build();
    }
}
